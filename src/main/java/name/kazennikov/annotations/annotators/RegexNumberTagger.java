package name.kazennikov.annotations.annotators;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import name.kazennikov.annotations.AnnotationConstants;
import name.kazennikov.annotations.Annotator;
import name.kazennikov.annotations.Document;

public class RegexNumberTagger implements Annotator {
	private static final Pattern p = Pattern.compile("\\d+");

	@Override
	public boolean isApplicable(Document doc) {
		return true;
	}

	@Override
	public void annotate(Document doc) {
		Matcher m = p.matcher(doc);
		while(m.find()) {
			Long l = Long.parseLong(m.group());
			Map<String, Object> feats = new HashMap<>();
			feats.put("value", l);
			doc.addAnnotation(AnnotationConstants.NUMBER, m.start(), m.end(), feats);
		}
		
		doc.sortAnnotations();
		
	}
	
	@Override
	public String getName() {
		return "Regex number tagger";
	}
	

}
