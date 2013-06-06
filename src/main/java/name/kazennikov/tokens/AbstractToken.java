package name.kazennikov.tokens;

import java.util.HashMap;
import java.util.Map;

import name.kazennikov.annotations.TokenType;

@Deprecated
public abstract class AbstractToken {
	Map<String, Object> properties;
	TokenType type;
	String src;
	int start;
	int end;
	
	public AbstractToken(String src, int start, int end, TokenType type) {
		this.src = src;
		this.start = start;
		this.end = end;
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
		return src.substring(start, end).toString();
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
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public String getSrc() {
		return src;
	}
}
