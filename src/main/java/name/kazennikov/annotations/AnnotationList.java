package name.kazennikov.annotations;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: kzn
 * Date: 23.09.12
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public class AnnotationList extends ArrayList<Annotation> {

    public AnnotationList get(Predicate<Annotation>... p) {
        AnnotationList list = new AnnotationList();
        Predicate<Annotation> pred = Predicates.and(p);
        for(Annotation a : this) {
            if(pred.apply(a))
                list.add(a);
        }

        return list;
    }

    public void sort() {
        Collections.sort(this, Annotation.COMPARATOR);
    }

    public void sort(Comparator<Annotation> comparator) {
        Collections.sort(this, comparator);
    }
    
    public void removeIf(Predicate<Annotation> p) {
    	Iterator<Annotation> it = iterator();
    	
    	while(it.hasNext()) {
    		if(p.apply(it.next())) {
    			it.remove();
    		}
    	}
    }
    
    public void removeIfNot(Predicate<Annotation> p) {
    	removeIf(Predicates.not(p));
    }
}
