package name.kazennikov.annotations.fsm;

import gnu.trove.TIntCollection;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;

public class IntFSM {
	
	public static final int EPSILON_LABEL = 0;
	
	public static class State {
		int number;
		TIntSet finals;
		List<Transition> transitions = new ArrayList<>();
		
		public void addTransition(State dest, int label) {
			Transition t = new Transition();
			t.dest = dest;
			t.label = label;
			transitions.add(t);
		}
		
		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("number", number)
					.add("transitions", transitions)
					.toString();
		}
		
		public boolean isFinal() {
			return finals != null && !finals.isEmpty();
		}
		
		public void addFinal(int finalId) {
			if(finals == null)
				finals = new TIntHashSet();
			
			finals.add(finalId);
		}
		
		public void addFinals(TIntCollection finals) {
			if(finals == null)
				finals = new TIntHashSet();
			finals.addAll(finals);
		}
		
		public void setFinalsFrom(Collection<? extends State> states) {
			for(State s : states) {
				if(s.isFinal()) {
					addFinals(s.finals);
				}
			}
		}
	}
	
	public static class Transition {
		int label;
		State dest;
		
		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("dest", dest.number)
					.add("label", label)
					.toString();
		}
		
		public boolean isEpsilon() {
			return label == EPSILON_LABEL;
		}
	}
	
	State start;
	List<State> states;
	
	public IntFSM() {
		start = addState();
	}
	
	public int size() {
		return states.size();
	}
	
	public State getStart() {
		return start;
	}
	
	public State addState() {
		State s = new State();
		s.number = states.size();
		return s;
	}
	public void addTransition(int from, int label, int to) {
		State src = states.get(from);
		State dest = states.get(to);
		src.addTransition(dest, label);
	}
	
	
	public IntFSM reverse() {
		IntFSM fsm = new IntFSM();
		
		for(int i = 1; i < size(); i++) {
			fsm.addState();
		}
		
		for(int i = 0; i < size(); i++) {
			for(Transition t : states.get(i).transitions) {
				fsm.addTransition(t.dest.number, t.label, i);
			}
		}
		
		return fsm;
	}
	
	public Set<State> lambdaClosure(Set<State> states) {
		LinkedList<State> list = new LinkedList<State>(states);
		Set<State> closure = new HashSet<State>(states);

		while(!list.isEmpty()) {
			State current = list.removeFirst();
			for(Transition t : current.transitions) {
				if(t.isEpsilon()) {
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
	
	public IntFSM determinize() {
		IntFSM fsm = new IntFSM();
		
		Map<Set<State>, State> newStates = new HashMap<>();
		Set<Set<State>> dStates = new HashSet<>();
		LinkedList<Set<State>> unmarkedDStates = new LinkedList<Set<State>>();
		
		Set<State> currentDState = new HashSet<State>();


		currentDState.add(start);
		currentDState = lambdaClosure(currentDState);
		dStates.add(currentDState);
		unmarkedDStates.add(currentDState);
		
		newStates.put(currentDState, fsm.start);
		fsm.start.setFinalsFrom(currentDState);
		
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
					newState.setFinalsFrom(next);
				}

				State currentState = newStates.get(currentDState);
				State newState = newStates.get(next);
				fsm.addTransition(currentState.number, newState.number, label);
			}
		}
		return fsm;
	}

	
	/**
	 * Converts this epsilon-NFA to epsilon-free NFA. Non-destructive procedure
	 * 
	 * @return fresh epsilon free NFA
	 */
	public IntFSM epsilonFreeFSM() {

		Map<Set<State>, State> newStates = new HashMap<>();
		Set<Set<State>> dStates = new HashSet<>();
		LinkedList<Set<State>> unmarkedDStates = new LinkedList<Set<State>>();
		Set<State> currentDState = new HashSet<State>();


		currentDState.add(start);
		currentDState = lambdaClosure(currentDState);
		dStates.add(currentDState);
		unmarkedDStates.add(currentDState);
		IntFSM fsm = new IntFSM();

		newStates.put(currentDState, fsm.start);

		fsm.start.setFinalsFrom(currentDState);

		while(!unmarkedDStates.isEmpty()) {
			currentDState = unmarkedDStates.removeFirst();

			for(State state: currentDState) {
				for(Transition t : state.transitions) {

					// skip epsilon transitions
					if(t.label == 0)
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
						newState.setFinalsFrom(newDState);
					}

					State currentState = newStates.get(currentDState);
					State newState = newStates.get(newDState);
					fsm.addTransition(currentState.number, newState.number, t.label);
				}
			}

		}

		return fsm;
	}
	
	private TIntSet labels(Set<State> states) {
		TIntHashSet set = new TIntHashSet();
		
		for(State s : states) {
			for(Transition t : s.transitions) {
				if(!t.isEpsilon())
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

	


	
	
	
	
	

}
