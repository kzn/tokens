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
	public void annotate(Document doc) {
		if(check(doc)) {
			a.annotate(doc);
		}
	}
}
