package name.kazennikov.tokens;

import java.util.List;
import java.util.Set;

public class TaggedToken<E> extends BaseToken {
	E tag;
	List<E> possibleTags;

	public TaggedToken(Span span, List<AbstractToken> tokens, TokenType type, List<E> possibleTags) {
		super(span, type, tokens);
		this.possibleTags = possibleTags;
	}
	
	public E getTag() {
		return tag;
	}
	
	public void setTag(E tag) {
		this.tag = tag;
	}
	
	public List<E> possibleTags() {
		return possibleTags;
	}
	
	public void setPossibleTags(List<E> tags) {
		this.possibleTags = tags;
	}

}
