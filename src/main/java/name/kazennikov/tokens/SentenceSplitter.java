package name.kazennikov.tokens;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		
		
		public List<BaseToken> split(TokenStream in) {
			List<BaseToken> tokens = new ArrayList<BaseToken>();
			int sentenceStart = 0;

			while(!in.isEmpty()) {
				if(isSentenceEnd(in, splitOnLower)) {
					tokens.add(in.getSequence(sentenceStart, in.pos(), NLPTokenType.SENTENCE).trim());
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
		
		public static boolean isPossibleEOS(AbstractToken token) {
			if(!token.is(BaseTokenType.PUNC))
				return false;
			
			String value = token.text();
			
			if(value.length() == 1 && (value == "!" || value == "?" || value == "."))
				return true;
			
			return value.contains(".");
		}
		
		/**
		 * Check if current token is a sentence end token
		 * @param s
		 * @return
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
			
			// skip all punctuation
			while(s.current().is(BaseTokenType.PUNC))
				s.next();
			
			if(!s.current().is(BaseTokenType.SPACE))
				return false;
			
			while(s.current() != TextToken.NULL && !s.current().is(BaseTokenType.TEXT))
				s.next();
			
			if(s.current() == TextToken.NULL)
				return true;
			
			if(!Character.isLowerCase(s.current().text().charAt(0)) || splitOnLower) {
				if(isAbbrev(prev) || isInitial(prev))
					return false;
				return true;
			}
			
			
			
			
			return false;
			
		}

		private boolean isInitial(AbstractToken prev) {
			String value = prev.text();
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
			
			List<BaseToken> ts1 = ss.split(new TokenStream(ts.childs()));
			
			System.out.printf("Split size: %d%n", ts1.size());
			
		}


}