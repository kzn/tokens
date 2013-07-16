package name.kazennikov.annotations.fsm;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import name.kazennikov.annotations.patterns.AnnotationMatcher;
import name.kazennikov.annotations.patterns.RHS;

import com.google.common.base.Objects;

public class State {
	int number;
	List<Transition> transitions = new ArrayList<>();
	List<RHS> rhs = new ArrayList<>();
	int priority = -1;

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


	private String escape(String s) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if(ch == '"')
				sb.append("\\");
			sb.append(ch);
		}

		return sb.toString();
	}


	public void toDot(PrintWriter pw, Set<State> visited) {
		if(visited.contains(this))
			return;

		visited.add(this);

		for(Transition t : transitions) {
			pw.printf("%d -> %d [label=\"%s%s\"];%n", number, t.target.number, escape(t.matcher != null? t.matcher.toString() : "null"), t.bindings);
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
}
