package name.kazennikov.tokens;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import name.kazennikov.annotations.BaseTokenType;
import name.kazennikov.annotations.NLPTokenType;
@Deprecated
public class SentenceSplitter {
		Set<String> abbrev;
		boolean splitOnLower;
		
		public SentenceSplitter(Set<String> abbrev) {
			this.abbrev = abbrev;
		}
		
		public Set<String> getAbbrev() {
			return abbrev;
		}

		public void addAbbrev(String abbrev) {
			this.abbrev.add(abbrev);
		}

		/**
		 * Split given list of tokens into list of sentences
		 * @param tokens
		 * @return list of sentences, contains at least 1 sentence if tokens list isn't empty
		 */
		public List<AbstractToken> split(List<AbstractToken> tokens) {
			return split(new TokenStream(tokens));
		}
		
		
		public List<AbstractToken> split(TokenStream in) {
			List<AbstractToken> tokens = new ArrayList<AbstractToken>();
			
			while(in.isNull() && in.current().is(BaseTokenType.WHITESPACE))
				in.next();

			int sentenceStart = in.pos();


			while(!in.isEmpty()) {
				if(isSentenceEnd(in, splitOnLower)) {
					tokens.add(in.getSequence(sentenceStart, in.pos(), NLPTokenType.SENTENCE).trim());
					
					while(in.isNull() && in.current().is(BaseTokenType.WHITESPACE))
						in.next();
					
					sentenceStart = in.pos();
				} else {
					in.next();
				}
			}
			
			if(sentenceStart < in.pos()) {
				tokens.add(in.getSequence(sentenceStart, in.size(), NLPTokenType.SENTENCE));
			}
			return tokens;
		}

		/**
		 * Check if the token could be a possible EOS
		 */
		public static boolean isPossibleEOS(AbstractToken token) {
			if(!token.is(BaseTokenType.PUNC))
				return false;
			
			String value = token.text();
			
			if(value.length() == 1 && (value.equals("!") || value.equals("?") || value.equals(".")))
				return true;
			
			return value.contains(".");
		}
		
		/**
		 * Check if current token is a sentence end token. Also, if the current token is 
		 * the end of sentence, then advance to the logical end of the sentence.
		 * The method also checks for abbreviations that couldn't mean EOS
		 * @param s token stream
		 * @param splitOnLower if true, sentence ends on punctuation mark even if the start of the
		 * next sentence in in lowercase
		 * @return true if current tokens end a sentence
		 */
		public boolean isSentenceEnd(TokenStream s, boolean splitOnLower) {
			if(!isPossibleEOS(s.current()))
				return false;
			
			AbstractToken prev = s.current(-1);
			AbstractToken next = s.current(1);
			
			if(next == TextToken.NULL) {
				s.next();
				return true;
			}
			
			// skip all punctuation after current position
			while(s.current().is(BaseTokenType.PUNC))
				s.next();
			
			if(!s.current().is(BaseTokenType.WHITESPACE))
				return false;
			
			int sentStart = s.pos();
			// skip all non-text after the punctuation
			while(s.get(sentStart) != TextToken.NULL && !s.get(sentStart).is(BaseTokenType.TEXT)) {
				sentStart++;
			}
			
			if(s.get(sentStart) == TextToken.NULL) {
				s.setPos(sentStart);
				return true;
			}
			
			if(!Character.isLowerCase(s.get(sentStart).text().charAt(0)) || splitOnLower) {
				if(isAbbrev(prev) || isInitial(prev))
					return false;
				return true;
			}
			
			
			
			
			return false;
			
		}

		/**
		 * Checks if the token could be an initial like A. A. Petrov
		 * @param token token to check
		 */
		private boolean isInitial(AbstractToken token) {
			String value = token.text();
			if(value.isEmpty())
				return false;
			
			return value.length() < 3 && Character.isUpperCase(value.charAt(0));
		}

		private boolean isAbbrev(AbstractToken prev) {
			return abbrev.contains(prev.text());
		}
		
		/**
		 * Load abbreviations from ETAP-3 definition file
		 * @param fileName file name 
		 * @return set of abbreviations
		 * @throws IOException
		 */
		public static Set<String> loadAbbrev(String fileName) throws IOException {
			HashSet<String> abbrev = new HashSet<String>();
			BufferedReader r = null;
			try {
				r = new BufferedReader(new FileReader(fileName));
				String s;
				do {
					s = r.readLine();
					abbrev.add(s.trim());
				} while(s != null);
			} finally {
				if(r != null)
					r.close();
			}
			
			return abbrev;
			
		}
		
		public static void main(String[] args) {
			BaseToken ts = SimpleTokenizer.tokenize("Мама мыла раму. Это 2.5 предложения.", NLPTokenType.SENTENCE);
			SentenceSplitter ss = new SentenceSplitter(new HashSet<String>());
			
			List<AbstractToken> ts1 = ss.split(new TokenStream(ts.childs()));
			
			System.out.printf("Split size: %d%n", ts1.size());
			
		}


}
