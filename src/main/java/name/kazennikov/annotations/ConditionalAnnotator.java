package name.kazennikov.annotations;

public class ConditionalAnnotator implements Annotator {
	Annotator a;
	
	public ConditionalAnnotator(Annotator a) {
		this.a = a;
	}
	
	
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
