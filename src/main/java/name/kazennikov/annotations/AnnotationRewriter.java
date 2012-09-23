package name.kazennikov.annotations;

/**
 * Created with IntelliJ IDEA.
 * User: kzn
 * Date: 23.09.12
 * Time: 22:28
 * To change this template use File | Settings | File Templates.
 */
public interface AnnotationRewriter {
    /**
     * Rewrite given annotation. Doesn't guarantees that
     * the result will be a fresh annotation (may be side-effects)
     * @param a source annotation
     * @return result annotation
     */
    public Annotation rewriter(Annotation a);
}
