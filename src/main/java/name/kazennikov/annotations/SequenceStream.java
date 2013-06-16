package name.kazennikov.annotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Sequence Stream. It is an extension of List that supports current position in the stream.
 * <p>
 * It could be implemented using List, but it has following distictions:
 * <ul>
 * <li> it is positional: it has current postion and operations to change positions
 * <li> there is a user-specified null object (if not specified, ordinary null value is used)
 * <li> null object is returned on out-of-bounds operations
 * <li> implementation of relative operations: current(), next(), prev(), peek(). So one can query for elements relative
 * to current positon. It is handy for sequence oprations
 * <li> null checking is done by isNull() methods
 * </ul>
 * 
 * 
 * @author Anton Kazennikov
 *
 * @param <E> stream element type
 */
public class SequenceStream<E> {
	List<E> elements;
	E nullObject;
	int pos;

	/**
	 * Construct a sequence stream
	 * 
	 * @param nullObject null object
	 * @param elements list of elements (assumed that they are sorted in ascending order)
	 */
	public SequenceStream(E nullObject, Collection<? extends E> elements) {
		this(nullObject, elements, null);
	}
	
	/**
	 * Generic constuctor for sequence stream
	 * 
	 * @param nullObject null object
	 * @param elements elements of the sequence
	 * @param comparator comparator to sort elements in desired order, or null, if sorting is not needed
	 */
	public SequenceStream(E nullObject, Collection<? extends E> elements, Comparator<E> comparator) {
		this.elements = new ArrayList<E>(elements);
		this.nullObject = nullObject;
		this.pos = 0;
		
		if(comparator != null) {
			Collections.sort(this.elements, comparator);
		}
	}
	
	/**
	 * Checks if current position is null object
	 * 
	 * @return
	 */
	public boolean isNull() {
		return current() == nullObject;
	}
	
	/**
	 * Checks if given object is null object
	 * 
	 * @param object
	 * @return
	 */
	public boolean isNull(E object) {
		return object == nullObject;
	}
	
	/**
	 * Get null object of this sequence stream
	 * @return
	 */
	public E getNull() {
		return nullObject;
	}

	/**
	 * Return current element
	 */
	public E current() {
		if(pos >= 0 && pos < elements.size())
			return elements.get(pos);
		return nullObject;
	}


	/**
	 * Move to previous element and return it
	 */
	public E prev() {
		pos--; 
		return current();
	}

	/**
	 * Move to next element and return it
	 */
	public E next() {
		pos++;
		return current();
	}

	/**
	 * Return total size of elements in the stream
	 * 
	 * @return
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * Get current position in the sequence
	 * 
	 * @return
	 */
	public int pos() {
		return pos;
	}


	/**
	 * Peek next element, equivalent of current(1)
	 * 
	 * @return
	 */
	public E peek() {
		int p = pos + 1;
		if(p >= 0 && p < elements.size())
			return elements.get(p);
		
		return nullObject;
	}
	

	/**
	 * Get element given offset relative to current position
	 * 
	 * @param offset relative offset of the element
	 * @return actual element from the stream, or null object if position is out of bounds
	 */
	public E current(int offset) {
		int p = pos + offset;
		if(p >= 0 && p < elements.size())
			return elements.get(p);
		
		return nullObject;
	}


	/**
	 * Reset position to the start of the stream
	 */
	public void reset() {
		pos = 0;
	}


	/**
	 * Set absolute position in the stream
	 * 
	 * @param pos position to set
	 */
	public void setPos(int pos) {
		this.pos = pos < 0? 0 : pos;
	}
	
	/**
	 * Return stream content as a list of elements
	 * 
	 * @return
	 */
	public List<E> elements() {
		return elements;
	}


	/**
	 * Checks if stream is empty, i.e. the current position is at the end of stream
	 * @return
	 */
	public boolean isEmpty() {
		return pos >= elements.size();
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
	 * Replace current element
	 * 
	 * @param current replacement element
	 */
	public void setCurrent(E current) {
		if(pos >= 0 && pos < elements.size())
			elements.set(pos, current);
	}
	
	/**
	 * Append new element at the end of the sequence
	 * @param element new element to append
	 */
	public void append(E element) {
		elements.add(element);
	}
	
	/**
	 * Return reversed sequence
	 * 
	 * @return
	 */
	public SequenceStream<E> reverse() {
		List<E> elements = new ArrayList<E>(this.elements);
		Collections.reverse(elements);
		return new SequenceStream<E>(nullObject, elements);
	}
	
	/**
	 * Get element at absolute position
	 * 
	 * @param index index of the element
	 */
	public E get(int index) {
		if(index < 0 || index >= elements.size())
			return nullObject;
		
		return elements.get(index);
	}
}
