package name.kazennikov.annotations.patterns;

import java.util.List;

import name.kazennikov.annotations.Annotation;

public class AnnotationMatchers {
	
	private AnnotationMatchers() {
		
	}
	
	public static abstract class BaseMatcher implements AnnotationMatcher {
		String type;
		
		public BaseMatcher(String type) {
			this.type = type;
		}
		
//		@Override
//		public AnnotationMatcher complement() {
//			final BaseMatcher bm = this;
//			return new AnnotationMatcher() {
//				
//				@Override
//				public boolean match(Annotation a) {
//					return !bm.match(a);
//				}
//				
//				@Override
//				public AnnotationMatcher complement() {
//					return bm;
//				}
//			};
//		}

		@Override
		public String getType() {
			return type;
		}

	}
	
	public static abstract class BaseFeatureMatcher extends BaseMatcher {
		String name;
		Object value;
		
		public BaseFeatureMatcher(String type, String name, Object value) {
			super(type);
			this.name = name;
			this.value = value;
		}
		
		public Object getValue(Annotation a) {
			if(!a.getType().equals(type))
				return null;
			
			return a.getFeature(name);
		}

	}
		
		
	public static class TypeMatcher extends BaseMatcher {

		public TypeMatcher(String type) {
			super(type);
		}

		@Override
		public boolean match(Annotation a) {
			return a.getType().equals(type);
		}
		
		@Override
		public String toString() {
			return String.format("{%s}", type);
		}
		
		
	}
	
	public static class FeatureEqMatcher extends BaseFeatureMatcher {
		public FeatureEqMatcher(String type, String name, Object value) {
			super(type, name, value);
		}
		
		@Override
		public boolean match(Annotation a) {
			Object v = getValue(a);
			return v == null? false : v.equals(value);
		}
		
		@Override
		public String toString() {
			return String.format("{%s.%s == %s}", type, name, value);
		}
	}
	
	public static class FeatureNEqMatcher extends BaseFeatureMatcher {
		public FeatureNEqMatcher(String type, String name, Object value) {
			super(type, name, value);
		}
		
		@Override
		public boolean match(Annotation a) {
			Object v = getValue(a);
			return v == null? false : !v.equals(value);
		}
		
		@Override
		public String toString() {
			return String.format("{%s.%s != %s}", type, name, value);
		}
	}
	
	public static class NegativeAnnotationMatcher implements AnnotationMatcher {
		AnnotationMatcher matcher;
		
		public NegativeAnnotationMatcher(AnnotationMatcher matcher) {
			this.matcher = matcher;
		}
		@Override
		public boolean match(Annotation a) {
			return !matcher.match(a);
		}

		@Override
		public String getType() {
			return matcher.getType();
		}
		
	}

	
	public static class ANDMatcher implements AnnotationMatcher {
		List<AnnotationMatcher> matchers;

		public ANDMatcher(List<AnnotationMatcher> matchers) {
			this.matchers = matchers;
		}

		@Override
		public boolean match(Annotation a) {
			for(AnnotationMatcher m : matchers) {
				if(!m.match(a))
					return false;
			}
			return true;
		}

		@Override
		public String getType() {
			return null;
		}
	}



	

}
