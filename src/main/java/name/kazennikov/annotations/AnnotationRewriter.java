package name.kazennikov.annotations;

/**
 * Annotation rewriter
 * 
 */
public interface AnnotationRewriter {
    /**
     * Rewrite given annotation. Doesn't guarantees that
     * the result will be a fresh annotation (allows side-effects)
     * @param a source annotation
     * @return result annotation
     */
    public Annotation rewrite(Annotation a);
}
