package name.kazennikov.annotations.gazetteer;

import gnu.trove.list.array.TIntArrayList;
import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.Document;

public class TokenDAFSAGazetteer extends AbstractDAFSAGazetteer {

	@Override
	public void annotate(Document doc) {
		TIntArrayList starts = getStarts(doc);
		TIntArrayList ends = getEnds(doc);
		if(starts.isEmpty())
			return;
		
		int currentState = 0;
		int lastMatchingState = 0;
		int startToken = 0; 
		int currentIndex = starts.get(startToken++);
		int matchStart = currentIndex;
		int matchEnd = 0;



		while(currentIndex < doc.length()) {
			char ch = doc.charAt(currentIndex);

			if(isWhitespace(ch)) {
				ch = ' ';
				// skip all whitespace chars

				while(currentIndex < doc.length() - 1) {
					if(!isWhitespace(doc.charAt(currentIndex + 1)))
						break;
					currentIndex++;
				}
			} else {
				ch = caseSensitive? ch: Character.toLowerCase(ch);
			}
			
			int nextState = walkFSA.next(currentIndex, ch);
			
			if(nextState == 0) {
				//the matching stopped
				//if we had a successful match then act on it;
				if(lastMatchingState != 0) {
					createLookup(lastMatchingState, doc, matchStart, matchEnd);
					lastMatchingState = 0;
				}
				
				//reset the FSM (обходим каждую позицию т.е. сначала с 0, потом с 1, потом с 2)
				currentIndex = starts.get(startToken++);
				matchStart = currentIndex;

				currentState = 0;
			} else { // go on with the matching
				currentState = nextState;
				//if we have a successful state then store it
				int[] finals = walkFSA.getFinals(currentState);
				
				if(finals != null && finals.length != 0 && ends.binarySearch(currentIndex) > 0) {
					//we have a new match
					//if we had an existing match and we need to annotate prefixes, then 
					//apply it
					if(!longestMatchOnly && lastMatchingState != 0) {
						createLookup(lastMatchingState, doc, matchStart, currentIndex);
					}
					
					matchEnd = currentIndex;
					lastMatchingState = currentState;
				}
				currentIndex++;

				if(currentIndex == doc.length()) {
					//we can't go on, use the last matching state and restart matching
					//from the next char
					if(lastMatchingState != 0) {
						//let's add the new annotation(s)
						createLookup(lastMatchingState, doc, matchStart, matchEnd);
						lastMatchingState = 0;
					}
					//reset the FSM
					currentIndex = starts.get(startToken++);
					matchStart = currentIndex;
					currentState = 0;
				}

			}
		}



	}

	TIntArrayList getStarts(Document doc) {
		TIntArrayList t = new TIntArrayList();
		for(Annotation a : doc.get(Annotation.TOKEN)) {
			t.add(a.getStart());
		}

		return t;
	}

	TIntArrayList getEnds(Document doc) {
		TIntArrayList t = new TIntArrayList();
		for(Annotation a : doc.get(Annotation.TOKEN)) {
			t.add(a.getEnd());
		}

		return t;
	}


}
