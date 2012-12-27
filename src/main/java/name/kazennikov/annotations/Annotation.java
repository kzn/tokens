package name.kazennikov.annotations;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Document annotation. Annotation is a continuous text span in the document. It has a name (type) and 
 * specific features on the given annotation. 
 * <p>
 * Features is a mapping : feature name (string) -> value (object)
 * <p>
 * Annotations are sorted in the document: they are sorted by start offset (inc) and by lenth (decr). That means
 * that if two annotatinos have same start offset the longer is earlier.
 * 
 * @author kzn
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

	Document doc;
	
	public final static Comparator<Annotation> COMPARATOR = new Comparator<Annotation>() {
		
		@Override
		public int compare(Annotation o1, Annotation o2) {
			int res = o1.start - o2.start;
			return res != 0? res : o2.end - o1.end;
		}
	};
	
	String name;
	int start;
	int end;
	
	Object data;
	
	Map<String, Object> features;
	
	/**
	 * Construct a new annotation. Does NOT add this annotation to the source document.
	 * 
	 * @param doc source document
	 * @param name annotation name
	 * @param start start offset
	 * @param end end offset
	 * @param features features map
	 */
	public Annotation(Document doc, String name, int start, int end, Map<String, Object> features) {
		this.doc = doc;
		this.name = name;
		this.start = start;
		this.end = end;
		this.features = features;
	}
	
	/**
	 * Creates a new annotation in the document with empty features
	 * @param doc source document
	 * @param name annotation name
	 * @param start start offset
	 * @param end end offset
	 */
	public Annotation(Document doc, String name, int start, int end) {
		this(doc, name, start, end, new HashMap<String, Object>());
	}
	
	/**
	 * Get source document
	 * @return
	 */
	public Document getDoc() {
		return doc;
	}
	
	/**
	 * Set source document
	 * @param doc
	 */
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	
	/**
	 * Get annotation name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get annotation start offset
	 * @return
	 */
	public int getStart() {
		return start;
	}
	
	/**
	 * Get annotation end offset
	 * @return
	 */
	public int getEnd() {
		return end;
	}
	
	/**
	 * Set start offset
	 * @param start
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * Set end offset
	 * @param end
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * Get all feature names of this annotation
	 * @return
	 */
	public Set<String> getFeatureNames() {
		return features.keySet();
	}
	
	
	/**
	 * Get features with unsafe casting to the return type
	 * @param feat feature name
	 * @return feature value or null
	 */
	@SuppressWarnings("unchecked")
	public <E> E getFeature(String feat) {
		return (E) features.get(feat);
	}
	
	/**
	 * Get feature with class assumption
	 * @param feat feature name
	 * @param cls feature class
	 * @return
	 */
	public <E> E getFeature(String feat, Class<E> cls) {
		return cls.cast(getFeature(feat));
	}
	
	/**
	 * Set feature value
	 * @param feat feature name
	 * @param value feature value
	 */
	public void setFeature(String feat, Object value) {
		features.put(feat, value);
	}
	
	/**
	 * Get feature map
	 */
	public Map<String, Object> getFeatureMap() {
		return features;
	}
	
	@Override
	public String toString() {
		return String.format("'%s'@%s[%d,%d]%s%s", getText(), name, start, end, features, data);
	}
	

	/**
	 * Get annotation text. 
	 * <p>
	 * Returns the substring of the document in range [start, end)
	 * @return
	 */
	public String getText() {
		return doc.getText().substring(start, end);
	}
	
	/**
	 * Checks if the annotation is empty. An empty annotation has zero length
	 * @return
	 */
	public boolean isEmpty() {
		return start == end;
	}
	
	/**
	 * Cast this object to given class
	 * @param cls target class
	 * @return
	 */
	public <E> E as(Class<E> cls) {
		return cls.cast(this);
	}
	
	/**
	 * Checks if this annotation contains other annotation
	 */
	public boolean contains(Annotation other) {
		return start <= other.getStart() && end >= other.getEnd();
	}

	/**
	 * Checks if this annotation overlaps with other annotation
	 */
	public boolean overlaps(Annotation other) {
		return end > other.getStart() || start <= other.getEnd();
	}

	/**
	 * Checks if this annotation is in left of the other annotation.
	 * @param other
	 * @return
	 */
	public boolean isLeftOf(Annotation other) {
		return start > other.start;
	}

	/**
	 * Checks if this annotation is in right of the other annotation.
	 * @param other
	 * @return
	 */
	public boolean isRightOf(Annotation other) {
		return start < other.start;
	}
	
	/**
	 * Checks is this annotation is contained in the other annotation
	 * @param other
	 * @return
	 */
	public boolean contained(Annotation other) {
		return other.contains(this);
	}
	
	/**
	 * Checks if this annotation contains specified span
	 * @param start span start
	 * @param end span end
	 */
	public boolean contains(int start, int end) {
		return this.start <= start && this.end >= end;
	}

	/**
	 * Checks if this annotation overlaps specified span
	 * @param start span start
	 * @param end span end
	 */
	public boolean overlaps(int start, int end) {
		return this.end > start || this.start <= end;
	}

	/**
	 * Checks if this annotation is in the left of specified span
	 * @param start span start
	 * @param end span end
	 */
	public boolean isLeftOf(int position) {
		return start > position;
	}
	
	/**
	 * Checks if this annotation is in the right of specified span
	 * @param start span start
	 * @param end span end
	 */
	public boolean isRightOf(int position) {
		return start < position;
	}
	
	
	/**
	 * Checks if this annotation contained in the specified span
	 * @param start span start
	 * @param end span end
	 */
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
	
	/**
	 * Checks if this annotation is coextensive (have the same span) with other annotation
	 * @param other
	 * @return
	 */
	public boolean isCoextensive(Annotation other) {
		return other.getStart() == start && other.getEnd() == end;
	}

	/**
	 * Get data (user specified object) of with annotation
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Get data (user specified object) of with annotation
	 * @param cls class of the user data
	 * @return
	 */
	public <E> E getData(Class<E> cls) {
		return cls.cast(data);
	}

	/**
	 * Set annotation data
	 * @param data user data
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * Set annotation name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Annotation o) {
		return COMPARATOR.compare(this, o);
	}
}
