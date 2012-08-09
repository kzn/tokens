package name.kazennikov.annotations;

import name.kazennikov.tokens.BaseTokenType;
import name.kazennikov.tokens.TokenType;

/**
 * Tokenizer that splits input string into 3 types of tokens:
 * <ul>
 *     <li>Whitespace. It captures all whitespace chars into single tokens</li>
 *     <li>Punctuation. Emit a token for each punctuation char</li>
 *     <li>Tokens. A content token is a sequence of non-separator characters</li>
 * </ul>
 *
 * Optionally, it inferes type of content token. Types could be:
 * <ul>
 *     <li>DIGITS</li>
 *     <li>LETTERS</li>
 *     <li>ALPHANUM</li>
 *     <li>MISC</li>
 * </ul>
 */
public abstract class AbstractTokenizer implements Annotator {

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

    Annotation makeAnnotation(int start, int end, TokenType type) {
        Annotation ann = new Annotation(Annotation.TOKEN, start, end);
        ann.setFeature(Annotation.TYPE, type);

        return ann;
    }

    void add(Document d, int start, int end, TokenType type) {
        d.addAnnotation(makeAnnotation(start, end, type));
    }
}