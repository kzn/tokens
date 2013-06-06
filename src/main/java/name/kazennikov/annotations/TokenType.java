package name.kazennikov.annotations;

/**
 * Basic tree-like type hierarchy (now for tokens)
 * @author Anton Kazennikov
 *
 */
public interface TokenType {
	/**
	 * Returns type name
	 */
	public String name();
	/**
	 * Checks if given type is compatible with <code>this</code>
	 * @param type type to check
	 */
	public boolean is(TokenType type);
	
	/**
	 * Get immediate parent of this type
	 * @return parent type, or null if there is no parent
	 */
	public TokenType parent();
}
