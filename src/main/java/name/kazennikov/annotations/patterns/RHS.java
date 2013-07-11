package name.kazennikov.annotations.patterns;

import java.util.Map;

import name.kazennikov.annotations.AnnotationList;
import name.kazennikov.annotations.Document;

public interface RHS {
	public boolean execute(Document doc, AnnotationList input, Map<String, AnnotationList> bindings);
}
