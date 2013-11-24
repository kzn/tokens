package name.kazennikov.annotations.annotators;

import java.util.HashMap;
import java.util.Map;

import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.AnnotationConstants;
import name.kazennikov.annotations.Annotator;
import name.kazennikov.annotations.Document;

public class TokenNumberTagger implements Annotator {
	
	public String getInputAnnotation() {
		return inputAnnotation;
	}

	public void setInputAnnotation(String inputAnnotation) {
		this.inputAnnotation = inputAnnotation;
	}

	String inputAnnotation = AnnotationConstants.NUMBER;
	
	
	
	@Override
	public boolean isApplicable(Document doc) {
		return doc.contains(inputAnnotation);
	}

	@Override
	public void annotate(Document doc) {
		for(Annotation a : doc.get(inputAnnotation)) {
			boolean allDigits = true;
			for(int i = 0; i < a.length(); i++) {
				char ch = a.charAt(i);
				if(!Character.isDigit(ch)) {
					allDigits = false;
					break;
				}
			}
			
			if(allDigits) {
				Long l = Long.parseLong(a.getText());
				Map<String, Object> feats = new HashMap<>();
				feats.put("value", l);
				doc.addAnnotation(AnnotationConstants.NUMBER, a.getStart(), a.getEnd(), feats);

			}
		}
		
		
	}
	
	@Override
	public String getName() {
		return "Token number tagger";
	}



}
