package name.kazennikov.annotations.fsm;

import java.util.ArrayList;
import java.util.List;

import name.kazennikov.annotations.patterns.AnnotationMatcher;
import name.kazennikov.annotations.patterns.RHS;

public class State {
	int number;
	List<Transition> transitions = new ArrayList<>();
	List<RHS> rhs = new ArrayList<>();
	
	public boolean isFinal() {
		return !rhs.isEmpty();
	}
	
	public void addTransition(State to, AnnotationMatcher matcher, List<String> bindings) {
		Transition t = new Transition();
		t.bindings = new ArrayList<>(bindings);
		t.matcher = matcher;
		t.target = to;
		transitions.add(t);
	}
}
