package name.kazennikov.annotations.patterns;

import java.util.Map;

import name.kazennikov.annotations.AnnotationList;
import name.kazennikov.annotations.Document;

public class EmptyRHS implements RHS{

	@Override
	public boolean execute(Document doc, AnnotationList input, Map<String, AnnotationList> bindings) {
		return true;
	}

}
