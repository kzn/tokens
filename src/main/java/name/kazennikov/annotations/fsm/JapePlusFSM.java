package name.kazennikov.annotations.fsm;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import name.kazennikov.annotations.patterns.AnnotationMatcher;
import name.kazennikov.annotations.patterns.AnnotationMatcherPatternElement;
import name.kazennikov.annotations.patterns.BasePatternElement;
import name.kazennikov.annotations.patterns.PatternElement;
import name.kazennikov.annotations.patterns.PatternElement.Operator;
import name.kazennikov.annotations.patterns.RangePatternElement;
import name.kazennikov.annotations.patterns.Rule;
import name.kazennikov.fsa.FSA;
import name.kazennikov.fsa.FSAState;
import name.kazennikov.tools.Alphabet;

import com.google.common.base.Objects;

public class JapePlusFSM {

	public static interface StateVisitor {
		public void visit(State s);
	}
	
	public static class State {
		int number;
		List<Transition> transitions = new ArrayList<>();
		Set<Rule> actions = new HashSet<>();

		public boolean isFinal() {
			return !actions.isEmpty();
		}

		public Transition addTransition(State to, int label) {
			Transition t = new Transition(this, label, to);
			transitions.add(t);
			return t;
		}


		public void toDot(PrintWriter pw, Set<State> visited) {
			if(visited.contains(this))
				return;

			visited.add(this);

			for(Transition t : transitions) {
				pw.printf("%d -> %d [label=\"%d\"];%n", number, t.dest.number, t.label);
			}

			for(Transition t : transitions) {
				t.dest.toDot(pw, visited);
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
		
		public Set<Rule> getActions() {
			return actions;
		}
		
		public int getNumber() {
			return number;
		}
				
		public void visit(StateVisitor v, Set<State> visited) {
			if(visited.contains(this))
				return;
			
			visited.add(this);
			v.visit(this);

			for(Transition t : transitions) {
				t.dest.visit(v, visited);
			}
		}
		
		public void setFinalFrom(Set<State> currentDState) {
			for(State c : currentDState) {
				if(c.isFinal()) {
					this.actions.addAll(c.actions);
				}
			}
		}
	}
	
	public static class Transition {
		public static final int GROUP_START = -1;
		
		/**
		 * Encoding:
		 * <ul>
		 * <li> label > 0 - AnnotationMatcher table lookup
		 * <li> label = 0 - epsilon
		 * <li> label = -1 - GROUP_START
		 * <li> label < -1 - named group lookup
		 */
		State src;
		int label;
		State dest;
		
		public Transition(State src, int label, State dest) {
			this.src = src;
			this.label = label;
			this.dest = dest;
		}
		
		
		public State getSrc() {
			return src;
		}
		
		public State getDest() {
			return dest;
		}
		
		public int getLabel() {
			return label;
		}
		
		public boolean isEpsilon() {
			return label == name.kazennikov.fsa.Constants.EPSILON;
		}
		
		@Override
		public String toString() {
			return String.format("{src=%d, label=%d, dest=%d}", src.getNumber(), label, dest.getNumber());
		}
		

	}
	
	protected static class InterFSM extends FSA<Set<Rule>> {
		@Override
		public boolean isFinal(FSAState<Set<Rule>> s) {
			return !s.getFinals().isEmpty();
		}

		@Override
		public FSAState<Set<Rule>> addState() {
			FSAState<Set<Rule>> state = super.addState();
			state.setFinals(new HashSet<Rule>());
			return state;
		}
		
		@Override
		public void mergeFinals(FSAState<Set<Rule>> dest, FSAState<Set<Rule>> src) {
			dest.getFinals().addAll(src.getFinals());
		}

		
		
		
	}


	//List<State> states = new ArrayList<>();	
	//List<Transition> transitions = new ArrayList<>();
	
	Alphabet<String> groups = new Alphabet<>();
	Alphabet<AnnotationMatcher> matchers = new Alphabet<>();
	InterFSM fsm = new InterFSM();
	
	public JapePlusFSM() {
	}
	
	public void addRule(Rule r) {
		FSAState<Set<Rule>> start = fsm.getStart();
		for(PatternElement e : r.lhs()) {
			start = addPE(e, start);
		}
		
		start.getFinals().add(r);
	}
	
	
	
	/**
	 * Add NFA states to start that represent pattern element
	 * @param e pattern element
	 * @param start current start state
	 * @return end state of the pattern element
	 */
	private FSAState<Set<Rule>> addPE(PatternElement e, FSAState<Set<Rule>> start) {
		FSAState<Set<Rule>> end = null;
		
		if(e instanceof AnnotationMatcherPatternElement) {
			end = fsm.addState();
			AnnotationMatcher matcher = ((AnnotationMatcherPatternElement) e).matcher();
			int label = matchers.get(matcher);
			
			fsm.addTransition(start, end, label);
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

	private FSAState<Set<Rule>> addRange(RangePatternElement e, FSAState<Set<Rule>> start) {
		FSAState<Set<Rule>> end = fsm.addState();
		
		for(int i = 0; i < e.min(); i++) {
			start = addPE(e.get(0), start);
		}

		fsm.addTransition(start, end, name.kazennikov.fsa.Constants.EPSILON); // skip optional parrts
		
		if(e.max() == RangePatternElement.INFINITE) { // kleene start
			FSAState<Set<Rule>> mEnd = addPE(e.get(0), start);
			fsm.addTransition(mEnd, end, name.kazennikov.fsa.Constants.EPSILON);
			fsm.addTransition(mEnd, start, name.kazennikov.fsa.Constants.EPSILON);

		} else { // range [n,m]
			for(int i = e.min(); i < e.max(); i++) {
				start = addPE(e.get(0), start);
				fsm.addTransition(start, end, name.kazennikov.fsa.Constants.EPSILON);
			}
		} 
		
		
		return end;
	}

	private FSAState<Set<Rule>> addSeq(BasePatternElement e, FSAState<Set<Rule>> start) {
		if(e.getName() != null) {
			FSAState<Set<Rule>> iStart = fsm.addState();
			fsm.addTransition(start, iStart, Transition.GROUP_START);
			start = iStart;
		}
		
		for(int i = 0; i < e.size(); i++) {
			start = addPE(e.get(i), start);
		}
		
		if(e.getName() != null) {
			FSAState<Set<Rule>> iEnd = fsm.addState();
			int label = -groups.get(e.getName()) - 1;
			fsm.addTransition(start, iEnd, label);
			start = iEnd;
		}
		
		return start;
		
	}

	private FSAState<Set<Rule>> addOR(BasePatternElement e, FSAState<Set<Rule>> start) {
		FSAState<Set<Rule>> end = fsm.addState();

		for(int i = 0; i < e.size(); i++) {
			FSAState<Set<Rule>> mStart = fsm.addState();
			fsm.addTransition(start, mStart, name.kazennikov.fsa.Constants.EPSILON);
			FSAState<Set<Rule>> mEnd = addPE(e.get(i), mStart);
			fsm.addTransition(mEnd, end, name.kazennikov.fsa.Constants.EPSILON);
		}

		return end;
	}


	public void toDot(PrintWriter pw) {
		fsm.toDot(pw);

	}
	
	public void toDot(String fileName) throws FileNotFoundException {
		fsm.toDot(fileName);
	}
	
	public void determinize() {
		InterFSM temp = new InterFSM();
		fsm.determinize(temp);
		fsm = temp;
	}
	
	public void minimize() {
		InterFSM temp = new InterFSM();
		fsm.minimize(temp);
		fsm = temp;
	}

	
	
	
	public int size() {
		return fsm.size();
	}
	





}
