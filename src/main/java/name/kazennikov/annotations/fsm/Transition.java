package name.kazennikov.annotations.fsm;

import java.util.List;

import name.kazennikov.annotations.patterns.AnnotationMatcher;

public class Transition {
	AnnotationMatcher matcher;
	List<String> bindings;
	State target;
	
	public List<String> getBindings() {
		return bindings;
	}
	
	public State getTarget() {
		return target;
	}
	
	public AnnotationMatcher getMatcher() {
		return matcher;
	}

}
