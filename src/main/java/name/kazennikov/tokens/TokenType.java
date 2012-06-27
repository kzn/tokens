package name.kazennikov.tokens;

public interface TokenType {
	public boolean is(TokenType type);
	public TokenType parent();
}
