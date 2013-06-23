package name.kazennikov.annotations;

import java.util.HashMap;
import java.util.Map;


/**
 * Tokenizer that splits input string into tokens.
 * <p>
 * A token is a continuous sequence of characters that is delimited by text start/end or separator characters.
 * A sequence of repeating separator characters is one token.
 * <p>
 * Technically it adds 'Token' annotations to the document. This annotations
 * has a 'type' feature which designates the token type.
 * <p>
 * Predefined token types:
 * <ul>
 *     <li>Whitespace. It captures all whitespace chars into single tokens</li>
 *     <li>Punctuation. Emit a token for each punctuation char</li>
 *     <li>Tokens. A content token is a sequence of non-separator characters</li>
 * </ul>
 *
 * Also, it infers type of content token. Predefined types are:
 * <ul>
 *     <li>DIGITS - for digit only char sequences
 *     <li>LETTERS - for letter only char sequences
 *     <li>ALPHANUM - for alphanumeric char sequences
 *     <li>PUNC - punctuation
 *     <li>MISC - for other char sequences
 * </ul>
 */
public abstract class AbstractTokenizer implements Annotator {
	public static final String DIGITS = "digits";
	public static final String LETTERS = "letters";
	public static final String ALPHANUM = "alphanum";
	public static final String MISC = "misc";
	public static final String SPACE = "space";
	public static final String PUNC = "punc";

    public AbstractTokenizer() {
    }

    /**
     * Checks if a char is a white space
     */
    public boolean isWhitespace(char ch) {
        return Character.isSpaceChar(ch) || Character.isWhitespace(ch);
    }

    /**
     * Return true if char is a separator
     *
     * @param ch
     * @return
     */
    public abstract boolean isSeparator(char ch);

    /**
     * Infer type for non-punctuation token
     *
     * @param text  text that contains token
     * @param start token start
     * @param end   token end
     * @return inferred type
     */
    public TokenType infer(String text, int start, int end) {
        boolean hasDigits = false;
        boolean hasLetters = false;

        while(start < end) {
            char ch = text.charAt(start);
            if(Character.isLetter(ch)) {
                hasLetters = true;
            } else if(Character.isDigit(ch)) {
                hasDigits = true;
            } else {
                return BaseTokenType.MISC;
            }
            start++;
        }

        if(hasDigits && hasLetters) {
            return BaseTokenType.ALPHANUM;
        }

        if(hasDigits) {
            return BaseTokenType.DIGITS;
        }

        return BaseTokenType.LETTERS; // last possible choice
    }

    @Override
    public boolean isApplicable(Document doc) {
        return true;
    }

    @Override
    public void annotate(Document doc) {

        String text = doc.getText();
        int pos = 0;
        int start = 0;

        while(pos < text.length()) {
            char ch = text.charAt(pos);
            boolean isSep = isSeparator(ch);
            boolean isWS = isWhitespace(ch);

            if(isSep || isWS) {
                // add char sequence between separators
                if(start != pos) {
                    add(doc, start, pos, infer(text, start, pos));
                }

                int tStart = pos;
                if(isWS) {
                    boolean isNewline = (ch == '\n' || ch == '\r');

                    while(pos < text.length()) {
                        ch = text.charAt(pos);
                        isNewline = isNewline || ch == '\n' || ch == '\r';
                        if(!isWhitespace(ch)) {
                            break;
                        }
                        pos++;
                    }
                    add(doc, tStart, pos, isNewline ? BaseTokenType.NEWLINE : BaseTokenType.SPACE);
                } else {
                    int puncStart = pos;
                    pos++;
                    add(doc, puncStart, pos, BaseTokenType.PUNC);
                }
                start = pos;
                continue;
            }

            pos++;
        }
        if(start != pos) {
            add(doc, start, pos, infer(text, start, pos));
        }
    }

    void add(Document d, int start, int end, TokenType type) {
    	Map<String, Object> feats = new HashMap<String, Object>();
    	feats.put(Annotation.TYPE, type);
        d.addAnnotation(Annotation.TOKEN, start, end, feats);
    }
}