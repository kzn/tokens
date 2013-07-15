package name.kazennikov.annotations.patterns;

public class RangePatternElement implements PatternElement {
	
	public static int INFINITE = Integer.MAX_VALUE;
	
	PatternElement element;
	int min = 0;
	int max = 0;
	
	boolean greedy = true;

	@Override
	public Operator op() {
		return Operator.RANGE;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public PatternElement get(int index) {
		if(index == 0)
			return element;
		
		return null;
	}
	
	@Override
	public String toString() {
		return String.format("%s(%d,%d)", element, min, max);
	}
	
	public int min() {
		return min;
	}
	
	public int max() {
		return max;
	}

}
