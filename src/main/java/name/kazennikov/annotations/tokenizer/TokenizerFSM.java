package name.kazennikov.annotations.tokenizer;

import java.util.ArrayList;
import java.util.List;

import name.kazennikov.annotations.tokenizer.SimpleTokenizer.RHS;
import name.kazennikov.fsa.FSA;
import name.kazennikov.fsa.FSAState;

public class TokenizerFSM extends FSA<List<SimpleTokenizer.RHS>> {

	@Override
	public FSAState<List<RHS>> addState() {
		FSAState<List<RHS>> s = super.addState();
		s.setFinals(new ArrayList<RHS>());
		return s;
	}
	
	@Override
	public void mergeFinals(FSAState<List<RHS>> dest, FSAState<List<RHS>> src) {
		dest.getFinals().addAll(src.getFinals());
	}

	@Override
	public boolean isFinal(FSAState<List<RHS>> s) {
		return !s.getFinals().isEmpty();
	}
	

}
