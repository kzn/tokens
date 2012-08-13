package name.kazennikov.annotations.patterns;

import java.util.List;

public interface PatternElement {
	public static enum Operator {
		AN_MATCHER,
		SEQ,
		OR,
		STAR,
		PLUS,
		NONGREEDY_STAR,
		NONGREEDY_PLUS,
		OPTIONAL
	}
	
	public Operator op();
	public List<PatternElement> args();
	public int size();
	public PatternElement get(int index);
}
