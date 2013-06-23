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
public class Annotation implements IdBearer, CharSequence, Comparable<Annotation> {

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
			int res = o1.start.getOffset() - o2.start.getOffset();
			return res != 0? res : o2.end.getOffset() - o1.end.getOffset();
		}
	};

	Document doc;
	int id;
	String type;
	Node start;
	Node end;

	
	Object data;
	
	Map<String, Object> features = Maps.newHashMap();

	Annotation() {
		
	}
	
	protected Annotation(Document doc, String type, Node start, Node end) {
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
	
	public Node getStart() {
		return start;
	}
	
	public Node getEnd() {
		return end;
	}
	
	public void setStart(Node start) {
		this.start = start;
	}

	public void setEnd(Node end) {
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
		return doc.getText().substring(start.getOffset(), end.getOffset());
	}
	
	public boolean isEmpty() {
		return start == end;
	}
	
	public <E> E as(Class<E> cls) {
		return cls.cast(this);
	}
	
	public boolean contains(Annotation other) {
		return start.getOffset() <= other.getStart().getOffset() && end.getOffset() >= other.getEnd().getOffset();
	}
	
	public boolean overlaps(Annotation other) {
		return end.getOffset() > other.getStart().getOffset() || start.getOffset() <= other.getEnd().getOffset();
	}
	
	public boolean isLeftOf(Annotation other) {
		return start.getOffset() > other.start.getOffset();
	}
	
	public boolean isRightOf(Annotation other) {
		return start.getOffset() < other.start.getOffset();
	}
	
	public boolean contained(Annotation other) {
		return other.contains(this);
	}
	
	
	public boolean contains(int start, int end) {
		return this.start.getOffset() <= start && this.end.getOffset() >= end;
	}
	
	public boolean overlaps(int start, int end) {
		return this.end.getOffset() > start || this.start.getOffset() <= end;
	}
	
	public boolean isLeftOf(int position) {
		return start.getOffset() > position;
	}
	
	public boolean isRightOf(int position) {
		return start.getOffset() < position;
	}
	
	public boolean contained(int start, int end) {
		return start <= this.start.getOffset() && end >= this.end.getOffset();
	}


	@Override
	public int length() {
		return end.getOffset() - start.getOffset();
	}


	@Override
	public char charAt(int index) {
		return doc.getText().charAt(start.getOffset() + index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return doc.getText().subSequence(this.start.getOffset() + start, this.start.getOffset() + end);
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
	
	@Override
    public int getId() {
		return id;
	}



}
