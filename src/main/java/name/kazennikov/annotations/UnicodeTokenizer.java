package name.kazennikov.annotations;

import com.google.common.base.CharMatcher;
import name.kazennikov.tokens.TokenType;


public class UnicodeTokenizer extends AbstractTokenizer {


    public UnicodeTokenizer() {
    }

    @Override
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
}