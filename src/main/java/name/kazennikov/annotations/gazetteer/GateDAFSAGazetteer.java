package name.kazennikov.annotations.gazetteer;

import name.kazennikov.annotations.AnnotationEngineException;
import name.kazennikov.annotations.Document;

public class GateDAFSAGazetteer extends AbstractDAFSAGazetteer {
	boolean wholeWordsOnly = true;
	
	
	@Override
	public void annotate(Document doc) {
		if(doc == null) {
			throw new AnnotationEngineException("No document to process!");
		}
		/**
		 * Идеи для построения газетира:
		 * 1. газетир общего назначения, который матчит все подряд (как в GATE)
		 * 2. газетир, который создает lookup только на границах токенов 
		 * (т.е. lookup начинается с начала токена и заканчивается концом токена)
		 */

		int length = doc.length();
		char currentChar;

		int currentState = 0;

		int lastMatchingState = 0;
		int matchedRegionEnd = 0;
		int matchedRegionStart = 0;
		int charIdx = 0;
	
		while(charIdx < length) {
			currentChar = doc.charAt(charIdx);

			if(isWhitespace(currentChar)) {
				currentChar = ' ';
				// skip all whitespace chars
				while(charIdx < length - 1) {
					if(!isWhitespace(doc.charAt(charIdx + 1)))
						break;
					charIdx++;
				}

			} else {
				currentChar = caseSensitive? currentChar : Character.toLowerCase(currentChar);
			}

			

			int nextState = walkFSA.next(currentState, currentChar);

			if(nextState == 0) {
				//the matching stopped
				//if we had a successful match then act on it;
				if(lastMatchingState != 0) {
					createLookup(lastMatchingState, doc, matchedRegionStart, matchedRegionEnd);
					lastMatchingState = 0;
				}
				//reset the FSM (обходим каждую позицию т.е. сначала с 0, потом с 1, потом с 2)
				charIdx = matchedRegionStart + 1;
				matchedRegionStart = charIdx;

				currentState = 0;
			} else { // go on with the matching
				currentState = nextState;
				//if we have a successful state then store it
				int[] finals = walkFSA.getFinals(currentState);
				if(finals != null && finals.length != 0 && 
						(
								(!wholeWordsOnly)
								||
								((matchedRegionStart == 0 || !isWordInternal(doc.charAt(matchedRegionStart - 1)))
										&&
										(charIdx + 1 >= doc.length()   ||
										!isWordInternal(doc.charAt(charIdx + 1)))
										)
								)
						) {
					//we have a new match
					//if we had an existing match and we need to annotate prefixes, then 
					//apply it
					if(!longestMatchOnly && lastMatchingState != 0) {
						createLookup(lastMatchingState, doc, matchedRegionStart, matchedRegionEnd);
					}
					matchedRegionEnd = charIdx;
					lastMatchingState = currentState;
				}
				charIdx++;

				if(charIdx == doc.length()){
					//we can't go on, use the last matching state and restart matching
					//from the next char
					if(lastMatchingState != 0) {
						//let's add the new annotation(s)
						createLookup(lastMatchingState, doc, matchedRegionStart, 
								matchedRegionEnd);
						lastMatchingState = 0;
					}
					//reset the FSM
					charIdx = matchedRegionStart + 1;
					matchedRegionStart = charIdx;
					currentState = 0;
				}

			}
		} // while(charIdx < length)
		//we've finished. If we had a stored match, then apply it.
		if(lastMatchingState != 0) {
			createLookup(lastMatchingState, doc, matchedRegionStart, matchedRegionEnd);
		}
	}
	
	  /**
	   * Tests whether a character is internal to a word (i.e. if it's a letter or
	   * a combining mark (spacing or not)).
	   * @param ch the character to be tested
	   * @return a boolean value
	   */
	  public static boolean isWordInternal(char ch){
	    return Character.isLetter(ch) ||
	           Character.getType(ch) == Character.COMBINING_SPACING_MARK ||
	           Character.getType(ch) == Character.NON_SPACING_MARK;
	  }


}
