package name.kazennikov.annotations;

import java.util.*;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

public class Document extends Annotation implements CharSequence {
	String text;
	
	Map<String, List<Annotation>> annotations = Maps.newHashMap();
	
	public Document() {	
		this("");
	}
	
	public Document(String text) {
		this("doc", text);
	}

    /**
     * Construct a document with root annotation and given text
     * @param annotName global document annotation
     * @param text document text
     */
	public Document(String annotName, String text) {
		super(annotName, 0, text.length());
		this.text = text;
		setDoc(this);
	}

	@Override
	public String getText() {
		return text;
	}

    /**
     * Get annotations by name
     * @param name annotations name
     * @return
     */
	public List<Annotation> get(String name) {
		if(name.equals(getName()))
			return Arrays.asList((Annotation)this);
		
		List<Annotation> ann = annotations.get(name);
		if(ann == null) {
			ann = new ArrayList<Annotation>();
			annotations.put(name, ann);
		}
		
		return ann;
	}

    /**
     * Checks if document has any of this annotations
     * @param annotationName annotation name
     */
    public boolean contains(String annotationName) {
        return getName().equals(annotationName) || annotations.containsKey(annotationName);
    }

    /**
     * Get set of all annotations present in the document
     * @return
     */
	public Set<String> getAnnotationNames() {
        HashSet<String> annotNames = new HashSet<String>(annotations.keySet());
        annotNames.add(getName());
		return annotNames;
	}

    /**
     * Add single annotation to the document
     * @param ann
     */
	public void addAnnotation(Annotation ann) {
		List<Annotation> anns = get(ann.getName());
		ann.setDoc(this);
		anns.add(ann);
	}

    /**
     * Add all annotations from annotation list
     * @param ans
     */
	public void addAnnotations(Collection<? extends Annotation> ans) {
		for(Annotation a : ans) {
			addAnnotation(a);
		}
	}
	
	public void clearAnnotations(String name) {
		get(name).clear();
	}

	@Override
	public int length() {
		return text.length();
	}

	@Override
	public char charAt(int index) {
		return text.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return text.subSequence(start, end);
	}
	
	/**
	 * Get annotations that are constrained by span of given annotation and properties of valid annotations
	 * @param a constrain span of the output annotation
	 * @param p select predicate for constrained annotations
	 * @return list of annotations
	 */
	public List<Annotation> getAnnotationsWithin(Annotation a, Predicate<Annotation> p) {
		List<Annotation> anns = new ArrayList<Annotation>();
		
		for(List<Annotation> anList : annotations.values()) {
			for(Annotation an : anList ) {
				// skip given
				if(an == a)
					continue;
				
				if(a.contains(an) && p.apply(an))
					anns.add(an);
			}
		}
		
		Collections.sort(anns, Annotation.COMPARATOR);
		
		return anns;
	}
}
