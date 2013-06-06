package name.kazennikov.annotations;

public interface TokenType {
	public String name();
	public boolean is(TokenType type);
	public TokenType parent();
}
