package name.kazennikov.annotations;

import gnu.trove.list.array.TIntArrayList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import name.kazennikov.trove.TPositionalCharIterator;

import com.google.common.base.Predicate;

/**
 * Token stream that acts like a positional character iterator
 * 
 * @author Anton Kazennikov
 *
 */
public class AnnotationTokenStream implements TPositionalCharIterator {

    Annotation text;
    String upper;
    String lower;
    List<Annotation> tokens;
    TIntArrayList tokenEnds;
    boolean prevIsSpace;
    int pos;

    /**
     * Construct new token stream
     * 
     * @param text bounds for this token stream
     * @param tokenType embedded token type
     */
    public AnnotationTokenStream(Annotation text, final String tokenType) {
        this.text = text;
        this.lower = text.getText().toLowerCase();
        this.upper = text.getText().toUpperCase();
        this.tokens = text.getDoc().getAnnotationsWithin(text, new Predicate<Annotation>() {
            @Override
            public boolean apply(@Nullable Annotation annotation) {
                return annotation.getType().equals(tokenType);
            }
        });

        tokenEnds = new TIntArrayList(tokens.size());

        for(Annotation tok : tokens) {
            tokenEnds.add(tok.getEnd());
        }
    }

    /**
     * Get substring from the char range [start, end)
     * 
     * @param start 
     * @param end
     * 
     * @return
     */
    public String substring(int start, int end) {
        return text.getText().substring(start, end);
    }
    
    /**
     * Checks if given position is token end
     * @param pos position to check
     * @return
     */
    public boolean isTokenEnd(int pos) {
        return tokenEnds.contains(pos);
    }

    /**
     * Get number of tokens in the stream
     * 
     * @return
     */
    public int tokenCount() {
        return tokens.size();
    }
    
    /**
     * Get current character position
     * 
     * @return
     */
    public int pos() {
    	return pos;
    }

    /**
     * Get character at current offset
     * 
     * @return
     */
    public char current() {
        if(pos >= text.length())
            return 0;
        return lower.charAt(pos);
    }

    @Override
    public char next() {
        char ch = lower.charAt(pos++);
        if(isWhiteSpace(ch)) {
        	ch = ' ';
            while(pos < text.length() && isWhiteSpace(text.charAt(pos))) {
                pos++;
            }
        }

        if(ch == '-') {
            ch = '-';
        }
        return ch;
    }
    
    /**
     * Check if given character is whitespace
     * 
     * @param ch
     * 
     * @return
     */
    public static boolean isWhiteSpace(char ch) {
    	return Character.isWhitespace(ch) || Character.isSpaceChar(ch);
    }

    /**
     * Peek next char (get it without moving the cursor)
     * 
     * @return peeked char of 0 if out of bounds
     */
    public char peek() {
        if(pos + 1 > text.length())
            return 0;

        int pos = this.pos;
        char ch = lower.charAt(pos++);
        if(isWhiteSpace(ch)) {
        	ch = ' ';
            while(pos < text.length() && isWhiteSpace(text.charAt(pos))) {
                pos++;
            }
        }
        return ch;
    }

    /**
     * Find the position of next non-whitespace character
     * 
     * @param pos start position
     * @return next position or -1 if out of bounds
     */
    public int nextPos(int pos) {
        if(pos + 1 >= text.length())
            return -1;

        pos++;
        while(pos < text.length() && isWhiteSpace(text.charAt(pos))) {
            pos++;
        }

        return pos;
    }

    @Override
    public boolean hasNext() {
        return pos < text.length();
    }

    @Override
    public void remove() {

    }
    
    @Override
	public void setPos(int pos) {
    	this.pos = pos;
    }
    
    @Override
	public int getPos() {
    	return pos();
    }
    
    /**
     * Get whole text of the stream
     * @return
     */
    public String getText() {
    	return text.getText();
    }

    public static void main(String[] args) {
        Document d = new Document("sent", "this   is  ");

        AnnotationTokenStream ts = new AnnotationTokenStream(d, "Token");

        while(ts.hasNext()) {
            System.out.println(ts.next());
        }
    }


    /**
     * Get token that corresponds given character offset
     * 
     * @param pos char offset
     * 
     * @return corresponding token
     */
    public Annotation at(int pos) {
		int p = tokenEnds.binarySearch(pos);
		return p < 0? tokens.get(-p - 1) : tokens.get(p + 1);
    }

    /**
     * Get token
     * @param index token index
     * @return
     */
    public Annotation getToken(int index) {
        return tokens.get(index);
    }

    public int getTokenIndex(int pos) {
        int p = tokenEnds.binarySearch(pos);
        return p < 0? (-p - 1) : (p + 1);

    }

    /**
     * Skip current token (get offset of the next token)
     * @param pos
     * @return
     */
	public int skipToken(int pos) {
		int p = tokenEnds.binarySearch(pos);
		
		p = p < 0? -p - 1 : p + 1;
		p++;
		
		return p < tokens.size()? tokens.get(p).getStart() : text.length();
	}
	
	/**
	 * Matches regex against given position.
	 * @param pos position
	 * @param regex regex to match
	 * @return end of the matched group or current position if no match found
	 */
	public int matchRegex(int pos, Pattern regex) {
		String s = getText().substring(pos);
		Matcher m = regex.matcher(s);
		return m.lookingAt()? pos + m.end() : pos;
	}

}
