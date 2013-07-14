package name.kazennikov.annotations.patterns;

import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;

import name.kazennikov.tools.EclipseECJWrapper;

public class JavaRHSBuilder {
	private static AtomicInteger actionClassNumber = new AtomicInteger();
	
	

	public static RHS build(String packageName, String code) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		String className = "JapeNGActionClass" + actionClassNumber.incrementAndGet();
		
		sb//.append("package " + packageName).append(";\n")
		.append("import java.util.*;\n")
		.append("import name.kazennikov.annotations.*;\n")
		.append("import name.kazennikov.annotations.patterns.RHS;\n")
		
		.append("public class " + className + " implements RHS {\n")
		.append("@Override public boolean execute(Document doc, AnnotationList input, Map<String, AnnotationList> bindings)\n")
		.append(code)
		.append("}");
		StringWriter w = new StringWriter();
		Class<?> clazz = EclipseECJWrapper.compileClass(sb.toString(), className, w);
		if(clazz == null)
			throw new IllegalStateException("RHS code not compiled:" + code);
		
		RHS o = (RHS) clazz.newInstance();
		
		return o;

	}
	
	
	public static void main(String[] args) throws Exception {
		RHS rhs = build("foo", "{List<String> foo = null; return true;}");
	}

}
