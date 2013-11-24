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

/**
 * FSM for single JAPE rules file (single phase)
 * 
 * This FSM is based on JAPE+ implementation and essentially does following:
 * <ul>
 * <li> compiles the rules into a FSM
 * <li> determinizes it as JAPE+ does (marks named group start as -1, and group
 * close with respective named label)
 * <li> minimizes it
 * <li> decomposes annotation matchers to atomic matchers
 * </ul>
 * 
 * @author Anton Kazennikov
 *
 */
public class JapePlusFSM {
	public static final int GROUP_START = -1;
	
	/**
	 * Intermediate FSM representation for ease of determinization and minimization
	 * 
	 * @author Anton Kazennikov
	 *
	 */
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
		
		
		/**
		 * Add new rule to this FSMBuilder
		 * 
		 * @param rule rule to add
		 */
		public void addRule(Rule rule) {
			FSAState<Set<Rule>> start = fsm.getStart();
			for(PatternElement e : rule.lhs()) {
				start = addPE(e, start);
			}
			
			start.getFinals().add(rule);
		}
		
		/**
		 * Add NFA states to state that represent pattern element
		 * 
		 * @param e pattern element
		 * @param state current start state
		 * @return end state of the pattern element
		 */
		private FSAState<Set<Rule>> addPE(PatternElement e, FSAState<Set<Rule>> state) {
			FSAState<Set<Rule>> end = null;
			
			if(e instanceof AnnotationMatcherPatternElement) {
				end = fsm.addState();
				AnnotationMatcher matcher = ((AnnotationMatcherPatternElement) e).matcher();
				int label = matchers.get(matcher);
				
				fsm.addTransition(state, end, label);
			} else if(e instanceof BasePatternElement) {
				// OR or SEQ
				if(e.op() == Operator.OR) {
					end = addOR((BasePatternElement)e, state);
				} else if(e.op() == Operator.SEQ) {
					end = addSeq((BasePatternElement)e, state);
				}
				
			} else if(e instanceof RangePatternElement) {
				end = addRange((RangePatternElement)e, state);
			}

			return end;
		}

		/**
		 * Add range pattern element to the FSMBuilder
		 * 
		 * @param e range pattern element
		 * @param state start state
		 * @return end state of the pattern element
		 */
		private FSAState<Set<Rule>> addRange(RangePatternElement e, FSAState<Set<Rule>> state) {
			FSAState<Set<Rule>> end = fsm.addState();
			
			for(int i = 0; i < e.min(); i++) {
				state = addPE(e.get(0), state);
			}

			fsm.addTransition(state, end, name.kazennikov.fsa.Constants.EPSILON); // skip optional parts
			
			if(e.max() == RangePatternElement.INFINITE) { // kleene start
				FSAState<Set<Rule>> mEnd = addPE(e.get(0), state);
				fsm.addTransition(mEnd, end, name.kazennikov.fsa.Constants.EPSILON);
				fsm.addTransition(mEnd, state, name.kazennikov.fsa.Constants.EPSILON);

			} else { // range [n,m]
				for(int i = e.min(); i < e.max(); i++) {
					state = addPE(e.get(0), state);
					fsm.addTransition(state, end, name.kazennikov.fsa.Constants.EPSILON);
				}
			} 
			
			
			return end;
		}

		/**
		 * Add sequence pattern element to the FSMBuilder
		 * 
		 * @param e sequence pattern element
		 * @param state start state
		 * @return end state of the pattern element
		 */
		private FSAState<Set<Rule>> addSeq(BasePatternElement e, FSAState<Set<Rule>> state) {
			if(e.getName() != null) {
				FSAState<Set<Rule>> iStart = fsm.addState();
				fsm.addTransition(state, iStart, GROUP_START);
				state = iStart;
			}
			
			for(int i = 0; i < e.size(); i++) {
				state = addPE(e.get(i), state);
			}
			
			if(e.getName() != null) {
				FSAState<Set<Rule>> iEnd = fsm.addState();
				int label = -groups.get(e.getName()) - 1;
				fsm.addTransition(state, iEnd, label);
				state = iEnd;
			}
			
			return state;
			
		}

		/**
		 * Add alternation(or) pattern element to the FSMBuilder
		 * 
		 * @param e sequence pattern element
		 * @param state start state
		 * @return end state of the pattern element
		 */
		private FSAState<Set<Rule>> addOR(BasePatternElement e, FSAState<Set<Rule>> state) {
			FSAState<Set<Rule>> end = fsm.addState();

			for(int i = 0; i < e.size(); i++) {
				FSAState<Set<Rule>> mStart = fsm.addState();
				fsm.addTransition(state, mStart, name.kazennikov.fsa.Constants.EPSILON);
				FSAState<Set<Rule>> mEnd = addPE(e.get(i), mStart);
				fsm.addTransition(mEnd, end, name.kazennikov.fsa.Constants.EPSILON);
			}

			return end;
		}
		

		/**
		 * Write current FSM to dot format into print writer
		 * 
		 * @param pw output print writer
		 */
		public void toDot(PrintWriter pw) {
			fsm.toDot(pw);

		}
		
		/**
		 * Write current FSM to dot format
		 * 
		 * @param fileName ouput filename
		 * @throws FileNotFoundException
		 */
		public void toDot(String fileName) throws FileNotFoundException {
			fsm.toDot(fileName);
		}
		
		/**
		 * Convert current NFA to DFA
		 */
		public void determinize() {
			IntermediateFSM temp = new IntermediateFSM();
			fsm.determinize(temp);
			fsm = temp;
		}
		
		/**
		 * Minimizes current DFA (assumes that current FSM is a DFA one)
		 */
		public void minimize() {
			IntermediateFSM temp = new IntermediateFSM();
			fsm.minimize(temp);
			fsm = temp;
		}
		
		/**
		 * Get size of the current FSM (in states)
		 * 
		 * @return number of states
		 */
		public int size() {
			return fsm.size();
		}
		
		/**
		 * Builds a JapePlusFSM for document processing
		 * @return
		 */
		public JapePlusFSM build() {
			determinize();
			minimize();
			JapePlusFSM fsm = new JapePlusFSM(this);
			return fsm;
		}
	}

	

	/**
	 * Single FSM state
	 * 
	 * @author Anton Kazennikov
	 *
	 */
	public static class State {
		int number;
		List<Transition> transitions = new ArrayList<>();
		Set<Rule> rules;
		boolean isFinal;
		
		public int getNumber() {
			return number;
		}
		
		public List<Transition> getTransitions() {
			return transitions;
		}

		public boolean isFinal() {
			return isFinal;
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
	
	/**
	 * Group of AnnotationMatchers with same type in the transition constraint
	 * 
	 * @author Anton Kazennikov
	 *
	 */
	public class TypeMatcher {
		String type;
		
		TIntArrayList matchersIndexes = new TIntArrayList();
		List<AnnotationMatcher> matchers = new ArrayList<>();
		TIntArrayList flags = new TIntArrayList(); // currently 1 - negated, 0 - non negated
		
		public String getType() {
			return type;
		}
		
		public List<AnnotationMatcher> getMatchers() {
			return matchers;
		}
		
		public TIntArrayList getMatchersIndexes() {
			return matchersIndexes;
		}
		
		public TIntArrayList getFlags() {
			return flags;
		}
		
	}
	
	/**
	 * FSM Transition<p>
	 * 
	 * The label is a set of constraints on the same annotation type
	 * 
	 * @author Anton Kazennikov
	 *
	 */
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
			s.isFinal = s.rules != null && !s.rules.isEmpty();
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

	/**
	 * Converts transition of an intermediate FSM to JapePlusFSM
	 * 
	 * @param t0 intermediate FSM transition
	 * @param builderAlphabet alphabet for annotation matchers
	 * @return converted transition
	 */
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
	
	/**
	 * Get (or adds a new one) type matcher from the list
	 * 
	 * @param typeMatchers list of type matchers
	 * @param name query name
	 * @return type matcher for given name
	 */
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
	
	/**
	 * Decomposes an annotation matcher to atomic matchers and adds them to respective type
	 * matchers
	 * 
	 * @param m complex annotation matcher
	 * @param typeMatchers list of type matchers
	 */
	void convertMatcher(AnnotationMatcher m, List<TypeMatcher> typeMatchers) {
		
		if(m instanceof ANDMatcher) {
			for(AnnotationMatcher atom : ((ANDMatcher)m).getMatchers()) {
				convertMatcher(atom, typeMatchers);
			}
		} else if(m instanceof AnnotationMatchers.NOTAnnotationMatcher) {
			AnnotationMatcher inner = ((AnnotationMatchers.NOTAnnotationMatcher) m).getMatcher();
			TypeMatcher matcher = getMatcherFor(typeMatchers, inner.getType());
			matcher.matchers.add(inner);
			matcher.matchersIndexes.add(matchers.get(inner));
			matcher.flags.add(1);
		} else {
			TypeMatcher matcher = getMatcherFor(typeMatchers, m.getType());
			matcher.matchers.add(m);
			matcher.matchersIndexes.add(matchers.get(m));
			matcher.flags.add(0);
		}
	}
	
	
	/**
	 * Get state by index
	 * 
	 * @param index state index
	 * @return
	 */
	public State getState(int index) {
		return states.get(index);
	}
	
	/**
	 * Get group by index
	 * 
	 * @param index group index
	 * @return
	 */
	public String getGroupName(int index) {
		return groups.get(index);
	}

	/**
	 * Get start state
	 * @return
	 */
	public State getStart() {
		return start;
	}

	/**
	 * Get annotation matcher by index
	 * @param key
	 * @return
	 */
	public AnnotationMatcher getMatcher(int key) {
		return matchers.get(key);
	}
	
	/**
	 * Get total count of atomic annotation matchers for this JapePlusFSM
	 * @return
	 */
	public int getMatcherCount() {
		return matchers.size();
	}
	
	
}
