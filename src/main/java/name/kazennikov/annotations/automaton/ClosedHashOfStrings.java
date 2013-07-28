/*
 *  ClosedHashOfStrings.java
 *
 *  Copyright (c) 2010-2011, Ontotext (www.ontotext.com).
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *
 *  $Id$
 */
package name.kazennikov.annotations.automaton;

/**
 * This class implements closed hash of elements of type String
 * 
 * @author petar.mitankin
 * 
 */
public class ClosedHashOfStrings extends ClosedHashOfObjects<String> {

	/**
	 * Puts a string in the closed hash.
	 * 
	 * @param s
	 * @return the number of the string
	 */
	@Override
	public int put(String s) {
		return put(s);
	}

	/**
	 * Gets an array of all strings that are stored in the closed hash.
	 * 
	 * @return
	 */
	public String[] getCopyOfStrings() {
		String[] s = new String[objectsStored];

		for (int i = 0; i < objectsStored; i++) {
			s[i] = (String) objects[i];
		}
		return s;
	}

	/**
	 * Gets the number of strings that are stored in the closed hash.
	 * 
	 * @return
	 */
	public int getStringsStored() {
		return (objectsStored);
	}

	@Override
	protected int getHashCode(String o) {
		String s = o;
		int length = s.length();
		int code = 0;
		for (int i = 0; i < length; i++) {
			code = CodeInt.code(s.charAt(i), code, hash.length);
		}
		return (code);
	}
}
