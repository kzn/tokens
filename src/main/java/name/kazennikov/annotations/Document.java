package name.kazennikov.annotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	public Document(String annotName, String text) {
		super(annotName, 0, text.length());
		this.text = text;
		setDoc(this);
	}

	@Override
	public String getText() {
		return text;
	}
	
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
	
	public Set<String> getAnnotationNames() {
		return annotations.keySet();
	}
	
	public void addAnnotation(Annotation ann) {
		List<Annotation> anns = get(ann.getName());
		ann.setDoc(this);
		anns.add(ann);
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
}
