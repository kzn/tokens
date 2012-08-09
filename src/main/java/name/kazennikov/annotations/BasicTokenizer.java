package name.kazennikov.annotations;

import com.google.common.base.CharMatcher;


public class BasicTokenizer extends UnicodeTokenizer {
    CharMatcher sep;

    public void setSeparator(String sepSet) {
        sep = CharMatcher.anyOf(sepSet);
    }

    @Override
    public boolean isSeparator(char ch) {
        return sep.matches(ch);
    }

    public static void main(String[] args) {
		String text = "Мама мыла раму. Сан=Хосе";
		
		Document d = new Document(text);
		
		Annotator ann = new BasicTokenizer();
		ann.annotate(d);
		
		for(Annotation a : d.get(Annotation.TOKEN)) {
			System.out.printf("'%s' %s%n", a.getText(), a.getFeatureMap());
		}
	}
	
	

}
