package name.kazennikov.tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseToken extends AbstractToken {
	List<AbstractToken> childs;
	Map<String, Object> properties;
	
	
	
	
	public BaseToken(String src, int start, int end, TokenType type, List<AbstractToken> tokens) {
		super(src,start, end, type);
		this.childs = tokens;
	}
	
	@Override
	public int size() {
		if(childs == null || childs.isEmpty())
			return 0;
		
		return childs.size();
	}
	
	@Override
	public AbstractToken getChild(int index) {
		if(index < 0)
			index = size() - index;
		
		return childs.get(index);
	}
	
	public void addToken(AbstractToken token) {
		childs.add(token);
	}

	@Override
	public String text() {
		StringBuilder sb = new StringBuilder();
		for(AbstractToken token : childs) {
			sb.append(token.text());
		}
		
		return sb.toString();
	}
	
	public static BaseToken valueOf(TokenType type, List<AbstractToken> tokens) {
		if(tokens.isEmpty())
			return new BaseToken(null, 0, 0, type, tokens);
		
		int start = tokens.get(0).getStart();
		int end = tokens.get(tokens.size() - 1).getEnd();
		String text = tokens.get(0).getSrc();
		
		return new BaseToken(text, start, end, type, tokens);
	}
	
	public BaseToken subSequence(int from, int to) {
		return valueOf(type, childs.subList(from, to));
	}
	
	@Override
	public String toString() {
		return String.format("%s:%s", type, childs);
	}
	
	public BaseToken trim() {
		int start = 0;
		int end = childs.size() - 1;
		
		while(start < childs.size() && childs.get(start).is(BaseTokenType.SPACE))
			start++;
		
		while(end >= 0 && childs.get(end).is(BaseTokenType.SPACE))
			end--;
		
		if(start >= end)
			return valueOf(type, new ArrayList<AbstractToken>());
		
		return valueOf(type, childs.subList(start, end + 1));
	}
	
	public List<AbstractToken> childs() {
		return childs;
	}
	
	public TokenStream asTokenStream() {
		return new TokenStream(childs);
	}


}
