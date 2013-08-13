/*
 *  TokeniserException.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 * 
 *  Valentin Tablan, 27/06/2000
 *
 *  $Id: TokeniserException.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package name.kazennikov.annotations.tokenizer;

/** The top level exception for all the exceptions fired by the tokeniser */
public class TokeniserException extends Exception {

	public TokeniserException(String message) {
		super(message);
	}

	public TokeniserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public TokeniserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

} // class TokeniserException
