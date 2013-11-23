package name.kazennikov.annotations.patterns;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import name.kazennikov.annotations.fsm.JapePlusFSM;

import com.google.common.base.Objects;
import com.google.common.io.Files;

public class Phase {
	
	String name;
	MatchMode mode = MatchMode.BRILL;
	List<String> input = new ArrayList<>();
	List<Rule> rules = new ArrayList<>();
	JapePlusFSM fsm;
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("name", name)
				.add("mode", mode)
				.add("input", input)
				.add("rules", rules)
				.toString();
	}
	
	public List<Rule> getRules() {
		return rules;
	}
	
	public boolean isCompiled() {
		return fsm != null;
	}
	
	public void compile() {
		JapePlusFSM.Builder builder = new JapePlusFSM.Builder();
		for(Rule r : rules) {
			builder.addRule(r);
		}
		
		fsm = builder.build();
	}
	
	public static Phase parse(JapeConfiguration config, File f) throws Exception {
		return SinglePhaseJapeASTParser.parsePhase(config, Files.toString(f, Charset.forName("UTF-8")));
	}
	
	

}
