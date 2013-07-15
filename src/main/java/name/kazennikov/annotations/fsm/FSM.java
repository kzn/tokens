package name.kazennikov.annotations.fsm;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
	
	public FSM(Rule r) {
		this.start = addState();
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

}
