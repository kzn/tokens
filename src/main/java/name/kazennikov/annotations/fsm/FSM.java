package name.kazennikov.annotations.fsm;


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
import name.kazennikov.annotations.patterns.RangePatternElement;
import name.kazennikov.annotations.patterns.Rule;


public class FSM {
	List<State> states = new ArrayList<>();
	
	State start;
	
	public FSM() {
		this.start = addState();
	}
	
	public void addRule(Rule r) {
		State start = this.start;
		for(PatternElement e : r.lhs()) {
			start = addPE(start, e, new ArrayList<String>());
		}
		
		start.rhs.addAll(r.rhs());

	}
	
	public State addState() {
		State state = new State();
		state.number = states.size();
		states.add(state);
		return state;
	}
	
	/**
	 * Add NFA states to start that represent pattern element
	 * @param start current start state
	 * @param e pattern element
	 * @return end state of the pattern element
	 */
	private State addPE(State start, PatternElement e, List<String> bindings) {
		State end = null;
		
		if(e instanceof AnnotationMatcherPatternElement) {
			end = addState();
			AnnotationMatcher matcher = ((AnnotationMatcherPatternElement) e).matcher();
			
			start.addTransition(end, matcher, bindings);
		} else if(e instanceof BasePatternElement) {
			// OR or SEQ
			if(e.op() == Operator.OR) {
				end = addOR((BasePatternElement)e, start, bindings);
			} else if(e.op() == Operator.SEQ) {
				end = addSeq((BasePatternElement)e, start, bindings);
			}
			
		} else if(e instanceof RangePatternElement) {
			end = addRange((RangePatternElement)e, start, bindings);
		}

		return end;
	}

	private State addRange(RangePatternElement e, State start, List<String> bindings) {
		State end = addState();
		
		for(int i = 0; i < e.min(); i++) {
			start = addPE(start, e.get(0), bindings);
		}
		
		if(e.max() == RangePatternElement.INFINITE) {
			State mEnd = addPE(start, e.get(0), bindings);
			mEnd.addTransition(end, null, bindings);
			mEnd.addTransition(start, null, bindings);
		} else if(e.min() != e.max()) {
			for(int i = e.min(); i < e.max(); i++) {
				start = addPE(start, e.get(0), bindings);
				start.addTransition(end, null, bindings);
			}
		} else {
			start.addTransition(end, null, bindings);
		}
		
		
		return end;
	}

	private State addSeq(BasePatternElement e, State start, List<String> bindings) {
		if(e.getName() != null)
			bindings.add(e.getName());
		
		for(int i = 0; i < e.size(); i++) {
			start = addPE(start, e.get(i), bindings);
		}
		
		if(e.getName() != null)
			bindings.remove(bindings.size() - 1);
		
		return start;
		
	}

	private State addOR(BasePatternElement e, State start, List<String> bindings) {
		State end = addState();

		for(int i = 0; i < e.size(); i++) {
			State mStart = addState();
			start.addTransition(mStart, null, bindings);
			State mEnd = addPE(mStart, e.get(i), bindings);
			mEnd.addTransition(end, null, bindings);
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
				if(t.matcher == null) {
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

	
	/**
	 * Converts this epsilon-NFA to epsilon-free NFA. Non-destructive procedure
	 * 
	 * @return fresh epsilon free NFA
	 */
	public FSM epsilonFreeFSM() {

		Map<Set<State>, State> newStates = new HashMap<>();
		Set<Set<State>> dStates = new HashSet<>();
		LinkedList<Set<State>> unmarkedDStates = new LinkedList<Set<State>>();
		Set<State> currentDState = new HashSet<State>();


		currentDState.add(start);
		currentDState = lambdaClosure(currentDState);
		dStates.add(currentDState);
		unmarkedDStates.add(currentDState);
		FSM fsm = new FSM();

		newStates.put(currentDState, fsm.start);

		for(State c : currentDState) {
			if(c.isFinal()) {
				fsm.start.rhs = c.rhs;
				break;
			}
		}

		while(!unmarkedDStates.isEmpty()) {
			currentDState = unmarkedDStates.removeFirst();

			for(State state: currentDState) {
				for(Transition t : state.transitions) {

					// skip epsilon transitions
					if(t.matcher == null)
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

						for(State currentInnerState : newDState) {
							if(currentInnerState.isFinal()) {
								newState.rhs = currentInnerState.rhs;
								break;
							}
						}
					}

					State currentState = newStates.get(currentDState);
					State newState = newStates.get(newDState);
					currentState.addTransition(newState, t.matcher, t.bindings);

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



}
