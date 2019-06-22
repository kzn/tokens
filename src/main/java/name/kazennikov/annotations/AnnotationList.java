package name.kazennikov.annotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * List of annotations. Some basic extension over the java ArrayList
 */
public class AnnotationList extends ArrayList<Annotation> {
	
	public AnnotationList() {
		super();
	}
	
	public AnnotationList(Collection<? extends Annotation> c) {
		super(c);
	}
	
	public AnnotationList(int initialSize) {
		super(initialSize);
	}

    public AnnotationList get(Predicate<Annotation>... p) {
        AnnotationList list = new AnnotationList();
        Predicate<Annotation> pred = Predicates.and(p);
        for(Annotation a : this) {
            if(pred.apply(a))
                list.add(a);
        }

        return list;
    }

    /**
     * Sort annotations using default annotation comparator
     */
    public void sort() {
        Collections.sort(this, Annotation.COMPARATOR);
    }


    /**
     * Removes all annotations which match a predicate
     * @param p annotation predicate
     */
    public void removeIf(Predicate<Annotation> p) {
    	Iterator<Annotation> it = iterator();
    	
    	while(it.hasNext()) {
    		if(p.apply(it.next())) {
    			it.remove();
    		}
    	}
    }
    
    /**
     * Removes all annotations which does NOT match a predicate
     * @param p annotation predicate
     */
    public void removeIfNot(Predicate<Annotation> p) {
    	removeIf(Predicates.not(p));
    }
    
    @Override
    public Annotation get(int index) {
    	return super.get(index < 0? size() - index : index);
    }
    
    public AnnotationList copy() {
    	AnnotationList copy = new AnnotationList();
    	for(int i = 0; i < size(); i++) {
    		copy.add(get(i));
    	}
    	
    	return copy;
    }
    
    public int getStart() {
    	int min = Integer.MAX_VALUE;
    	
    	for(Annotation a : this) {
    		if(a.getStart() < min) {
    			min = a.getStart();
    		}
    	}
    	
    	return min;
    }
    
    public int getEnd() {
    	int max = Integer.MIN_VALUE;
    	
    	for(Annotation a : this) {
    		if(a.getEnd() > max) {
    			max = a.getEnd();
    		}
    	}
    	
    	return max;
    }
}
