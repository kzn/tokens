package name.kazennikov.annotations.fsm;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import name.kazennikov.annotations.patterns.AnnotationMatcher;
import name.kazennikov.annotations.patterns.AnnotationMatcherPatternElement;
import name.kazennikov.annotations.patterns.BasePatternElement;
import name.kazennikov.annotations.patterns.PatternElement;
import name.kazennikov.annotations.patterns.PatternElement.Operator;
import name.kazennikov.annotations.patterns.RHS;
import name.kazennikov.annotations.patterns.RangePatternElement;
import name.kazennikov.annotations.patterns.Rule;
import name.kazennikov.tools.Alphabet;

import com.google.common.base.Objects;

public class JapePlusFSM {

	public static interface StateVisitor {
		public void visit(State s);
	}
	
	public static class State {
		int number;
		List<Transition> transitions = new ArrayList<>();
		List<RHS> rhs = new ArrayList<>();
		int priority = -1;

		public boolean isFinal() {
			return !rhs.isEmpty();
		}

		public void addTransition(State to, int label) {
			Transition t = new Transition();
			t.label = label;
			t.target = to;
			transitions.add(t);
		}


		public void toDot(PrintWriter pw, Set<State> visited) {
			if(visited.contains(this))
				return;

			visited.add(this);

			for(Transition t : transitions) {
				pw.printf("%d -> %d [label=\"%d\"];%n", number, t.target.number, t.label);
			}

			for(Transition t : transitions) {
				t.target.toDot(pw, visited);
			}



			if(isFinal()) {
				pw.printf("%d [shape=doublecircle];%n", number);
			}
		}
		
		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("number", number)
					.add("isFinal", isFinal())
					.toString();
		}
		
		public List<Transition> getTransitions() {
			return transitions;
		}
		
		public List<RHS> getRHS() {
			return rhs;
		}
		
		public int getNumber() {
			return number;
		}
		
		public int getPriority() {
			return priority;
		}
		
		public void setPriority(int priority) {
			this.priority = priority;
		}		
		
		
		public void visit(StateVisitor v, Set<State> visited) {
			if(visited.contains(this))
				return;
			
			visited.add(this);
			v.visit(this);

			for(Transition t : transitions) {
				t.target.visit(v, visited);
			}
		}
		
		public void setFinalFrom(Set<State> currentDState) {
			for(State c : currentDState) {
				if(c.isFinal()) {
					this.rhs = c.rhs;
					break;
				}
			}
		}
	}
	
	public static class Transition {
		public static final int EPSILON = 0;
		public static final int GROUP_START = -1;
		
		/**
		 * Encoding:
		 * <ul>
		 * <li> label > 0 - AnnotationMatcher table lookup
		 * <li> label = 0 - epsilon
		 * <li> label = -1 - GROUP_START
		 * <li> label < -1 - named group lookup
		 */
		int label;
		State target;
		
		public State getTarget() {
			return target;
		}
		
		public int getLabel() {
			return label;
		}
		

	}


	List<State> states = new ArrayList<>();	
	
	TIntArrayList tFrom = new TIntArrayList();
	TIntArrayList tTo = new TIntArrayList();
	TIntArrayList tLabel = new TIntArrayList();
	
	Alphabet<String> groups = new Alphabet<>();
	Alphabet<AnnotationMatcher> matchers = new Alphabet<>();
	State start;
	
	public JapePlusFSM() {
		this.start = addState();
	}
	
	public void addRule(Rule r) {
		State start = this.start;
		for(PatternElement e : r.lhs()) {
			start = addPE(e, start);
		}
		
		start.rhs.addAll(r.rhs());
		start.priority = r.getPriority();

	}
	
	public State addState() {
		State state = new State();
		state.number = states.size();
		states.add(state);
		return state;
	}
	
	
	public void addTransition(State from, State to, int label) {
		from.addTransition(to, label);
		tFrom.add(from.number);
		tTo.add(to.number);
		tLabel.add(label);
	}
	
	/**
	 * Add NFA states to start that represent pattern element
	 * @param e pattern element
	 * @param start current start state
	 * @return end state of the pattern element
	 */
	private State addPE(PatternElement e, State start) {
		State end = null;
		
		if(e instanceof AnnotationMatcherPatternElement) {
			end = addState();
			AnnotationMatcher matcher = ((AnnotationMatcherPatternElement) e).matcher();
			int label = matchers.get(matcher);
			
			addTransition(start, end, label);
		} else if(e instanceof BasePatternElement) {
			// OR or SEQ
			if(e.op() == Operator.OR) {
				end = addOR((BasePatternElement)e, start);
			} else if(e.op() == Operator.SEQ) {
				end = addSeq((BasePatternElement)e, start);
			}
			
		} else if(e instanceof RangePatternElement) {
			end = addRange((RangePatternElement)e, start);
		}

		return end;
	}

	private State addRange(RangePatternElement e, State start) {
		State end = addState();
		
		for(int i = 0; i < e.min(); i++) {
			start = addPE(e.get(0), start);
		}

		addTransition(start, end, Transition.EPSILON); // skip optional parrts
		
		if(e.max() == RangePatternElement.INFINITE) { // kleene start
			State mEnd = addPE(e.get(0), start);
			addTransition(mEnd, end, Transition.EPSILON);
			addTransition(mEnd, start, Transition.EPSILON);

		} else { // range [n,m]
			for(int i = e.min(); i < e.max(); i++) {
				start = addPE(e.get(0), start);
				addTransition(start, end, Transition.EPSILON);
			}
		} 
		
		
		return end;
	}

	private State addSeq(BasePatternElement e, State start) {
		if(e.getName() != null) {
			State iStart = addState();
			addTransition(start, iStart, Transition.GROUP_START);
			start = iStart;
		}
		
		for(int i = 0; i < e.size(); i++) {
			start = addPE(e.get(i), start);
		}
		
		if(e.getName() != null) {
			State iEnd = addState();
			int label = -groups.get(e.getName()) - 1;
			addTransition(start, iEnd, label);
			start = iEnd;
		}
		
		return start;
		
	}

	private State addOR(BasePatternElement e, State start) {
		State end = addState();

		for(int i = 0; i < e.size(); i++) {
			State mStart = addState();
			addTransition(start, mStart, Transition.EPSILON);
			State mEnd = addPE(e.get(i), mStart);
			addTransition(mEnd, end, Transition.EPSILON);
		}

		return end;
	}


	public void toDot(PrintWriter pw) {
		pw.println("digraph finite_state_machine {");
		pw.println("rankdir=LR;");
		pw.println("node [shape=circle]");
		start.toDot(pw, new HashSet<State>());
		pw.println("}");

	}
	
	public void toDot(String fileName) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fileName);
		toDot(pw);
		pw.close();
	}
	
	public Set<State> lambdaClosure(Set<State> states) {
		LinkedList<State> list = new LinkedList<State>(states);
		Set<State> closure = new HashSet<State>(states);

		while(!list.isEmpty()) {
			State current = list.removeFirst();
			for(Transition t : current.transitions) {
				if(t.label == Transition.EPSILON) {
					State target = t.target;
					if(!closure.contains(target)) {
						closure.add(target);
						list.addFirst(target);
					}
				}
			}
		}
		return closure;
	}
	
	private TIntSet labels(Set<State> states) {
		TIntHashSet set = new TIntHashSet();
		
		for(State s : states) {
			for(Transition t : s.transitions) {
				if(t.label != Transition.EPSILON)
					set.add(t.label);
			}
		}
		
		return set;
	}
	
	private Set<State> next(Set<State> states, int label) {
		Set<State> next = new HashSet<>();
		
		for(State s : states) {
			for(Transition t : s.transitions) {
				if(t.label == label)
					next.add(t.target);
			}
		}
		
		return next;
	}
	
	
	public JapePlusFSM determinize() {
		JapePlusFSM fsm = new JapePlusFSM();
		
		Map<Set<State>, State> newStates = new HashMap<>();
		Set<Set<State>> dStates = new HashSet<>();
		LinkedList<Set<State>> unmarkedDStates = new LinkedList<Set<State>>();
		
		Set<State> currentDState = new HashSet<State>();


		currentDState.add(start);
		currentDState = lambdaClosure(currentDState);
		dStates.add(currentDState);
		unmarkedDStates.add(currentDState);
		
		newStates.put(currentDState, fsm.start);
		fsm.start.setFinalFrom(currentDState);
		
		while(!unmarkedDStates.isEmpty()) {
			currentDState = unmarkedDStates.removeFirst();
			TIntSet labels = labels(currentDState);

			for(int label : labels.toArray()) {
				Set<State> next = next(currentDState, label);
				next = lambdaClosure(next);

				// add new state to epsilon-free automaton
				if(!dStates.contains(next)) {
					dStates.add(next);
					unmarkedDStates.add(next);
					State newState = fsm.addState();
					newStates.put(next, newState);
					newState.setFinalFrom(next);
				}

				State currentState = newStates.get(currentDState);
				State newState = newStates.get(next);
				fsm.addTransition(currentState, newState, label);
			}
		}
		return fsm;
	}

	
	/**
	 * Converts this epsilon-NFA to epsilon-free NFA. Non-destructive procedure
	 * 
	 * @return fresh epsilon free NFA
	 */
	public JapePlusFSM epsilonFreeFSM() {

		Map<Set<State>, State> newStates = new HashMap<>();
		Set<Set<State>> dStates = new HashSet<>();
		LinkedList<Set<State>> unmarkedDStates = new LinkedList<Set<State>>();
		Set<State> currentDState = new HashSet<State>();


		currentDState.add(start);
		currentDState = lambdaClosure(currentDState);
		dStates.add(currentDState);
		unmarkedDStates.add(currentDState);
		JapePlusFSM fsm = new JapePlusFSM();

		newStates.put(currentDState, fsm.start);

		fsm.start.setFinalFrom(currentDState);

		while(!unmarkedDStates.isEmpty()) {
			currentDState = unmarkedDStates.removeFirst();

			for(State state: currentDState) {
				for(Transition t : state.transitions) {

					// skip epsilon transitions
					if(t.label == 0)
						continue;


					State target = t.target;
					Set<State> newDState = new HashSet<State>();
					newDState.add(target);
					newDState = lambdaClosure(newDState);

					// add new state to epsilon-free automaton
					if(!dStates.contains(newDState)) {
						dStates.add(newDState);
						unmarkedDStates.add(newDState);
						State newState = fsm.addState();
						newStates.put(newDState, newState);
						newState.setFinalFrom(newDState);
					}

					State currentState = newStates.get(currentDState);
					State newState = newStates.get(newDState);
					fsm.addTransition(currentState, newState, t.label);
				}
			}

		}

		return fsm;
	}
	
	public State getStart() {
		return start;
	}
	
	public int size() {
		return states.size();
	}
	
	public JapePlusFSM rev() {
		
		JapePlusFSM fsm = new JapePlusFSM();
		List<State> finals = new ArrayList<>();

		for(int i = 0; i < states.size(); i++ ) {
			State s = fsm.addState();
			State s0 = states.get(i);

			if(s0.isFinal()) {
				s.priority = s0.priority;
				s.rhs = s0.rhs;
				finals.add(s);
			}
		}
		
		
		for(int i = 0; i < tFrom.size(); i++) {
			State from = fsm.states.get(tTo.get(i) + 1);
			int label = tLabel.get(i);
			State to = fsm.states.get(tFrom.get(i) + 1);
			fsm.addTransition(from, to, label);
		}
		
		for(State f : finals) {
			fsm.addTransition(fsm.start, f, Transition.EPSILON);
		}
		
		
		return fsm;
		
	}
	
	public List<State> finals() {
		List<State> finals = new ArrayList<>();
		
		for(State s : states) {
			if(s.isFinal())
				finals.add(s);
		}
		
		return finals;
	}
	
	public JapePlusFSM minimize() {
		JapePlusFSM fsm = new JapePlusFSM();
		/*
		 * Условия:
		 * 1. Нужен механизм для различения конечных состояний
		 * 2. Логично адресовать состояния не объектами, а номерами состояний
		 * 3. (Спорно) Логично адресовать переходы их индексами 
		 * 4. 
		 */
		
		
		
		/*
		 * P := {F, Q \ F};
		 * W := {F};
		 * while (W is not empty) do
		 *      choose and remove a set A from W
		 *      for each c in ∑ do
		 *           let X be the set of states for which a transition on c leads to a state in A
		 *           for each set Y in P for which X ∩ Y is nonempty do
		 *               replace Y in P by the two sets X ∩ Y and Y \ X
		 *               // работа с W
		 *               if Y is in W
		 *                    replace Y in W by the same two sets
		 *               else if |X ∩ Y| <= |Y \ X|
		 *                	  add X ∩ Y to W
		 *               else
		 *               	  add Y \ X to W
		 *           end;
		 *      end;
		 * end;
		 */
		
		return fsm;
	}


}
