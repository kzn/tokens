package name.kazennikov.tokens;

public class NLPTokenType {
	public static final TokenType WORD = BaseTokenType.add("WORD", BaseTokenType.TEXT);
	public static final TokenType COMPOSITE_WORD = BaseTokenType.add("COMPOSITE-WORD", NLPTokenType.WORD);
	public static final TokenType SENTENCE = BaseTokenType.add("SENTENCE", BaseTokenType.TEXT);
}
