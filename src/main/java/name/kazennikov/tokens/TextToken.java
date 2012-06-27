package name.kazennikov.tokens;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TextToken extends AbstractToken {
	String text;
	
	public TextToken(String text, TokenType type) {
		super(type);
		this.text = text;
	}
	
	@Override
	public String text() {
		return text;
	}
	
	public static TextToken valueOf(String text, TokenType type) {
		return new TextToken(text, type);
	}
	
	public static final TextToken SPACE = TextToken.valueOf(" ", BaseTokenType.SPACE);
	public static final TextToken NEWLINE = TextToken.valueOf("\n", BaseTokenType.SPACE);
	public static final TextToken COMMA = TextToken.valueOf(",", BaseTokenType.PUNC);
	public static final TextToken DOT = TextToken.valueOf(".", BaseTokenType.PUNC);
	public static final TextToken EMPTY = TextToken.valueOf("", BaseTokenType.SPACE);
	public static final TextToken NULL = TextToken.valueOf("", BaseTokenType.NULL);

	@Override
	public String toString() {
		return String.format("#text:'%s'%s,%s", text, type, properties);
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public AbstractToken getChild(int index) {
		return null;
	}

}
