package name.kazennikov.annotations;

import java.util.List;

import name.kazennikov.tokens.SequenceStream;

public class AnnotationStream extends SequenceStream<Annotation> {

	public AnnotationStream(Annotation nullObject,
			List<? extends Annotation> tokens) {
		super(nullObject, tokens);
	}
	

}
