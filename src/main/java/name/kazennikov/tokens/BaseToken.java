package name.kazennikov.tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseToken extends AbstractToken {
	List<AbstractToken> childs;
	Map<String, Object> properties;
	
	
	public BaseToken(List<AbstractToken> tokens, TokenType type) {
		super(type);
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
	
	public BaseToken subSequence(int from, int to) {
		return new BaseToken(childs.subList(from, to), type);
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
			return new BaseToken(new ArrayList<AbstractToken>(), type);
		
		return new BaseToken(childs.subList(start, end + 1), type);
	}
	
	public List<AbstractToken> childs() {
		return childs;
	}
	
	public TokenStream asTokenStream() {
		return new TokenStream(childs);
	}


}
