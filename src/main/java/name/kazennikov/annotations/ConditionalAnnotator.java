package name.kazennikov.annotations;

/**
 * Conditional annotator. Applies internal annotator only then the condition is true
 * @author kzn
 *
 */
public class ConditionalAnnotator implements Annotator {
	Annotator a;
	
	public ConditionalAnnotator(Annotator a) {
		this.a = a;
	}
	
	/**
	 * Checks if the annotator should be applied
	 * @param d target document
	 * @return
	 */
	public boolean check(Document d) {
		return true;
	}


    @Override
    public boolean isApplicable(Document doc) {
        return a.isApplicable(doc);
    }

    @Override
	public void annotate(Document doc) {
		if(check(doc)) {
			a.annotate(doc);
		}
	}
}
