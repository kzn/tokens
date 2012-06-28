package name.kazennikov.tokens;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TextToken extends AbstractToken {

	
	public TextToken(Span span, TokenType type) {
		super(span, type);
	}
	
	@Override
	public String text() {
		return span.toText();
	}
	
	public static TextToken valueOf(Span span, TokenType type) {
		return new TextToken(span, type);
	}
	
	//public static TextToken makeSpace = TextToken.valueOf(" ", BaseTokenType.SPACE);
	//public static final TextToken NEWLINE = TextToken.valueOf("\n", BaseTokenType.SPACE);
	//public static final TextToken COMMA = TextToken.valueOf(",", BaseTokenType.PUNC);
	//public static final TextToken DOT = TextToken.valueOf(".", BaseTokenType.PUNC);
	//public static final TextToken EMPTY = TextToken.valueOf("", BaseTokenType.SPACE);
	public static final TextToken NULL = TextToken.valueOf(Span.NULL, BaseTokenType.NULL);

	@Override
	public String toString() {
		return String.format("#text:'%s'%s,%s", text(), type, properties);
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
