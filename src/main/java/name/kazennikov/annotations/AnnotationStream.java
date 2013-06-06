package name.kazennikov.annotations;

import java.util.List;

/**
 * Stream of annotations
 * @author Anton Kazennikov
 *
 */
public class AnnotationStream extends SequenceStream<Annotation> {

	public AnnotationStream(Annotation nullObject,
			List<? extends Annotation> tokens) {
		super(nullObject, tokens);
	}
	
	public AnnotationStream(List<? extends Annotation> anns) {
		this(null, anns);
	}
	

}
