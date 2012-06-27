package name.kazennikov.tokens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class TokenStream extends SequenceStream<AbstractToken> {
		public TokenStream(List<AbstractToken> tokens) {
			super(TextToken.NULL, tokens);
		}
		
		/**
		 * Get several tokens from the stream as a single sequence token
		 * @param type type of the result token
		 * @param s source token stream
		 * @param start start index
		 * @param end end index
		 * @return
		 */
		public BaseToken getSequence(int start, int end, TokenType type) {
			ArrayList<AbstractToken> tokens = new ArrayList<AbstractToken>(end - start);
			for(int i = start; i != end; i++) {
				tokens.add(get(i));
			}
			return new BaseToken(tokens, type);
		}
}
