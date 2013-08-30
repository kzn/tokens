package name.kazennikov.annotations.patterns;

import name.kazennikov.annotations.Annotation;

/**
 * Basic annotation matcher. Used in JAPE-like rules to advance current position in annotation FSA
 * @author Anton Kazennikov
 *
 */
public interface AnnotationMatcher {
	/**
	 * Match given annotation
	 * 
	 * @param a annotation to match
	 */
	public boolean match(Annotation a);
	
	/**
	 * Get annotation type of this matcher
	 * @return annotation type name, or null if it could be matched to any type
	 */
	public String getType();
}
