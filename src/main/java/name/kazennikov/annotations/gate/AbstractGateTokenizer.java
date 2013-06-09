package name.kazennikov.annotations.gate;

import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.BaseTokenType;
import name.kazennikov.annotations.TokenType;
import gate.AnnotationSet;
import gate.FeatureMap;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.util.InvalidOffsetException;

public abstract class AbstractGateTokenizer extends AbstractLanguageAnalyser {
	
	public static final String DIGITS = "digits";
	public static final String LETTERS = "letters";
	public static final String ALPHANUM = "alphanum";
	public static final String MISC = "misc";
	public static final String SPACE = "space";
	public static final String PUNC = "punc";


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
    public void execute() throws ExecutionException {

        String text = document.getContent().toString();
        
        AnnotationSet as = document.getAnnotations();
        int pos = 0;
        int start = 0;
        
        try {

        while(pos < text.length()) {
            char ch = text.charAt(pos);
            boolean isSep = isSeparator(ch);
            boolean isWS = isWhitespace(ch);

            if(isSep || isWS) {
                // add char sequence between separators
                if(start != pos) {
                    add(start, pos, infer(text, start, pos));
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
                    add(tStart, pos, isNewline ? BaseTokenType.NEWLINE : BaseTokenType.SPACE);
                } else {
                    int puncStart = pos;
                    pos++;
                    add(puncStart, pos, BaseTokenType.PUNC);
                }
                start = pos;
                continue;
            }

            pos++;
        }
        if(start != pos) {
            add(start, pos, infer(text, start, pos));
        }
        
        } catch(InvalidOffsetException e) {
        	throw new ExecutionException(e);
        }
    }

    void add(int start, int end, TokenType type) throws InvalidOffsetException {
    	AnnotationSet as = document.getAnnotations();
    	FeatureMap fm = gate.Factory.newFeatureMap();
    	fm.put(Annotation.TYPE, type);
    	
    	as.add((long) start, (long) end, Annotation.TOKEN, fm);
    }
	

}
