package name.kazennikov.annotations.fsm;

import java.util.ArrayList;
import java.util.List;

import name.kazennikov.annotations.patterns.AnnotationMatcher;
import name.kazennikov.annotations.patterns.AnnotationMatcherPatternElement;
import name.kazennikov.annotations.patterns.PatternElement;
import name.kazennikov.annotations.patterns.Rule;


public class FSM {
	
	State start = new State();
	
	public FSM(Rule r) {
		State start = this.start;
		for(PatternElement e : r.lhs()) {
			start = addPE(start, e, new ArrayList<String>());
		}
	}
	
	/**
	 * Add NFA states to start that represent pattern element
	 * @param start current start state
	 * @param e pattern element
	 * @return end state of the pattern element
	 */
	private State addPE(State start, PatternElement e, List<String> bindings) {
		State end = new State();
		
		if(e instanceof AnnotationMatcherPatternElement) {
			AnnotationMatcher matcher = ((AnnotationMatcherPatternElement) e).matcher();
			start.addTransition(end, matcher, bindings);
		}

		return end;
	}

}
