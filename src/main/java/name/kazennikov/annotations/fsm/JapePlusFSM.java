package name.kazennikov.annotations.fsm;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import name.kazennikov.annotations.patterns.AnnotationMatcher;
import name.kazennikov.annotations.patterns.AnnotationMatcherPatternElement;
import name.kazennikov.annotations.patterns.BasePatternElement;
import name.kazennikov.annotations.patterns.PatternElement;
import name.kazennikov.annotations.patterns.PatternElement.Operator;
import name.kazennikov.annotations.patterns.RangePatternElement;
import name.kazennikov.annotations.patterns.Rule;
import name.kazennikov.fsa.FSA;
import name.kazennikov.fsa.FSAState;
import name.kazennikov.tools.Alphabet;

public class JapePlusFSM {
	public static final int GROUP_START = -1;

	
	protected static class InterFSM extends FSA<Set<Rule>> {
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


	//List<State> states = new ArrayList<>();	
	//List<Transition> transitions = new ArrayList<>();
	
	Alphabet<String> groups = new Alphabet<>();
	Alphabet<AnnotationMatcher> matchers = new Alphabet<>();
	InterFSM fsm = new InterFSM();
	
	public JapePlusFSM() {
	}
	
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

		fsm.addTransition(start, end, name.kazennikov.fsa.Constants.EPSILON); // skip optional parrts
		
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
		InterFSM temp = new InterFSM();
		fsm.determinize(temp);
		fsm = temp;
	}
	
	public void minimize() {
		InterFSM temp = new InterFSM();
		fsm.minimize(temp);
		fsm = temp;
	}

	
	
	
	public int size() {
		return fsm.size();
	}
	





}
