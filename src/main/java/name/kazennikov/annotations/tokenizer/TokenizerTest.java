package name.kazennikov.annotations.tokenizer;

import java.io.File;

import name.kazennikov.annotations.Document;

public class TokenizerTest {
	public static void main(String[] args) throws Exception {
		SimpleTokenizer tokenizer = new SimpleTokenizer();
		tokenizer.setRulesURL(new File("data/tokenizer/DefaultTokeniser.rules").toURL());
		tokenizer.init();
		
		Document d = new Document("Мама мыла раму. Это был Иванов-Крамской.");
		tokenizer.annotate(d);
	}
}
