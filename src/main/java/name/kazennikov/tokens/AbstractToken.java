package name.kazennikov.tokens;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractToken {
	Map<String, Object> properties;
	TokenType type;
	Span span;
	
	public AbstractToken(Span span, TokenType type) {
		this.span = span;
		this.type = type;
	}
	
	public boolean hasProperty(String prop) {
		if(properties == null)
			return false;
		
		return properties.containsKey(prop);
	}
	
	public void setProperty(String prop, Object value) {
		if(properties == null)
			properties = new HashMap<String, Object>();
		
		properties.put(prop, value);
	}
	
	public Object getProperty(String prop) {
		if(properties == null)
			return null;
		
		return properties.get(prop);
	}
	
	public String text() {
		return span.toText();
	}
	
	public abstract int size();
	public abstract AbstractToken getChild(int index);
	
	public TokenType type() {
		return type;
	}
	
	public void setTokenType(TokenType type) {
		this.type = type;
	}
	
	public boolean is(TokenType type) {
		return this.type.is(type);
	}
	
	public Map<String, Object> getProperties() {
		return properties;
	}
	
	public Span getSpan() {
		return span;
	}
}
