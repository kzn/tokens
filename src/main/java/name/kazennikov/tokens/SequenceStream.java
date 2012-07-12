package name.kazennikov.tokens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generic version of TokenStream
 * @author ant
 *
 * @param <E>
 */
public class SequenceStream<E> {
	List<E> tokens;
	E nullObject;
	int pos;
	
	public SequenceStream(E nullObject, List<? extends E> tokens) {
		this.tokens = new ArrayList<E>(tokens);
		this.nullObject = nullObject;
		this.pos = 0;
	}
	
	public boolean isNull() {
		return current() == nullObject;
	}
	
	public E getNull() {
		return nullObject;
	}

	/**
	 * Return current token
	 */
	public E current() {
		if(pos >= 0 && pos < tokens.size())
			return tokens.get(pos);
		return nullObject;
	}


	/**
	 * Move to previous token and return it
	 */
	public E prev() {
		pos--; 
		return current();
	}

	/**
	 * Move to next token and return it
	 */
	public E next() {
		pos++;
		return current();
	}

	/**
	 * Return total size of the token stream
	 * @return
	 */
	public int size() {
		return tokens.size();
	}

	/**
	 * Return current position
	 * @return
	 */
	public int pos() {
		return pos;
	}


	/**
	 * Peek next token, equivalent of current(1)
	 * @return
	 */
	public E peek() {
		int p = pos + 1;
		if(p >= 0 && p < tokens.size())
			return tokens.get(p);
		
		return nullObject;
	}
	

	/**
	 * Get a token wrt offset from the current position
	 * @param offset offset of the token wrt current position
	 * @return actual token from the stream, or Token.NULL if position is out of bounds
	 */
	public E current(int offset) {
		int p = pos + offset;
		if(p >= 0 && p < tokens.size())
			return tokens.get(p);
		
		return nullObject;
	}


	/**
	 * Reset to the start of the stream
	 */
	public void reset() {
		pos = 0;
	}


	/**
	 * Set absolute position in the stream
	 * @param pos position to set
	 */
	public void setPos(int pos) {
		this.pos = pos < 0? 0 : pos;
	}
	
	/**
	 * Return stream content as a list of tokens
	 * @return
	 */
	public List<E> tokens() {
		return tokens;
	}


	/**
	 * Checks if stream is empty, i.e. the current position is at the end of stream
	 * @return
	 */
	public boolean isEmpty() {
		return pos >= tokens.size();
	}


	/**
	 * Checks if the current position is at the start of the stream
	 * @return
	 */
	public boolean isStart() {
		return pos == 0;
	}
	
	/**
	 * Rewind to the start of the stream
	 */
	public void rewind() {
		pos = 0;
	}
	
	/**
	 * Replace current token with given
	 * @param current replacement token
	 */
	public void setCurrent(E current) {
		if(pos >= 0 && pos < tokens.size())
			tokens.set(pos, current);
	}
	
	/**
	 * Append new token to the last token of this stream
	 * @param token token to append
	 */
	public void append(E token) {
		tokens.add(token);
	}
	
	/**
	 * Return reversed sequence of tokens
	 * @return
	 */
	public SequenceStream<E> reverse() {
		List<E> tokens = new ArrayList<E>(this.tokens);
		Collections.reverse(tokens);
		return new SequenceStream<E>(nullObject, tokens);
	}
	
	/**
	 * Get token at absolute position
	 * @param index index of the token
	 */
	public E get(int index) {
		if(index < 0 || index >= tokens.size())
			return nullObject;
		
		return tokens.get(index);
	}
}
