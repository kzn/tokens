package name.kazennikov.annotations.patterns;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasePatternElement implements PatternElement {
	Operator op;
	String name;
	List<PatternElement> args;
	
	public BasePatternElement(String name, Operator op, List<PatternElement> args) {
		this.name = name;
		this.op = op;
		this.args = args;
	}
	
	
	public static BasePatternElement newInstance(Operator op, PatternElement... args) {
		return new BasePatternElement(op, args);
	}
	
	public BasePatternElement(Operator op, PatternElement... args) {
		this(null, op, new ArrayList<PatternElement>(Arrays.asList(args)));
	}
	
	public AnnotationMatcherPatternElement newInstance(AnnotationMatcher matcher) {
		return new AnnotationMatcherPatternElement(matcher);
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("name", name)
				.add("op", op)
				.add("args", args)
				.toString();
	}


	@Override
	public Operator op() {
		return op;
	}


	@Override
	public List<PatternElement> args() {
		return args;
	}


	@Override
	public int size() {
		return args.size();
	}


	@Override
	public PatternElement get(int index) {
		return args.get(index);
	}


}
