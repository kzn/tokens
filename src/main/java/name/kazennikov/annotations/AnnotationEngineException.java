package name.kazennikov.annotations;

public class AnnotationEngineException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnnotationEngineException() {
		super();
	}

	public AnnotationEngineException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AnnotationEngineException(String message, Throwable cause) {
		super(message, cause);
	}

	public AnnotationEngineException(String message) {
		super(message);
	}

	public AnnotationEngineException(Throwable cause) {
		super(cause);

	}
	
	

}
