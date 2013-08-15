package name.kazennikov.annotations.tokenizer;

import java.util.ArrayList;
import java.util.List;

import name.kazennikov.annotations.tokenizer.SimpleTokenizer.RHS;
import name.kazennikov.fsm.FSM;
import name.kazennikov.fsm.FSMState;

public class TokenizerFSM extends FSM<List<SimpleTokenizer.RHS>> {

	@Override
	public FSMState<List<RHS>> addState() {
		FSMState<List<RHS>> s = super.addState();
		s.setFinals(new ArrayList<RHS>());
		return s;
	}
	
	@Override
	public void mergeFinals(FSMState<List<RHS>> dest, FSMState<List<RHS>> src) {
		dest.getFinals().addAll(src.getFinals());
	}

	@Override
	public boolean isFinal(FSMState<List<RHS>> s) {
		return !s.getFinals().isEmpty();
	}
	

}
