package name.kazennikov.annotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Single annotator that is a sequence of child annotators
 * @author kzn
 *
 */
public class AnnotatorSequence implements Annotator {
	List<Annotator> annotators = new ArrayList<Annotator>();
	
	public AnnotatorSequence() {
	
	}
	
	/**
	 * Construct AnnotatorSequence from given list
	 * @param a list of annotators
	 * @return
	 */
	public static AnnotatorSequence newInstance(List<Annotator> a) {
		AnnotatorSequence seq = new AnnotatorSequence();
		seq.addAll(a);
		return seq;
	}
	
	/**
	 * Construct AnnotatorSequence from given list
	 * @param anns annotators
	 * @return
	 */
	public static AnnotatorSequence newInstance(Annotator... anns) {
		AnnotatorSequence seq = new AnnotatorSequence();
		
		for(Annotator a : anns) {
			seq.add(a);
		}
		
		return seq;
	}
	
	
	/**
	 * Add new annotator to the end of the list
	 * @param a annotator to add
	 */
	public void add(Annotator a) {
		annotators.add(a);
	}
	
	/**
	 * Add all annotators to the end of the list
	 * @param anns annotators to add
	 */
	public void addAll(Collection<? extends Annotator> anns) {
		annotators.addAll(anns);
	}
	
	/**
	 * Get sequence size
	 * @return
	 */
	public int size() {
		return annotators.size();
	}
	
	/**
	 * Return list of annotators
	 * @return
	 */
	public List<Annotator> getAnnotators() {
		return annotators;
	}


    @Override
    public boolean isApplicable(Document doc) {
        return !annotators.isEmpty() && annotators.get(0).isApplicable(doc);
    }

    @Override
	public void annotate(Document doc) {
		for(Annotator a : annotators) {
			a.annotate(doc);
		}
	}

}
