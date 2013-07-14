package name.kazennikov.annotations.patterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.AnnotationList;
import name.kazennikov.annotations.Document;

import com.google.common.base.Objects;

public class SimpleRHS implements RHS {
	public static abstract class Value {
		String name;
		
		public Value(String name) {
			this.name = name;
		}
		
		public String name() {
			return name;
		}
		
		public abstract Object value(Map<String, AnnotationList> bindings);
	}
	
	public static class SimpleValue extends Value {
		Object value;
		
		public SimpleValue(String name, Object value) {
			super(name);
			this.value = value;
		}

		@Override
		public Object value(Map<String, AnnotationList> bindings) {
			return value;
		}
		
		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("name", name)
					.add("value", value)
					.toString();
		}
	}
	
	public static class BindingValue extends Value {
		String binding;
		String annotation;
		String feature;
		
		public BindingValue(String name, String binding, String annotation, String feature) {
			super(name);
			this.binding = binding;
			this.annotation = annotation;
			this.feature = feature;
		}

		@Override		
		public Object value(Map<String, AnnotationList> bindings) {
			AnnotationList l = bindings.get(binding);
			if(l == null)
				return null;
			
			for(Annotation a : l) {
				if(a.getType().equals(annotation)) {
					return a.getFeature(feature);
				}
			}
			
			return null;
		}
		
		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("name", name)
					.add("value", String.format(":%s.%s.%s", binding, annotation, feature))
					.toString();
		}

		
		
		
	}
	
	
	
	
	String bindingName; // target binding
	String type; // target type
	List<Value> values = new ArrayList<>();

	@Override
	public boolean execute(Document doc, AnnotationList input, Map<String, AnnotationList> bindings) {
		AnnotationList binding = bindings.get(bindingName);
		if(binding != null) {
			int start = binding.get(0).getStart();
			int end = binding.get(binding.size() - 1).getEnd();
			Map<String, Object> feats = new HashMap<>();
			
			for(Value v : values) {
				Object value = v.value(bindings);
				if(value != null) {
					feats.put(v.name(), value);
				}
			}
			
			doc.addAnnotation(type, start, end, feats);
		}

		return true;
	}

}
