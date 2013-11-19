package name.kazennikov.annotations.patterns;

import java.util.HashMap;
import java.util.Map;

import name.kazennikov.annotations.patterns.AnnotationMatchers.FeatureAccessor;

public class JapeConfiguration {
	Map<String, FeatureAccessor> accessors = new HashMap<String, AnnotationMatchers.FeatureAccessor>();
	
	
	public JapeConfiguration() {
		registerMetaFeature(new AnnotationMatchers.StringMetaFeatureAccessor());
		registerMetaFeature(new AnnotationMatchers.LengthMetaFeatureAccessor());
	}

	public FeatureAccessor getMetaAccessor(String feat) {
		return accessors.get(feat);
	}
	
	public void registerMetaFeature(FeatureAccessor fa) {
		accessors.put(fa.getName(), fa);
	}


}
