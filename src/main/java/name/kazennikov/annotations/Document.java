package name.kazennikov.annotations;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import java.util.*;

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
		super(null, annotName, 0, text.length());
		this.text = text;
		setDoc(this);
	}

	@Override
	public String getText() {
		return text;
	}

    /**
     * Get annotations by name
     * @param names annotations names
     * @return
     */
	public List<Annotation> get(String... names) {
        List<Annotation> anns = new ArrayList<Annotation>();

        for(String name : names) {
            if(name.equals(getName())) {
                anns.add(this);
            }

            List<Annotation> ann = annotations.get(name);
            if(ann != null) {
                anns.addAll(ann);
            }
        }

        Collections.sort(anns, Annotation.COMPARATOR);
		
		return anns;
	}

    /**
     * Checks if document has any of this annotations
     * @param annotationNames annotation names
     */
    public boolean contains(String... annotationNames) {
        for(String annotationName : annotationNames) {
             boolean  res = getName().equals(annotationName) || annotations.containsKey(annotationName);
            if(res)
                return true;
        }

        return false;
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
		//List<Annotation> anns = get(ann.getName());
		if(ann.getName() == getName()) {
			throw new IllegalArgumentException("Couldn't add annotation with same name as document root");
		}
		List<Annotation> annots = annotations.get(ann.getName());
		
		if(annots == null) {
			annots = new ArrayList<Annotation>();
			annotations.put(ann.getName(), annots);
		}
		
		ann.setDoc(this);
		annots.add(ann);
	}
	
	public void addAnnotation(String name, int start, int end) {
		Annotation a = new Annotation(this, name, start, end);
		addAnnotation(a);
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

    public void sortAnnotations() {
        for(List<Annotation> annots : annotations.values()) {
            Collections.sort(annots, Annotation.COMPARATOR);
        }
    }

    public void sortAnnotations(String... names) {
        for(String anName : names) {
            List<Annotation> annots = annotations.get(anName);
            if(annots == null || annots.isEmpty())
                continue;

            Collections.sort(annots, Annotation.COMPARATOR);
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
