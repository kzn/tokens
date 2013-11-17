package name.kazennikov.annotations.patterns;


public class AnnotationMatcherPatternElement implements PatternElement {
	AnnotationMatcher matcher;
	
	public AnnotationMatcherPatternElement(AnnotationMatcher matcher) {
		this.matcher = matcher;
	}

	@Override
	public Operator op() {
		return Operator.AN_MATCHER;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public PatternElement get(int index) {
		return index == 0? this : null;
	}
	
	public AnnotationMatcher matcher() {
		return matcher;
	}
	
	@Override
	public String toString() {
		return matcher.toString();
	}

}
