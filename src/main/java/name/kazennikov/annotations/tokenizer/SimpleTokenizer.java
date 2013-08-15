/*
 *  DefaultTokeniser.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 2000
 *
 *  $Id: SimpleTokeniser.java 16171 2012-10-27 15:41:23Z markagreenwood $
 */

package name.kazennikov.annotations.tokenizer;

import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import name.kazennikov.annotations.Annotator;
import name.kazennikov.annotations.Document;
import name.kazennikov.fsm.FSMState;

/**
 * Implementation of a Unicode rule based tokeniser. The tokeniser gets its
 * rules from a file an {@link java.io.InputStream InputStream} or a
 * {@link java.io.Reader Reader} which should be sent to one of the
 * constructors. The implementations is based on a finite state machine that is
 * built based on the set of rules. A rule has two sides, the left hand side
 * (LHS)and the right hand side (RHS) that are separated by the &quot;&gt;&quot;
 * character. The LHS represents a regular expression that will be matched
 * against the input while the RHS describes a Gate2 annotation in terms of
 * annotation type and attribute-value pairs. The matching is done using Unicode
 * enumarated types as defined by the {@link java.lang.Character Character}
 * class. At the time of writing this class the suported Unicode categories
 * were:
 * <ul>
 * <li>UNASSIGNED
 * <li>UPPERCASE_LETTER
 * <li>LOWERCASE_LETTER
 * <li>TITLECASE_LETTER
 * <li>MODIFIER_LETTER
 * <li>OTHER_LETTER
 * <li>NON_SPACING_MARK
 * <li>ENCLOSING_MARK
 * <li>COMBINING_SPACING_MARK
 * <li>DECIMAL_DIGIT_NUMBER
 * <li>LETTER_NUMBER
 * <li>OTHER_NUMBER
 * <li>SPACE_SEPARATOR
 * <li>LINE_SEPARATOR
 * <li>PARAGRAPH_SEPARATOR
 * <li>CONTROL
 * <li>FORMAT
 * <li>PRIVATE_USE
 * <li>SURROGATE
 * <li>DASH_PUNCTUATION
 * <li>START_PUNCTUATION
 * <li>END_PUNCTUATION
 * <li>CONNECTOR_PUNCTUATION
 * <li>OTHER_PUNCTUATION
 * <li>MATH_SYMBOL
 * <li>CURRENCY_SYMBOL
 * <li>MODIFIER_SYMBOL
 * <li>OTHER_SYMBOL
 * </ul>
 * The accepted operators for the LHS are "+", "*" and "|" having the usual
 * interpretations of "1 to n occurences", "0 to n occurences" and "boolean OR".
 * For instance this is a valid LHS: <br>
 * "UPPERCASE_LETTER" "LOWERCASE_LETTER"+ <br>
 * meaning an uppercase letter followed by one or more lowercase letters.
 * 
 * The RHS describes an annotation that is to be created and inserted in the
 * annotation set provided in case of a match. The new annotation will span the
 * text that has been recognised. The RHS consists in the annotation type
 * followed by pairs of attributes and associated values. E.g. for the LHS above
 * a possible RHS can be:<br>
 * Token;kind=upperInitial;<br>
 * representing an annotation of type &quot;Token&quot; having one attribute
 * named &quot;kind&quot; with the value &quot;upperInitial&quot;<br>
 * The entire rule willbe:<br>
 * 
 * <pre>
 * "UPPERCASE_LETTER" "LOWERCASE_LETTER"+ > Token;kind=upperInitial;
 * </pre>
 * 
 * <br>
 * The tokeniser ignores all the empty lines or the ones that start with # or
 * //.
 * 
 */

public class SimpleTokenizer implements Annotator {

	public static class RHS {
		List<String> attrName = new ArrayList<>();
		List<String> attrValue = new ArrayList<>();
		String type;
	}

	/**    */
	static protected String defaultResourceName = "creole/tokeniser/DefaultTokeniser.rules";

	/**
	 * A set of string representing tokens to be ignored (e.g. blanks
	 */
	static Set<String> ignoreTokens = new HashSet<>(Arrays.asList(" ", "\t", "\f"));

	/**
	 * The separator from LHS to RHS
	 */
	static String RULE_SEP = ">";

	/**
	 * The static initialiser will inspect the class {@link java.lang.Character}
	 * using reflection to find all the public static members and will map them
	 * to ids starting from 0. After that it will build all the static data:
	 * {@link #typeIds}, {@link #maxTypeId}, {@link #typeMnemonics},
	 * {@link #stringTypeIds}
	 */
	public static TObjectIntHashMap<String> getCharTypes() {

		try {
			Field[] characterClassFields = Class.forName("java.lang.Character").getFields();
			TObjectIntHashMap<String> types = new TObjectIntHashMap<>();

			for (int i = 0; i < characterClassFields.length; i++) {

				Field field = characterClassFields[i];
				
				if (!Modifier.isStatic(field.getModifiers()))
					continue;
				
				
				// JDK 1.4 introduced directionality constants that have the same values
				// as character types; we need to skip those as well
				
				if(field.getName().indexOf("DIRECTIONALITY") != -1)
					continue;
				
				// skip non-byte fields
				if(!field.getType().toString().equals("byte"))
					continue;
				
				String fieldName = field.getName();
				int id = field.getInt(null);
				types.put(fieldName, id);				
			}

			return types;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Parse RHS from format [TokenType]; attr1 = val1; ... attrN = valN;
	 * @param s RHS string
	 * 
	 * @return parsed RHS object
	 */
	public static RHS parseRHS(String s) {
		String[] parts = s.split(";");
		RHS rhs = new RHS();

		if (parts.length < 1)
			return null;

		rhs.type = parts[0];

		for (int i = 1; i < parts.length; i++) {
			String part = parts[i];
			
			if(part.isEmpty())
				continue;
			
			int sep = part.indexOf('=');
			if (sep == -1)
				throw new IllegalStateException("missing attribute value in RHS:" + s);
			String name = part.substring(0, sep).trim();
			String value = part.substring(sep + 1).trim();
			rhs.attrName.add(name);
			rhs.attrValue.add(value);
		}

		return rhs;
	}

	/**
	 * Skips the ignorable tokens from the input returning the first significant
	 * token. The ignorable tokens are defined by {@link #ignoreTokens a set}
	 */
	protected static String skipIgnoreTokens(StringTokenizer st) {
		while (st.hasMoreTokens()) {
			String token = st.nextToken();

			if (ignoreTokens.contains(token))
				continue;

			return token;
		}

		return null;
	}

	private String encoding = "UTF-8";

	TokenizerFSM fsm = new TokenizerFSM();

	private String rulesResourceName;

	private java.net.URL rulesURL;

	TObjectIntHashMap<String> uClasses = getCharTypes();

	/**
	 * The method that does the actual tokenisation.
	 */
	@Override
	public void annotate(Document document) {

		String content = document.getText();
		FSMState<List<RHS>> current = fsm.getStart();
		FSMState<List<RHS>> lastMatch = null;
		// the index of the first character of the token trying to be recognised
		int tokenStartIdx = 0;

		// the index of the last character of the last token recognised
		int lastMatchIdx = -1;
		int charIdx = 0;

		while (charIdx < content.length()) {
			char currentChar = content.charAt(charIdx);
			int charType = Character.getType(currentChar);

			FSMState<List<RHS>> nextState = current.next(charType);

			if (nextState != null) {
				current = nextState;
				if (fsm.isFinal(current)) {
					lastMatchIdx = charIdx;
					lastMatch = current;
				}
				charIdx++;
			} else {
				Map<String, Object> newTokenFm = new HashMap<>();

				if (lastMatch != null) { // we have a match!
					String tokenString = content.substring(tokenStartIdx, lastMatchIdx + 1);
					newTokenFm.put("string", tokenString);
					newTokenFm.put("length", Integer.toString(tokenString.length()));

					String type = null;
					for (RHS rhs : lastMatch.getFinals()) {
						type = rhs.type;
						for (int i = 0; i < rhs.attrName.size(); i++) {
							newTokenFm.put(rhs.attrName.get(i), rhs.attrValue.get(i));
						}
					}

					document.addAnnotation(type, tokenStartIdx, lastMatchIdx + 1, newTokenFm);
					charIdx = lastMatchIdx + 1;
				} else {
					String tokenString = content.substring(tokenStartIdx, tokenStartIdx + 1);
					newTokenFm.put("type", "UNKNOWN");
					newTokenFm.put("string", tokenString);
					newTokenFm.put("length", Integer.toString(tokenString.length()));
					document.addAnnotation("DEFAULT_TOKEN", tokenStartIdx, tokenStartIdx + 1,
							newTokenFm);
					charIdx = tokenStartIdx + 1;

				}

				lastMatch = null;
				current = fsm.getStart();
				tokenStartIdx = charIdx;
			}

		}

		if (lastMatch != null) {
			String tokenString = content.substring(tokenStartIdx, lastMatchIdx + 1);
			Map<String, Object> newTokenFm = new HashMap<>();
			newTokenFm.put("string", tokenString);
			newTokenFm.put("length", Integer.toString(tokenString.length()));
			String type = null;
			for (RHS rhs : lastMatch.getFinals()) {
				type = rhs.type;
				for (int i = 0; i < rhs.attrName.size(); i++) {
					newTokenFm.put(rhs.attrName.get(i), rhs.attrValue.get(i));
				}
			}

			document.addAnnotation(type, tokenStartIdx, lastMatchIdx + 1, newTokenFm);
		}
		
		document.sortAnnotations();

	}

	public String getRulesResourceName() {
		return rulesResourceName;
	}

	/**
	 * This property holds an URL to the file containing the rules for this
	 * tokeniser
	 * 
	 */

	/**
	 * Gets the value of the <code>rulesURL</code> property hich holds an URL to
	 * the file containing the rules for this tokeniser.
	 */
	public java.net.URL getRulesURL() {
		return rulesURL;
	}

	/**
	 * Initialises this tokeniser by reading the rules from an external source
	 * (provided through an URL) and building the finite state machine at the
	 * core of the tokeniser.
	 * 
	 * @exception ResourceInstantiationException
	 */
	public SimpleTokenizer init() throws Exception {
		BufferedReader reader;
		try {
			if (rulesURL != null) {
				reader = new BufferedReader(new InputStreamReader(rulesURL.openStream(), encoding));
			} else {
				// no init data, Scream!
				throw new TokeniserException("No URL provided for the rules!");
			}

			StringBuilder ruleLine = new StringBuilder();

			while (true) {
				String line = reader.readLine();

				if (line == null)
					break;

				line = line.trim();

				if (line.isEmpty())
					continue;

				if (line.endsWith("\\")) {
					ruleLine.append(line.substring(0, line.length() - 1));
					continue;
				}

				ruleLine.append(line);
				parseRule(ruleLine.toString());
				ruleLine.setLength(0);
			}

			TokenizerFSM temp = new TokenizerFSM();
			fsm.epsilonFreeFSM(temp);
			fsm = temp;
			temp = new TokenizerFSM();
			fsm.determinize(temp);
			fsm = temp;
			temp = new TokenizerFSM();
			fsm.minimize(temp);
			fsm = temp;
		} catch (java.io.IOException ioe) {
			throw new TokeniserException(ioe);
		} catch (TokeniserException te) {
			throw new TokeniserException(te);
		}
		return this;
	}

	@Override
	public boolean isApplicable(Document doc) {
		return doc != null;
	}

	/**
	 * Parses a part or the entire LHS.
	 * 
	 * @param startState
	 *            a FSMState object representing the initial state for the small
	 *            FSM that will recognise the (part of) the rule parsed by this
	 *            method.
	 * @param st
	 *            a {@link java.util.StringTokenizer StringTokenizer} that
	 *            provides the input
	 * @param until
	 *            the string that marks the end of the section to be recognised.
	 *            This method will first be called by {@link #parseRule(String)}
	 *            with &quot; &gt;&quot; in order to parse the entire LHS. when
	 *            necessary it will make itself another call to
	 *            {@link #parseLHS parseLHS} to parse a region of the LHS (e.g.
	 *            a &quot;(&quot;,&quot;)&quot; enclosed part.
	 */
	FSMState<List<RHS>> parseLHS(FSMState<List<RHS>> startState,
			StringTokenizer st, String until) throws TokeniserException {

		FSMState<List<RHS>> currentState = startState;
		boolean orFound = false;
		List<FSMState<List<RHS>>> orList = new LinkedList<>();
		String token = skipIgnoreTokens(st);

		if (token == null)
			return currentState;

		FSMState<List<RHS>> newState;

		bigwhile: while (!token.equals(until)) {
			if (token.equals("(")) { // (..)
				newState = parseLHS(currentState, st, ")");
			} else if (token.equals("\"")) { // "unicode_type"
				String sType = parseQuotedString(st, "\"");
				newState = fsm.addState();
				int type = uClasses.get(sType);
				if(type == 0)
					throw new IllegalStateException("unknown unicode type: " + sType);

				fsm.addTransition(currentState, newState, type);
			} else { // a type with no quotes
				String sType = token;
				newState = fsm.addState();
				int type = uClasses.get(sType);
				if(type == 0)
					throw new IllegalStateException("unknown unicode type: " + sType);


				fsm.addTransition(currentState, newState, type);
			}
			// treat the operators
			token = skipIgnoreTokens(st);
			if (null == token)
				throw new InvalidRuleException("Tokeniser rule ended too soon!");

			if (token.equals("|")) {

				orFound = true;
				orList.add(newState);
				token = skipIgnoreTokens(st);
				if (token == null)
					throw new InvalidRuleException("Tokeniser rule ended too soon!");

				continue bigwhile;
			} else if (orFound) {// done parsing the "|"
				orFound = false;
				orList.add(newState);
				newState = fsm.addState();

				for (FSMState<List<RHS>> state : orList) {
					fsm.addTransition(state, newState, 0);
				}
				orList.clear();
			}

			if (token.equals("+")) {

				fsm.addTransition(newState, currentState, 0);
				currentState = newState;
				newState = fsm.addState();
				fsm.addTransition(currentState, newState, 0);
				token = skipIgnoreTokens(st);

				if (token == null)
					throw new InvalidRuleException("Tokeniser rule ended too soon!");
			} else if (token.equals("*")) {

				fsm.addTransition(currentState, newState, 0);
				fsm.addTransition(newState, currentState, 0);
				currentState = newState;
				newState = fsm.addState();
				fsm.addTransition(currentState, newState, 0);
				token = skipIgnoreTokens(st);

				if (token == null)
					throw new InvalidRuleException("Tokeniser rule ended too soon!");
			}
			currentState = newState;
		}

		return currentState;
	}

	/**
	 * Parses from the given string tokeniser until it finds a specific
	 * delimiter. One use for this method is to read everything until the first
	 * quote.
	 * 
	 * @param st
	 *            a {@link java.util.StringTokenizer StringTokenizer} that
	 *            provides the input
	 * @param until
	 *            a String representing the end delimiter.
	 */
	String parseQuotedString(StringTokenizer st, String until) throws TokeniserException {
		if (!st.hasMoreTokens())
			return null;

		String token = st.nextToken();

		StringBuffer type = new StringBuffer();

		while (!token.equals(until)) {
			type.append(token);

			if (st.hasMoreElements())
				token = st.nextToken();
			else
				throw new InvalidRuleException("Tokeniser rule ended too soon!");
		}
		return type.toString();
	}

	/**
	 * Parses one input line containing a tokeniser rule. This will create the
	 * necessary FSMState objects and the links between them.
	 * 
	 * @param line
	 *            the string containing the rule
	 */
	void parseRule(String line) throws TokeniserException {

		if (line.startsWith("#") || line.startsWith("//"))
			return;

		StringTokenizer st = new StringTokenizer(line, "()+*|\" \t\f>", true);
		FSMState<List<RHS>> newState = fsm.addState();

		fsm.addTransition(fsm.getStart(), newState, 0);
		FSMState<List<RHS>> finalState = parseLHS(newState, st, RULE_SEP);
		String rhs = "";

		if (st.hasMoreTokens()) {
			rhs = st.nextToken("\f").trim();

			if (!rhs.isEmpty()) {
				RHS r = parseRHS(rhs);
				finalState.getFinals().add(r);
			}

		}

	} // parseRule

	public void setRulesResourceName(String newRulesResourceName) {
		rulesResourceName = newRulesResourceName;
	}

	/**
	 * Sets the value of the <code>rulesURL</code> property which holds an URL
	 * to the file containing the rules for this tokeniser.
	 * 
	 * @param newRulesURL
	 */

	public void setRulesURL(java.net.URL newRulesURL) {
		rulesURL = newRulesURL;
	}

}