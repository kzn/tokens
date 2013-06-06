package name.kazennikov.tokens;

import gnu.trove.list.array.TIntArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Implementation of a token list.
 * Optionally supports a null object - object that is returned on out-of-bounds operations.
 * By default, null is returned.<br>
 * 
 * Supports dual views of the token list:<ul>
 * <li> as a list of the tokens
 * <li> as a sequence of characters
 * </ul>
 * @author Anton Kazennikov
 *
 * @param <E> type of the token
 */
@Deprecated
public class TokenList<E extends AbstractToken> implements CharSequence {
	List<E> tokens = new ArrayList<E>();
	E nullObject;
	TIntArrayList ends = new TIntArrayList();
	StringBuilder sb = new StringBuilder();
	
	public TokenList(E nullObject, Collection<? extends E> tokens) {
		addAll(tokens);
		this.nullObject = nullObject;
	}
	
	public TokenList(Collection<? extends E> tokens) {
		this(null, tokens);
	}
	
	public E getNullObject() {
		return nullObject;
	}
	
	public void setNullObject(E nullObject) {
		this.nullObject = nullObject; 
	}
	
	/**
	 * Get token at specified index
	 * @param index of the token
	 * @return token or nullObject, if index is out of bounds
	 */
	public E get(int index) {
		if(isIndexValid(index))
			return tokens.get(index);
		
		return nullObject;
	}
	
	
	/**
	 * Get if the index is in bounds
	 * @param index to check
	 */
	public boolean isIndexValid(int index) {
		return index >= 0 && index < tokens.size();
	}
	
	/**
	 * Add new token to the list
	 * @param token token to add
	 */
	public void add(E token) {
		tokens.add(token);
		sb.append(token.text());
		ends.add(sb.length());
	}
	
	/**
	 * Get token, specified by char index of the list
	 * @param index character index in the token list
	 * @return token, or nullObject if out of bounds
	 */
	public E getToken(int index) {
		return get(tokenIndex(index));
	}
	
	/**
	 * Check if that char index is at the end of token list
	 * @param index character index
	 */
	public boolean isAtTokenEnd(int index) {
		return index == sb.length() || ends.binarySearch(index) >=  0;
	}
	
	/**
	 * Check if that character index is at the start of the token list
	 * @param index character index
	 */
	public boolean isAtTokenStart(int index) {
		return index == 0 || ends.binarySearch(index - 1) >= 0;
	}
	
	/**
	 * Get next token wrt to the given character position
	 * @param pos character position
	 * @return token, or nullObject if out-of-bounds
	 */
	public E nextToken(int pos) {
		int i = tokenIndex(pos);
		return get(i + 1);
	}
	
	/**
	 * Get previous token wrt to the given character position
	 * @param pos character position
	 * @return token, or nullObject is out-of-bounds
	 */
	public E prevToken(int pos) {
		return get(tokenIndex(pos) - 1);
	}
	
	/**
	 * Get character position of the current token given the character position within it
	 * @param character position
	 * @return start of next token
	 */
	public int skipToken(int pos) {
		int i = tokenIndex(pos);
		if(i < tokens.size())
			return ends.get(i);
		return length();
	}

	/**
	 * Get index of the token given the character position
	 * @param pos character position
	 * @return token index
	 */
	public int tokenIndex(int pos) {
		int index = ends.binarySearch(pos);
		if(index < 0)
			index = -index - 1;
		else 
			index++;
		
		return index;

		
	}
	
	public void addAll(Collection<? extends E> tokens) {
		for(E token : tokens) {
			add(token);
		}
	}
	
	/**
	 * Return length of the token list in tokens
	 * @return
	 */
	public int size() {
		return tokens.size();
	}


	/**
	 * Return length of the token list in characters
	 */
	@Override
	public int length() {
		return sb.length();
	}


	/**
	 * Get character at the given index
	 */
	@Override
	public char charAt(int index) {
		return sb.charAt(index);
	}

	@Override
	public TokenList<E> subSequence(int start, int end) {
		start = ends.binarySearch(start);
		if(start < 0)
			start = -start - 1;
		end = ends.binarySearch(end);
		if(end < 0)
			end = end - 1;
		end++;
		if(end > tokens.size())
			end = tokens.size();
		
		return new TokenList<E>(nullObject, tokens.subList(start, end));
	}
	
	public String substring(int start, int end) {
		return sb.substring(start, end);
	}
	
	public int tokenEnd(int index) {
		return ends.get(index);
	}
	
	/**
	 * Checks if token list contains a string starting from given position. 
	 * Behave like String.startsWith()
	 * @param pos start position to check
	 * @param str string to check
	 */
	public boolean startsWith(int pos, String str) {
		if(pos + str.length() < sb.length() || pos < 0)
			return false;
		
		for(int i = 0; i != str.length(); i++) {
			if(sb.charAt(pos + i) != str.charAt(i))
				return false;
		}
		
		return true;
	}

}
