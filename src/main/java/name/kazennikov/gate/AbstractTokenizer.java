package name.kazennikov.gate;

import java.io.File;
import java.net.MalformedURLException;

import name.kazennikov.tokens.BaseTokenType;
import name.kazennikov.tokens.TokenType;
import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.DocumentContent;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Resource;
import gate.corpora.CorpusImpl;
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;
import gate.util.Benchmark;
import gate.util.GateException;
import gate.util.InvalidOffsetException;

public class AbstractTokenizer extends AbstractLanguageAnalyser {
	
	
	
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
    public boolean isSeparator(char ch) {
        int type = Character.getType(ch);

        if(type == Character.CONNECTOR_PUNCTUATION ||
                type == Character.DASH_PUNCTUATION ||
                type == Character.START_PUNCTUATION ||
                type == Character.END_PUNCTUATION ||
                type == Character.INITIAL_QUOTE_PUNCTUATION ||
                type == Character.FINAL_QUOTE_PUNCTUATION ||
                type == Character.OTHER_PUNCTUATION ||
                type == Character.MATH_SYMBOL) {
            return true;
        }


        return false;

    }
    
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
	public Resource init() throws ResourceInstantiationException {
		return super.init();
	}

	@Override
	public void execute() throws ExecutionException {
		String text = document.getContent().toString();
		AnnotationSet annotationSet = document.getAnnotations();
		

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
                    add(annotationSet, start, pos, infer(text, start, pos));
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
                    add(annotationSet, tStart, pos, isNewline ? BaseTokenType.NEWLINE : BaseTokenType.SPACE);
                } else {
                    int puncStart = pos;
                    pos++;
                    add(annotationSet, puncStart, pos, BaseTokenType.PUNC);
                }
                start = pos;
                continue;
            }

            pos++;
        }
        if(start != pos) {
            add(annotationSet, start, pos, infer(text, start, pos));
        }
        } catch(InvalidOffsetException e) {
        	throw new ExecutionException(e);
        }
    }

    void add(AnnotationSet annotationSet, int start, int end, TokenType type) throws InvalidOffsetException {
    	FeatureMap featureMap = Factory.newFeatureMap();
    	
    	featureMap.put("type", type);
    	annotationSet.add((long)start, (long)end, TOKEN_ANNOTATION_TYPE, featureMap);
    }
		

	@Override
	public String getName() {
		return "UnicodeTokenizer";
	}
	
	public static void main(String[] args) throws GateException, MalformedURLException {
		Gate.init();
		Gate.getCreoleRegister().addDirectory(new File("gate").toURL());
		
		SerialAnalyserController pipe = new SerialAnalyserController();
		AbstractTokenizer tokenizer = new AbstractTokenizer();
		pipe.add(tokenizer);
		pipe.setBenchmarkId("tokenizer");
		Benchmark.setBenchmarkingEnabled(false);
		
		String text = "Мама мыла раму";
		
		Corpus c = Factory.newCorpus("foo");
		
		Document doc = Factory.newDocument(text);
		c.add(doc);
		pipe.setCorpus(c);
		pipe.execute();
		//tokenizer.setDocument(doc);
		//tokenizer.execute();
		
		
		
		
		
	}
	

}
