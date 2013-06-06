package name.kazennikov.annotations;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/**
 * Central class for annotation framework. Annotation is a typed span in the document.
 * 
 * @author Anton Kazennikov
 *
 */
public class Annotation implements CharSequence, Comparable<Annotation> {

	public static final String TOKEN = "token";
    public static final String WORD = "word";
    public static final String SENT = "sent";
    public static final String DOC = "doc";
    public static final String TYPE = "type";
    public static final String KIND = "kind";
    public static final String DATA = "data";

	/**
	 * Basic comparator for natural order. Sorts by:
	 * <ul>
	 * <li> start offset (ascending)
	 * <li> length (descending)
	 * </ul>
	 * 
	 * 
	 */
	public final static Comparator<Annotation> COMPARATOR = new Comparator<Annotation>() {
		
		@Override
		public int compare(Annotation o1, Annotation o2) {
			int res = o1.start - o2.start;
			return res != 0? res : o2.end - o1.end;
		}
	};

	Document doc;
	int id;
	String type;
	int start;
	int end;

	
	Object data;
	
	Map<String, Object> features = Maps.newHashMap();
	
	public Annotation(Document doc, String type, int start, int end) {
		this.doc = doc;
		this.type = type;
		this.start = start;
		this.end = end;
	}
	
	public Document getDoc() {
		return doc;
	}
	
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	
	public String getType() {
		return type;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public Set<String> getFeatureNames() {
		return features.keySet();
	}
	
	
	@SuppressWarnings("unchecked")
	public <E> E getFeature(String feat) {
		return (E) features.get(feat);
	}
	
	public <E> E getFeature(String feat, Class<E> cls) {
		return cls.cast(getFeature(feat));
	}
	
	public void setFeature(String feat, Object value) {
		features.put(feat, value);
	}
	
	public Map<String, Object> getFeatureMap() {
		return features;
	}
	
	@Override
	public String toString() {
		return String.format("'%s'@%s[%d,%d]{#%d,%s,%s}", getText(), type, start, end, id, features, data);
	}
	
	
	public String getText() {
		return doc.getText().substring(start, end);
	}
	
	public boolean isEmpty() {
		return start == end;
	}
	
	public <E> E as(Class<E> cls) {
		return cls.cast(this);
	}
	
	public boolean contains(Annotation other) {
		return start <= other.getStart() && end >= other.getEnd();
	}
	
	public boolean overlaps(Annotation other) {
		return end > other.getStart() || start <= other.getEnd();
	}
	
	public boolean isLeftOf(Annotation other) {
		return start > other.start;
	}
	
	public boolean isRightOf(Annotation other) {
		return start < other.start;
	}
	
	public boolean contained(Annotation other) {
		return other.contains(this);
	}
	
	
	public boolean contains(int start, int end) {
		return this.start <= start && this.end >= end;
	}
	
	public boolean overlaps(int start, int end) {
		return this.end > start || this.start <= end;
	}
	
	public boolean isLeftOf(int position) {
		return start > position;
	}
	
	public boolean isRightOf(int position) {
		return start < position;
	}
	
	public boolean contained(int start, int end) {
		return start <= this.start && end >= this.end;
	}


	@Override
	public int length() {
		return end - start;
	}


	@Override
	public char charAt(int index) {
		return doc.getText().charAt(start + index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return doc.getText().subSequence(this.start + start, this.start + end);
	}
	
	public boolean isCoextensive(Annotation ann) {
		return ann.getStart() == start && ann.getEnd() == end;
	}

	public Object getData() {
		return data;
	}
	
	public <E> E getData(Class<E> cls) {
		return cls.cast(data);
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int compareTo(Annotation o) {
		return COMPARATOR.compare(this, o);
	}
	
	@SuppressWarnings("unchecked")
	public AnnotationList get(Predicate<Annotation>... predicates) {
		return doc.get(Predicates.and(AnnotationPredicates.within(this), Predicates.and(predicates)));
	}
		
    public int getId() {
		return id;
	}



}
