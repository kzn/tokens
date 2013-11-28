package name.kazennikov.annotations.patterns;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import name.kazennikov.annotations.fsm.JapePlusFSM;

import com.google.common.base.Objects;
import com.google.common.io.Files;

public class Phase {
	
	String name;
	MatchMode mode = MatchMode.BRILL;
	Set<String> input = new HashSet<>();
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
