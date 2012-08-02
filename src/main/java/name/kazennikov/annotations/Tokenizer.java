package name.kazennikov.annotations;

import name.kazennikov.tokens.BaseTokenType;
import name.kazennikov.tokens.SimpleTokenizer;
import name.kazennikov.tokens.TextToken;
import name.kazennikov.tokens.TokenType;

public class Tokenizer implements Annotator {
	public static final String TOKEN = "token";
	
	
	public Tokenizer() {
		
	}

	
	@Override
	public void annotate(Document doc) {
		
		String text = doc.getText();
		int pos = 0;
		int start = 0;

		while(pos < text.length()) {
			char ch = text.charAt(pos);

			if(SimpleTokenizer.isSeparator(ch)) {
				if(start != pos) {
					add(doc, start, pos, SimpleTokenizer.infer(text, start, pos));
				}

				int tStart = pos;
				if(Character.isSpaceChar(ch) || Character.isWhitespace(ch)) {
					boolean newLine = (ch == '\n' || ch == '\r');

					while(pos < text.length()) {
						ch = text.charAt(pos);
						newLine = newLine || ch == '\n' || ch == '\r';
						if(!Character.isSpaceChar(ch) && !Character.isWhitespace(ch))
							break;
						pos++;
					}
					add(doc, tStart, pos, newLine? BaseTokenType.NEWLINE : BaseTokenType.SPACE);
				} else {
					int puncStart = pos;
					while (pos < text.length() && text.charAt(pos) == ch)
						pos++;
					add(doc, puncStart, pos, BaseTokenType.PUNC);
				}
				start = pos;
				continue;
			}

			pos++;
		}
		if(start != pos) {
			add(doc, start, pos, SimpleTokenizer.infer(text, start, pos));
		}
	}
	
	Annotation makeAnnotation(int start, int end, TokenType type) {
		Annotation ann = new Annotation(TOKEN, start, end);
		ann.setFeature("type", type);
		
		return ann;
	}
	
	void add(Document d, int start, int end, TokenType type) {
		d.addAnnotation(makeAnnotation(start, end, type));
	}
	
	
	public static void main(String[] args) {
		String text = "Мама мыла раму.";
		
		Document d = new Document(text);
		
		Annotator ann = new Tokenizer();
		ann.annotate(d);
		
		for(Annotation a : d.get(TOKEN)) {
			System.out.printf("'%s' %s%n", a.getText(), a.getFeatureMap());
		}
	}
	
	

}
