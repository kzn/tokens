package name.kazennikov.annotations;

/**
 * Annotator interface.
 * <p>
 * An annotator (suprisingly) annotates a document. So, the document is modified.
 * All anotators of the document are expected to run sequentially.
 * 
 * An annotator ensures, that all modified document annotations rest in sane state.
 * That means that all annotations are sorted in proper way
 * 
 * @author Anton Kazennikov
 *
 */
public interface Annotator {

    /**
     * Checks if this annotator is applicable to the document
     * @param doc document
     */
    public boolean isApplicable(Document doc);

    /**
	 * Annotate a document
	 * @param doc
	 */
	public void annotate(Document doc);
	
	/**
	 * Gets annotator name
	 * @return
	 */
	public String getName();


}
