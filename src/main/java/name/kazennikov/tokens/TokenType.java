package name.kazennikov.tokens;

public interface TokenType {
	public String name();
	public boolean is(TokenType type);
	public TokenType parent();
}
