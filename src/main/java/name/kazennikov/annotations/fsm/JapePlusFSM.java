package name.kazennikov.annotations.fsm;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.procedure.TIntIntProcedure;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import name.kazennikov.annotations.automaton.Constants;
import name.kazennikov.annotations.automaton.GenericWholeArrray;
import name.kazennikov.annotations.automaton.IntSequence;
import name.kazennikov.annotations.patterns.AnnotationMatcher;
import name.kazennikov.annotations.patterns.AnnotationMatcherPatternElement;
import name.kazennikov.annotations.patterns.BasePatternElement;
import name.kazennikov.annotations.patterns.PatternElement;
import name.kazennikov.annotations.patterns.PatternElement.Operator;
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
			return label == EPSILON;
		}
		
		@Override
		public String toString() {
			return String.format("{src=%d, label=%d, dest=%d}", src.getNumber(), label, dest.getNumber());
		}
		

	}


	List<State> states = new ArrayList<>();	
	List<Transition> transitions = new ArrayList<>();
	
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
		
		start.actions.add(r);
	}
	
	public State addState() {
		State state = new State();
		state.number = states.size();
		states.add(state);
		return state;
	}
	
	
	public void addTransition(State from, State to, int label) {
		Transition t = from.addTransition(to, label);
		transitions.add(t);
		
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
		
		for(State s : states) {
			for(Transition t : s.transitions) {
				pw.printf("%d -> %d [label=\"%d\"];%n", t.src.number, t.dest.number, t.label);
			}

			if(s.isFinal()) {
				pw.printf("%d [shape=doublecircle];%n", s.number);
			}

		}
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
					State target = t.dest;
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
					next.add(t.dest);
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
					if(t.label == Transition.EPSILON)
						continue;


					State target = t.dest;
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
				s.actions = s0.actions;
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
	
	
	
	
	private void trReverse() {
		for(State s : states) {
			s.transitions.clear();
		}
	
		
		for(Transition t : transitions) {
			State temp = t.dest;
			t.dest = t.src;
			t.src = temp;
			
			states.get(t.src.number).transitions.add(t);
		}
		
		
	
	}
	
	private void trSort() {
		Collections.sort(transitions, new Comparator<Transition>() {

			@Override
			public int compare(Transition o1, Transition o2) {
				int res = Integer.compare(o1.src.number, o2.src.number);
				if(res != 0)
					return res;
				res = Integer.compare(o1.label, o2.label);
				if(res != 0)
					return res;
				res = Integer.compare(o1.dest.number, o2.dest.number);
				
				return res;
			}
		});
	}
	
	public class AutomatonMinimizationData {
		
		TIntIntHashMap labelsMap = new TIntIntHashMap();
		// states:
		protected int[] statesClassNumber; // state -> class
		
		/*
		 *  linked list for state classes
		 *  stateNext[i] - next member of class for i-th state
		 *  statePrev[i] - previous member of class for i-th state
		 */
		protected int[] statesNext; // следующее состояния данного класса
		protected int[] statesPrev; // предыдущее состояние этого класса
		
		protected int statesStored; // число классов

		// classes:
		protected int[] classesFirstState; // номер первого состояния для класса i
		protected int[] classesPower; // размер класса (число состояний в классе)
		protected int[] classesNewPower;
		protected int[] classesNewClass;
		protected int[] classesFirstLetter;
		protected int[] classesNext;
		protected int classesStored; // число классов
		protected int classesAlloced;
		protected int firstClass;

		// letters:
		protected int[] lettersLetter;
		protected int[] lettersNext;
		protected int lettersStored; // число меток перехода
		protected int lettersAlloced;

		public AutomatonMinimizationData(int statesStored) {
			this.statesStored = statesStored;
			statesClassNumber = new int[statesStored];
			statesNext = new int[statesStored];
			statesPrev = new int[statesStored];

			classesAlloced = 1024;
			classesFirstState = new int[classesAlloced];
			classesPower = new int[classesAlloced];
			classesNewPower = new int[classesAlloced];
			classesNewClass = new int[classesAlloced];
			classesFirstLetter = new int[classesAlloced];
			classesNext = new int[classesAlloced];
			firstClass = Constants.NO;

			lettersAlloced = 1024;
			lettersLetter = new int[lettersAlloced];
			lettersNext = new int[lettersAlloced];
		}
		
		/*
		 * Процедуры строят цепочки состояний и переходов в обратном порядке.
		 * Т.е. firstClass - на самом деле последнее по порядку добавления.
		 * Таким образом получается, что идут цепочки:
		 * 1. классов. от firstClass по classsesNext
		 * 2. classesFirstState - первое состояние класса. Можно обходить по classes[
		 */
		/**
		 * Adds state to given state class
		 * 
		 * @param state state number
		 * @param cls class number
		 */
		protected void addState(int state, int cls) {
			
			// linked list addFirst() method
			statesNext[state] = classesFirstState[cls];
			if (classesFirstState[cls] != Constants.NO) {
				statesPrev[classesFirstState[cls]] = state;
			}
			statesPrev[state] = Constants.NO;
			
			statesClassNumber[state] = cls;
			classesFirstState[cls] = state;
			classesPower[cls]++;
		}

		protected void addLetter(int cls, int letter) {
			// reallocate letters if needed
			if (lettersStored == lettersAlloced) {
				int mem = lettersAlloced + lettersAlloced / 4; // 1.25 growth rate
				lettersLetter = GenericWholeArrray.realloc(lettersLetter, mem, lettersStored);
				lettersNext = GenericWholeArrray.realloc(lettersNext, mem, lettersStored);
				lettersAlloced = mem;
			}
			
			// установить первую метку перехода для класса
			if (classesFirstLetter[cls] == Constants.NO) {
				classesNext[cls] = firstClass;
				firstClass = cls;
			}
			
			lettersLetter[lettersStored] = letter;
			lettersNext[lettersStored] = classesFirstLetter[cls];
			classesFirstLetter[cls] = lettersStored;
			lettersStored++;
		}

		protected void reallocClasses() {
			int mem = classesAlloced + classesAlloced / 4;
			
			classesFirstState = GenericWholeArrray.realloc(classesFirstState, mem, classesStored);
			classesPower = GenericWholeArrray.realloc(classesPower, mem, classesStored);
			classesNewPower = GenericWholeArrray.realloc(classesNewPower, mem, classesStored);
			classesNewClass = GenericWholeArrray.realloc(classesNewClass, mem, classesStored);
			classesFirstLetter = GenericWholeArrray.realloc(classesFirstLetter, mem, classesStored);
			classesNext = GenericWholeArrray.realloc(classesNext, mem, classesStored);
			classesAlloced = mem;
		}

		protected void moveState(int state, int newClass) {
			int curClass = statesClassNumber[state];
			
			if (statesPrev[state] == Constants.NO) {
				classesFirstState[curClass] = statesNext[state];
			} else {
				statesNext[statesPrev[state]] = statesNext[state];
			}
			
			if (statesNext[state] != Constants.NO) {
				statesPrev[statesNext[state]] = statesPrev[state];
			}
			
			addState(state, newClass);
		}
		
		public void mapTransitions() {
			labelsMap.put(0, 0);
			
			// map labels to [0 ... n] values for correct algorithm work
			for(Transition t : transitions) {
				
				if(!labelsMap.containsKey(t.label)) {
					int label = labelsMap.size();
					labelsMap.put(t.label, label);
					t.label = label;
				} else {
					t.label = labelsMap.get(t.label);
				}
			}
		}
		
		public void unmapTransitions() {
			final TIntIntHashMap map = new TIntIntHashMap();
			labelsMap.forEachEntry(new TIntIntProcedure() {
				
				@Override
				public boolean execute(int a, int b) {
					map.put(b, a);
					return true;
				}
			});
			
			for(Transition t : transitions) {
				t.label = map.get(t.label);
			}
		}
	}

	
	protected AutomatonMinimizationData hopcroftMinimize() {
		// reverse transitions
		trReverse();
		trSort();
		
		AutomatonMinimizationData data = new AutomatonMinimizationData(states.size());
		data.mapTransitions();
		
		int labelsStored = data.labelsMap.size();
		
		
		IntSequence classes = new IntSequence(); // существующие классы. изначально - final - каждый в отдельный класс
		int[] finalties = new int[states.size()];
		int finalClassCount = 0;
		
		for (int i = 0; i < states.size(); i++) {
			
			finalties[i] = -1;
			State s = states.get(i);
			
			if (s.isFinal()) {
				finalties[i] = s.number;
				finalClassCount++;
			}
			classes.addIfDoesNotExsist(finalties[i]);
		}


		
		if (finalClassCount == 0) {
			return data;
		}

		// инициализаця данных о минимизации
		for (int j = 0; j < classes.seqStored; j++) {
			data.classesFirstState[j] = Constants.NO;
			data.classesNewClass[j] = Constants.NO;
			data.classesNewPower[j] = 0;
			data.classesPower[j] = 0;
			data.classesFirstLetter[j] = Constants.NO;
			data.classesNext[j] = Constants.NO;
		}
		data.classesStored = classes.seqStored;

		// добавить состояния по классам
		for (int i = 0; i < states.size(); i++) {
			data.addState(i, classes.contains(finalties[i]));
		}

		// добавить метки переходов (по классам)?
		for (int i = 0; i < labelsStored; i++) {
			for (int j = 0; j < data.classesStored; j++) {
				data.addLetter(j, i);
			}
		}
		
		IntSequence states = new IntSequence();
		classes.seqStored = 0;
		GenericWholeArrray alph = new GenericWholeArrray(GenericWholeArrray.TYPE_BIT, labelsStored);

		while (data.firstClass != Constants.NO) {
			int q1 = data.firstClass; 
			int a = data.lettersLetter[data.classesFirstLetter[q1]];
			data.classesFirstLetter[q1] = data.lettersNext[data.classesFirstLetter[q1]];
			
			if (data.classesFirstLetter[q1] == Constants.NO) {
				data.firstClass = data.classesNext[q1];
			}
			
			classes.seqStored = 0;
			states.seqStored = 0;

			// iterate through states of the class q1
			for (int state = data.classesFirstState[q1]; state != Constants.NO; state = data.statesNext[state]) {
				State s = this.states.get(state);
				for(Transition t : s.transitions) {
					if(t.label == a) {
						int q0 = data.statesClassNumber[t.dest.number];
						states.add(t.dest.number);
						if (data.classesNewPower[q0] == 0) {
							classes.add(q0);
						}
						data.classesNewPower[q0]++;
					}
				}
			}
			
			for (int j = 0; j < states.seqStored; j++) {
				int q0 = data.statesClassNumber[states.seq[j]];
				
				if (data.classesNewPower[q0] == data.classesPower[q0]) {
					continue;
				}
				
				if (data.classesNewClass[q0] == Constants.NO) {
					
					if (data.classesStored == data.classesAlloced) {
						data.reallocClasses();
					}
					
					data.classesNewClass[q0] = data.classesStored;
					data.classesFirstState[data.classesStored] = Constants.NO;
					data.classesNewClass[data.classesStored] = Constants.NO;
					data.classesNewPower[data.classesStored] = 0;
					data.classesPower[data.classesStored] = 0;
					data.classesFirstLetter[data.classesStored] = Constants.NO;
					data.classesNext[data.classesStored] = Constants.NO;
					data.classesStored++;
				}
				data.moveState(states.seq[j], data.classesNewClass[q0]);
			}
			
			for (int i = 0; i < classes.seqStored; i++) {
				int q0 = classes.seq[i];
				
				if (data.classesNewPower[q0] != data.classesPower[q0]) {
					data.classesPower[q0] -= data.classesNewPower[q0];
					
					for (int j = 1; j < labelsStored; j++) {
						alph.setElement(j, 0);
					}
					
					for (int j = data.classesFirstLetter[q0]; j != Constants.NO; j = data.lettersNext[j]) {
						data.addLetter(data.classesNewClass[q0], data.lettersLetter[j]);
						alph.setElement(data.lettersLetter[j], Constants.NO);
					}
					
					for (int j = 1; j < labelsStored; j++) {
						if (alph.elementAt(j) == Constants.NO) {
							continue;
						}
						
						if (data.classesPower[q0] < data.classesPower[data.classesNewClass[q0]]) {
							data.addLetter(q0, j);
						} else {
							data.addLetter(data.classesNewClass[q0], j);
						}
						
					}
				}

				data.classesNewPower[q0] = 0;
				data.classesNewClass[q0] = Constants.NO;
			}
		}
		return data;
	}
	
	public JapePlusFSM minimize() {
		AutomatonMinimizationData data = hopcroftMinimize();
		
		JapePlusFSM fsm = new JapePlusFSM();
		
		if (data.classesStored == 0) {
			return fsm;
		}
		
		data.unmapTransitions();
		trReverse();
		trSort();
		// add states
		for(int i = 0; i < data.classesStored; i++) {
			fsm.addState();
		}

		// add transitions
		for (int i = 0; i < data.classesStored; i++) {
			int state = data.classesFirstState[i];
			State s = states.get(state);

			// set start state
			if(s.number == 0) {
				fsm.start = fsm.states.get(i);
			}
			
			for(Transition t : s.transitions) {
				fsm.addTransition(fsm.states.get(i), fsm.states.get(data.statesClassNumber[t.dest.number]), t.label);
			}
		}
		
		// add finals
		for(int i = 0; i < data.classesStored; i++) {
			int state = data.classesFirstState[i];
			State s0 = states.get(state);

			State s = fsm.states.get(i);
			
			if(s0.isFinal()) {
				s.actions.addAll(s0.actions);
			}
		}

				
		return fsm;
	}




}
