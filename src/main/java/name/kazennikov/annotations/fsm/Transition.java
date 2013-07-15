package name.kazennikov.annotations.fsm;

import java.util.List;

import name.kazennikov.annotations.patterns.AnnotationMatcher;

public class Transition {
	AnnotationMatcher matcher;
	List<String> bindings;
	State target;

}
