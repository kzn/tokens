package name.kazennikov.annotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

public class Document {
	String text;
	
	Map<String, List<Annotation>> annotations = Maps.newHashMap();
	
	public Document() {	
	}
	
	public Document(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
	
	public List<Annotation> getAnnotations(String name) {
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
		List<Annotation> anns = getAnnotations(ann.getName());
		anns.add(ann);
	}
	
	public void clearAnnotations(String name) {
		getAnnotations(name).clear();
	}
}
