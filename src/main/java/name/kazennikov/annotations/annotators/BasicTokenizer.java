package name.kazennikov.annotations.annotators;

import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.Annotator;
import name.kazennikov.annotations.Document;

import com.google.common.base.CharMatcher;

/**
 * Basic tokenizer that splits tokens on given 
 * closed set of separator characters.
 * 
 * <p>
 * Default separator set is : <b><pre>,.!?()[]\"'$%^&*#{}\|/</pre></b>
 * 
 * 
 * @author Anton Kazennikov
 *
 */
public class BasicTokenizer extends AbstractTokenizer {
    CharMatcher sep = CharMatcher.anyOf(",.!?()[]\"'$%^&*#{}\\|/");

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
