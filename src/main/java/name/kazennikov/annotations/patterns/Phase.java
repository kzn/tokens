package name.kazennikov.annotations.patterns;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

public class Phase {
	
	String name;
	MatchMode mode = MatchMode.BRILL;
	List<String> input = new ArrayList<>();
	List<Rule> rules = new ArrayList<>();
	
	
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
	

}
