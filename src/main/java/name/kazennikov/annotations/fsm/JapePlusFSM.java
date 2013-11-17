package name.kazennikov.annotations.fsm;

import gnu.trove.list.array.TIntArrayList;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import name.kazennikov.annotations.patterns.AnnotationMatcher;
import name.kazennikov.annotations.patterns.AnnotationMatcherPatternElement;
import name.kazennikov.annotations.patterns.AnnotationMatchers;
import name.kazennikov.annotations.patterns.AnnotationMatchers.ANDMatcher;
import name.kazennikov.annotations.patterns.BasePatternElement;
import name.kazennikov.annotations.patterns.PatternElement;
import name.kazennikov.annotations.patterns.PatternElement.Operator;
import name.kazennikov.annotations.patterns.RangePatternElement;
import name.kazennikov.annotations.patterns.Rule;
import name.kazennikov.fsa.FSA;
import name.kazennikov.fsa.FSAState;
import name.kazennikov.fsa.FSATransition;
import name.kazennikov.tools.Alphabet;

import com.google.common.base.Objects;

public class JapePlusFSM {
	public static final int GROUP_START = -1;
	
	protected static class IntermediateFSM extends FSA<Set<Rule>> {
		@Override
		public boolean isFinal(FSAState<Set<Rule>> s) {
			return !s.getFinals().isEmpty();
		}

		@Override
		public FSAState<Set<Rule>> addState() {
			FSAState<Set<Rule>> state = super.addState();
			state.setFinals(new HashSet<Rule>());
 			return state;
		}
		
		@Override
		public void mergeFinals(FSAState<Set<Rule>> dest, FSAState<Set<Rule>> src) {
			dest.getFinals().addAll(src.getFinals());
		}

		
		
		
	}

	
	
	public static class Builder {
		Alphabet<String> groups = new Alphabet<>();
		Alphabet<AnnotationMatcher> matchers = new Alphabet<>();
		IntermediateFSM fsm = new IntermediateFSM();
		
		
		public void addRule(Rule r) {
			FSAState<Set<Rule>> start = fsm.getStart();
			for(PatternElement e : r.lhs()) {
				start = addPE(e, start);
			}
			
			start.getFinals().add(r);
		}
		
		/**
		 * Add NFA states to start that represent pattern element
		 * @param e pattern element
		 * @param start current start state
		 * @return end state of the pattern element
		 */
		private FSAState<Set<Rule>> addPE(PatternElement e, FSAState<Set<Rule>> start) {
			FSAState<Set<Rule>> end = null;
			
			if(e instanceof AnnotationMatcherPatternElement) {
				end = fsm.addState();
				AnnotationMatcher matcher = ((AnnotationMatcherPatternElement) e).matcher();
				int label = matchers.get(matcher);
				
				fsm.addTransition(start, end, label);
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

		private FSAState<Set<Rule>> addRange(RangePatternElement e, FSAState<Set<Rule>> start) {
			FSAState<Set<Rule>> end = fsm.addState();
			
			for(int i = 0; i < e.min(); i++) {
				start = addPE(e.get(0), start);
			}

			fsm.addTransition(start, end, name.kazennikov.fsa.Constants.EPSILON); // skip optional parts
			
			if(e.max() == RangePatternElement.INFINITE) { // kleene start
				FSAState<Set<Rule>> mEnd = addPE(e.get(0), start);
				fsm.addTransition(mEnd, end, name.kazennikov.fsa.Constants.EPSILON);
				fsm.addTransition(mEnd, start, name.kazennikov.fsa.Constants.EPSILON);

			} else { // range [n,m]
				for(int i = e.min(); i < e.max(); i++) {
					start = addPE(e.get(0), start);
					fsm.addTransition(start, end, name.kazennikov.fsa.Constants.EPSILON);
				}
			} 
			
			
			return end;
		}

		private FSAState<Set<Rule>> addSeq(BasePatternElement e, FSAState<Set<Rule>> start) {
			if(e.getName() != null) {
				FSAState<Set<Rule>> iStart = fsm.addState();
				fsm.addTransition(start, iStart, GROUP_START);
				start = iStart;
			}
			
			for(int i = 0; i < e.size(); i++) {
				start = addPE(e.get(i), start);
			}
			
			if(e.getName() != null) {
				FSAState<Set<Rule>> iEnd = fsm.addState();
				int label = -groups.get(e.getName()) - 1;
				fsm.addTransition(start, iEnd, label);
				start = iEnd;
			}
			
			return start;
			
		}

		private FSAState<Set<Rule>> addOR(BasePatternElement e, FSAState<Set<Rule>> start) {
			FSAState<Set<Rule>> end = fsm.addState();

			for(int i = 0; i < e.size(); i++) {
				FSAState<Set<Rule>> mStart = fsm.addState();
				fsm.addTransition(start, mStart, name.kazennikov.fsa.Constants.EPSILON);
				FSAState<Set<Rule>> mEnd = addPE(e.get(i), mStart);
				fsm.addTransition(mEnd, end, name.kazennikov.fsa.Constants.EPSILON);
			}

			return end;
		}
		


		public void toDot(PrintWriter pw) {
			fsm.toDot(pw);

		}
		
		public void toDot(String fileName) throws FileNotFoundException {
			fsm.toDot(fileName);
		}
		
		public void determinize() {
			IntermediateFSM temp = new IntermediateFSM();
			fsm.determinize(temp);
			fsm = temp;
		}
		
		public void minimize() {
			IntermediateFSM temp = new IntermediateFSM();
			fsm.minimize(temp);
			fsm = temp;
		}
		
		public int size() {
			return fsm.size();
		}
		
		public JapePlusFSM build() {
			determinize();
			minimize();
			JapePlusFSM fsm = new JapePlusFSM(this);
			return fsm;
		}
	}

	

	public static class State {
		int number;
		List<Transition> transitions = new ArrayList<>();
		Set<Rule> rules;
		
		public int getNumber() {
			return number;
		}
		
		public List<Transition> getTransitions() {
			return transitions;
		}

		public boolean isFinal() {
			return !(rules != null && rules.isEmpty());
		}
		
		public Set<Rule> getRules() {
			return rules;
		}
		
		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("number", number)
					.toString();
		}
	}
	
	public class TypeMatcher {
		String type;
		
		TIntArrayList matchers = new TIntArrayList();
		TIntArrayList flags = new TIntArrayList(); // currently 1 - negated, 0 - non negated
		
		public String getType() {
			return type;
		}
		
		public TIntArrayList getMatchers() {
			return matchers;
		}
		
		public TIntArrayList getFlags() {
			return flags;
		}
		
	}
	
	public static class Transition {
		State src;
		State dest;
		
		int type;
		
		List<TypeMatcher> matchers = new ArrayList<>();
		
		public int getType() {
			return type;
		}
		
		public List<TypeMatcher> getMatchers() {
			return matchers;
		}
		
		public State getDest() {
			return dest;
		}
		
		public State getSrc() {
			return src;
		}
		
		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("from", src.number)
					.add("to", dest.number)
					.add("type", type)
					.toString();
		}

	}
	
	Alphabet<AnnotationMatcher> matchers = new Alphabet<>();
	Alphabet<String> groups;
	

	List<State> states = new ArrayList<>();	
	List<Transition> transitions = new ArrayList<>();
	


	Map<FSAState<Set<Rule>>, State> stateMap = new HashMap<>();
	Map<FSATransition<Set<Rule>>, Transition> transitionMap = new HashMap<>();
	
	State start;
	
	protected JapePlusFSM(Builder builder) {
		this.groups = builder.groups;
		
		// create states
		for(int i = 0; i < builder.fsm.size(); i++) {
			FSAState<Set<Rule>> s0 = builder.fsm.getState(i);
			State s = new State();
			s.number = i;
			s.rules = s0.getFinals();
			stateMap.put(s0, s);
			states.add(s);
		}
		
		// create transitions
		for(int i = 0; i < builder.fsm.size(); i++) {
			FSAState<Set<Rule>> s0 = builder.fsm.getState(i);
			for(FSATransition<Set<Rule>> t0 : s0.getTransitions()) {
				State s = stateMap.get(s0);
				Transition t = convertTransition(t0, builder.matchers);
				s.transitions.add(t);
				transitions.add(t);
			}
		}
		
		start = stateMap.get(builder.fsm.getStart());
	}

	private Transition convertTransition(FSATransition<Set<Rule>> t0, Alphabet<AnnotationMatcher> builderAlphabet) {
		State srcState = stateMap.get(t0.getSrc());
		State destState = stateMap.get(t0.getDest());
		
		Transition t = new Transition();
		t.src = srcState;
		t.dest = destState;
		

		t.type = t0.getLabel();
		
		if(t0.getLabel() >= 0) { // convert annotation matcher
			AnnotationMatcher m = builderAlphabet.get(t0.getLabel());
			convertMatcher(m, t.matchers);
		}
		
		return t;
	}
	
	TypeMatcher getMatcherFor(List<TypeMatcher> typeMatchers, String name) {
		for(TypeMatcher m : typeMatchers) {
			if(m.type == name || m.type.equals(name)) // allow null m.type value for wildcard matches
				return m;
		}
		
		TypeMatcher m = new TypeMatcher();
		m.type = name;
		typeMatchers.add(m);
		return m;
	}
	
	void convertMatcher(AnnotationMatcher m, List<TypeMatcher> typeMatchers) {
		
		if(m instanceof ANDMatcher) {
			for(AnnotationMatcher atom : ((ANDMatcher)m).getMatchers()) {
				convertMatcher(atom, typeMatchers);
			}
		} else if(m instanceof AnnotationMatchers.NOTAnnotationMatcher) {
			AnnotationMatcher inner = ((AnnotationMatchers.NOTAnnotationMatcher) m).getMatcher();
			TypeMatcher matcher = getMatcherFor(typeMatchers, inner.getType());
			matcher.matchers.add(matchers.get(inner));
			matcher.flags.add(1);
		} else {
			TypeMatcher matcher = getMatcherFor(typeMatchers, m.getType());
			matcher.matchers.add(matchers.get(m));
			matcher.flags.add(0);
		}
	}
	
	
	public State getState(int index) {
		return states.get(index);
	}
	
	public String getGroupName(int index) {
		return groups.get(index);
	}

	public State getStart() {
		return start;
	}

	public AnnotationMatcher getMatcher(int key) {
		return matchers.get(key);
	}
	
	
}
