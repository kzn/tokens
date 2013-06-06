package name.kazennikov.annotations;

import java.util.HashMap;

/**
 * Base type hierarchy for tokens 
 * 
 * @author Anton Kazennikov
 *
 */
public class BaseTokenType implements TokenType {
	final TokenType parent;
	final String name;

	private BaseTokenType(String name, TokenType parent) {
		this.name = name;
		this.parent = parent;
	}
	
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	private static HashMap<String, TokenType> types = new HashMap<String, TokenType>();
	public final static TokenType NULL = BaseTokenType.add("null", null);
	public final static TokenType TEXT = BaseTokenType.add("text", null);
	
	// separators
	public final static TokenType SEPARATOR = BaseTokenType.add("sep", null);
	public final static TokenType PUNC = BaseTokenType.add("punc", SEPARATOR);

	// whitespace
	public final static TokenType WHITESPACE = BaseTokenType.add("ws", SEPARATOR);
	public final static TokenType SPACE = BaseTokenType.add("space", WHITESPACE);
	public final static TokenType NEWLINE = BaseTokenType.add("newline", WHITESPACE);

	// text
	public final static TokenType ALPHANUM = BaseTokenType.add("alphanum", TEXT);
	public final static TokenType LETTERS = BaseTokenType.add("letters", TEXT);
	public final static TokenType DIGITS = BaseTokenType.add("digits", TEXT);
	public final static TokenType MISC = BaseTokenType.add("misc", TEXT);


	/**
	 * Check if given token type is equivalent to this
	 * 'true, if <b>this</b> is t'
	 * @param type given token type
	 * @return
	 */
	@Override
	public boolean is(TokenType type) {
		if(this == type)
			return true;
		
		TokenType p = this.parent;
		
		while(p != null) {
			if(p == type)
				return true;
			
			p = p.parent();
		}
		
		return false;
				
	}
	

	/**
	 * Get type by name.
	 * @param name type name.
	 * @return if type doesn't exist, return null
	 */
	public static TokenType getType(String name) {
		return types.get(name);
	}
	
	/**
	 * Add type or return an existing type
	 * @param name type name
	 * @param parent parent
	 * @return
	 */
	public static TokenType add(String name, TokenType parent) {
		TokenType t = types.get(name);
		
		if(t != null)
			return t;
		
		t = new BaseTokenType(name, parent);
		types.put(name, t);
		return t;
	}

	@Override
	public TokenType parent() {
		return parent;
	}
}
