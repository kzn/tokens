package name.kazennikov.annotations.patterns;

import java.util.List;

import name.kazennikov.annotations.Annotation;

public class AnnotationMatchers {
	
	private AnnotationMatchers() {
		
	}
	
	public static abstract class BaseMatcher implements AnnotationMatcher {
		@Override
		public AnnotationMatcher complement() {
			final BaseMatcher bm = this;
			return new AnnotationMatcher() {
				
				@Override
				public boolean match(Annotation a) {
					return !bm.match(a);
				}
				
				@Override
				public AnnotationMatcher complement() {
					return bm;
				}
			};
		}
	}
		
		
	public static class TypeMatcher extends BaseMatcher {
		String type;

		public TypeMatcher(String type) {
			this.type = type;
		}

		@Override
		public boolean match(Annotation a) {
			return a.getType().equals(type);
		}
	}
	
	public static class FeatureEqMatcher extends BaseMatcher {
		String type;
		String name;
		Object value;
		
		public FeatureEqMatcher(String type, String name, Object value) {
			this.type = type;
			this.name = name;
			this.value = value;
		}
		
		@Override
		public boolean match(Annotation a) {
			Object o = a.getFeature(name);
			if(o == null)
				return false;
			
			return value.equals(o);
		}
	}
	
	public static class ANDMatcher extends BaseMatcher {
		List<AnnotationMatcher> matchers;

		public ANDMatcher(List<AnnotationMatcher> matchers) {
			super();
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
	}



	

}
