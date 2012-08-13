package name.kazennikov.annotations.patterns;

import name.kazennikov.annotations.Annotation;

public interface AnnotationMatcher {
	public boolean match(Annotation a);
}
