package name.kazennikov.annotations.fsm;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

public class IntFSM {
	
	public static class State {
		int number;
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
	

}
