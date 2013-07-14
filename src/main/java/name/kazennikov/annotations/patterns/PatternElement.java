package name.kazennikov.annotations.patterns;


public interface PatternElement {
	public static enum Operator {
		AN_MATCHER,
		SEQ,
		OR,
		STAR,
		PLUS,
		OPTIONAL,
		RANGE
	}
	
	public Operator op();
	public int size();
	public PatternElement get(int index);
}
