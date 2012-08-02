package name.kazennikov.annotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AnnotatorSequence implements Annotator {
	List<Annotator> annotators = new ArrayList<Annotator>();
	
	public AnnotatorSequence() {
	
	}
	
	public static AnnotatorSequence newInstance(List<Annotator> a) {
		AnnotatorSequence seq = new AnnotatorSequence();
		seq.addAll(a);
		return seq;
	}
	
	public static AnnotatorSequence newInstance(Annotator... anns) {
		AnnotatorSequence seq = new AnnotatorSequence();
		
		for(Annotator a : anns) {
			seq.add(a);
		}
		
		return seq;
	}
	
	
	
	public void add(Annotator a) {
		annotators.add(a);
	}
	
	public void addAll(Collection<? extends Annotator> anns) {
		annotators.addAll(anns);
	}
	
	public int size() {
		return annotators.size();
	}
	
	public List<Annotator> getAnnotators() {
		return annotators;
	}
	

	@Override
	public void annotate(Document doc) {
		for(Annotator a : annotators) {
			a.annotate(doc);
		}
	}

}
