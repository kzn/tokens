package name.kazennikov.annotations.patterns;

import java.util.Arrays;
import java.util.List;

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
	public List<PatternElement> args() {
		return Arrays.asList((PatternElement)this);
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public PatternElement get(int index) {
		return index == 0? this : null;
	}

}
