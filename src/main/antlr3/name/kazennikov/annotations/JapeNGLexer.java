// $ANTLR 3.4 /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g 2013-06-26 16:56:29

  package name.kazennikov.annotations;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class JapeNGLexer extends Lexer {
    public static final int EOF=-1;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__19=19;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int COMMENT=4;
    public static final int INPUT=5;
    public static final int OPTION=6;
    public static final int OPTIONS=7;
    public static final int PHASE=8;
    public static final int SIMPLE=9;
    public static final int SINGLE_COMMENT=10;
    public static final int STRING=11;
    public static final int WS=12;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public JapeNGLexer() {} 
    public JapeNGLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public JapeNGLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g"; }

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:11:7: ( '!=' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:11:9: '!='
            {
            match("!="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:12:7: ( '(' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:12:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:13:7: ( ')' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:13:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:14:7: ( '*' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:14:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:15:7: ( '+' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:15:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:16:7: ( ',' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:16:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:17:7: ( '.' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:17:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:18:7: ( ':' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:18:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:19:7: ( '=' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:19:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:20:7: ( '==' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:20:9: '=='
            {
            match("=="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:21:7: ( '?' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:21:9: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:22:7: ( 'Input:' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:22:9: 'Input:'
            {
            match("Input:"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:23:7: ( 'Options:' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:23:9: 'Options:'
            {
            match("Options:"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:24:7: ( 'Phase:' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:24:9: 'Phase:'
            {
            match("Phase:"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:25:7: ( 'Rule:' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:25:9: 'Rule:'
            {
            match("Rule:"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:26:7: ( '{' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:26:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:27:7: ( '}' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:27:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:38:3: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:38:5: ( ' ' | '\\t' | '\\n' | '\\r' )+
            {
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:38:5: ( ' ' | '\\t' | '\\n' | '\\r' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '\t' && LA1_0 <= '\n')||LA1_0=='\r'||LA1_0==' ') ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:
            	    {
            	    if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


             _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "SINGLE_COMMENT"
    public final void mSINGLE_COMMENT() throws RecognitionException {
        try {
            int _type = SINGLE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:39:15: ( '//' (~ ( '\\r' | '\\n' ) )* )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:39:17: '//' (~ ( '\\r' | '\\n' ) )*
            {
            match("//"); 



            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:39:22: (~ ( '\\r' | '\\n' ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '\u0000' && LA2_0 <= '\t')||(LA2_0 >= '\u000B' && LA2_0 <= '\f')||(LA2_0 >= '\u000E' && LA2_0 <= '\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SINGLE_COMMENT"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:40:8: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:40:12: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 



            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:40:17: ( options {greedy=false; } : . )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='*') ) {
                    int LA3_1 = input.LA(2);

                    if ( (LA3_1=='/') ) {
                        alt3=2;
                    }
                    else if ( ((LA3_1 >= '\u0000' && LA3_1 <= '.')||(LA3_1 >= '0' && LA3_1 <= '\uFFFF')) ) {
                        alt3=1;
                    }


                }
                else if ( ((LA3_0 >= '\u0000' && LA3_0 <= ')')||(LA3_0 >= '+' && LA3_0 <= '\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:40:45: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            match("*/"); 



            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:42:8: ( '\"' (~ ( '\"' | '\\\\' ) | '\\\\' . )* '\"' )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:42:10: '\"' (~ ( '\"' | '\\\\' ) | '\\\\' . )* '\"'
            {
            match('\"'); 

            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:42:14: (~ ( '\"' | '\\\\' ) | '\\\\' . )*
            loop4:
            do {
                int alt4=3;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0 >= '\u0000' && LA4_0 <= '!')||(LA4_0 >= '#' && LA4_0 <= '[')||(LA4_0 >= ']' && LA4_0 <= '\uFFFF')) ) {
                    alt4=1;
                }
                else if ( (LA4_0=='\\') ) {
                    alt4=2;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:42:15: ~ ( '\"' | '\\\\' )
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;
            	case 2 :
            	    // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:42:31: '\\\\' .
            	    {
            	    match('\\'); 

            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "SIMPLE"
    public final void mSIMPLE() throws RecognitionException {
        try {
            int _type = SIMPLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:43:7: ( (~ ( '(' | ')' | ' ' | ',' | '<' | '>' | '\\t' | '\\r' | '\\n' | '{' | '}' | '[' | ']' ) )+ )
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:43:9: (~ ( '(' | ')' | ' ' | ',' | '<' | '>' | '\\t' | '\\r' | '\\n' | '{' | '}' | '[' | ']' ) )+
            {
            // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:43:9: (~ ( '(' | ')' | ' ' | ',' | '<' | '>' | '\\t' | '\\r' | '\\n' | '{' | '}' | '[' | ']' ) )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0 >= '\u0000' && LA5_0 <= '\b')||(LA5_0 >= '\u000B' && LA5_0 <= '\f')||(LA5_0 >= '\u000E' && LA5_0 <= '\u001F')||(LA5_0 >= '!' && LA5_0 <= '\'')||(LA5_0 >= '*' && LA5_0 <= '+')||(LA5_0 >= '-' && LA5_0 <= ';')||LA5_0=='='||(LA5_0 >= '?' && LA5_0 <= 'Z')||LA5_0=='\\'||(LA5_0 >= '^' && LA5_0 <= 'z')||LA5_0=='|'||(LA5_0 >= '~' && LA5_0 <= '\uFFFF')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\b')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\u001F')||(input.LA(1) >= '!' && input.LA(1) <= '\'')||(input.LA(1) >= '*' && input.LA(1) <= '+')||(input.LA(1) >= '-' && input.LA(1) <= ';')||input.LA(1)=='='||(input.LA(1) >= '?' && input.LA(1) <= 'Z')||input.LA(1)=='\\'||(input.LA(1) >= '^' && input.LA(1) <= 'z')||input.LA(1)=='|'||(input.LA(1) >= '~' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SIMPLE"

    public void mTokens() throws RecognitionException {
        // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:8: ( T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | WS | SINGLE_COMMENT | COMMENT | STRING | SIMPLE )
        int alt6=22;
        alt6 = dfa6.predict(input);
        switch (alt6) {
            case 1 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:10: T__13
                {
                mT__13(); 


                }
                break;
            case 2 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:16: T__14
                {
                mT__14(); 


                }
                break;
            case 3 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:22: T__15
                {
                mT__15(); 


                }
                break;
            case 4 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:28: T__16
                {
                mT__16(); 


                }
                break;
            case 5 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:34: T__17
                {
                mT__17(); 


                }
                break;
            case 6 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:40: T__18
                {
                mT__18(); 


                }
                break;
            case 7 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:46: T__19
                {
                mT__19(); 


                }
                break;
            case 8 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:52: T__20
                {
                mT__20(); 


                }
                break;
            case 9 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:58: T__21
                {
                mT__21(); 


                }
                break;
            case 10 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:64: T__22
                {
                mT__22(); 


                }
                break;
            case 11 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:70: T__23
                {
                mT__23(); 


                }
                break;
            case 12 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:76: T__24
                {
                mT__24(); 


                }
                break;
            case 13 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:82: T__25
                {
                mT__25(); 


                }
                break;
            case 14 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:88: T__26
                {
                mT__26(); 


                }
                break;
            case 15 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:94: T__27
                {
                mT__27(); 


                }
                break;
            case 16 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:100: T__28
                {
                mT__28(); 


                }
                break;
            case 17 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:106: T__29
                {
                mT__29(); 


                }
                break;
            case 18 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:112: WS
                {
                mWS(); 


                }
                break;
            case 19 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:115: SINGLE_COMMENT
                {
                mSINGLE_COMMENT(); 


                }
                break;
            case 20 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:130: COMMENT
                {
                mCOMMENT(); 


                }
                break;
            case 21 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:138: STRING
                {
                mSTRING(); 


                }
                break;
            case 22 :
                // /home/kzn/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:1:145: SIMPLE
                {
                mSIMPLE(); 


                }
                break;

        }

    }


    protected DFA6 dfa6 = new DFA6(this);
    static final String DFA6_eotS =
        "\1\uffff\1\24\2\uffff\1\26\1\27\1\uffff\1\30\1\31\1\33\1\34\4\24"+
        "\3\uffff\2\24\1\uffff\1\47\4\uffff\1\50\2\uffff\4\24\1\56\3\24\1"+
        "\46\3\uffff\4\24\1\56\1\uffff\2\24\1\uffff\5\24\1\61\3\24\1\77\1"+
        "\100\1\24\1\102\2\uffff\1\24\1\uffff\1\104\1\uffff";
    static final String DFA6_eofS =
        "\105\uffff";
    static final String DFA6_minS =
        "\1\0\1\75\2\uffff\2\0\1\uffff\4\0\1\156\1\160\1\150\1\165\3\uffff"+
        "\1\52\1\0\1\uffff\1\0\4\uffff\1\0\2\uffff\1\160\1\164\1\141\1\154"+
        "\5\0\3\uffff\1\165\1\151\1\163\1\145\1\0\1\uffff\2\0\1\uffff\1\0"+
        "\1\164\1\157\1\145\1\72\1\0\1\72\1\156\1\72\2\0\1\163\1\0\2\uffff"+
        "\1\72\1\uffff\1\0\1\uffff";
    static final String DFA6_maxS =
        "\1\uffff\1\75\2\uffff\2\uffff\1\uffff\4\uffff\1\156\1\160\1\150"+
        "\1\165\3\uffff\1\57\1\uffff\1\uffff\1\uffff\4\uffff\1\uffff\2\uffff"+
        "\1\160\1\164\1\141\1\154\5\uffff\3\uffff\1\165\1\151\1\163\1\145"+
        "\1\uffff\1\uffff\2\uffff\1\uffff\1\uffff\1\164\1\157\1\145\1\72"+
        "\1\uffff\1\72\1\156\1\72\2\uffff\1\163\1\uffff\2\uffff\1\72\1\uffff"+
        "\1\uffff\1\uffff";
    static final String DFA6_acceptS =
        "\2\uffff\1\2\1\3\2\uffff\1\6\10\uffff\1\20\1\21\1\22\2\uffff\1\26"+
        "\1\uffff\1\4\1\5\1\7\1\10\1\uffff\1\11\1\13\11\uffff\1\25\1\1\1"+
        "\12\5\uffff\1\23\2\uffff\1\24\15\uffff\1\17\1\14\1\uffff\1\16\1"+
        "\uffff\1\15";
    static final String DFA6_specialS =
        "\1\20\3\uffff\1\12\1\14\1\uffff\1\5\1\1\1\16\1\4\10\uffff\1\24\1"+
        "\uffff\1\25\4\uffff\1\6\6\uffff\1\26\1\2\1\3\1\15\1\21\7\uffff\1"+
        "\7\1\uffff\1\27\1\11\1\uffff\1\0\4\uffff\1\22\3\uffff\1\17\1\13"+
        "\1\uffff\1\23\4\uffff\1\10\1\uffff}>";
    static final String[] DFA6_transitionS = {
            "\11\24\2\21\2\24\1\21\22\24\1\21\1\1\1\23\5\24\1\2\1\3\1\4\1"+
            "\5\1\6\1\24\1\7\1\22\12\24\1\10\1\24\1\uffff\1\11\1\uffff\1"+
            "\12\11\24\1\13\5\24\1\14\1\15\1\24\1\16\10\24\1\uffff\1\24\1"+
            "\uffff\35\24\1\17\1\24\1\20\uff82\24",
            "\1\25",
            "",
            "",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\24\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\24\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            "",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\24\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\24\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\32\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\24\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            "\1\35",
            "\1\36",
            "\1\37",
            "\1\40",
            "",
            "",
            "",
            "\1\42\4\uffff\1\41",
            "\11\43\2\46\2\43\1\46\22\43\1\46\1\43\1\45\5\43\2\46\2\43\1"+
            "\46\17\43\1\46\1\43\1\46\34\43\1\46\1\44\1\46\35\43\1\46\1\43"+
            "\1\46\uff82\43",
            "",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\24\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            "",
            "",
            "",
            "",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\24\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            "",
            "",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\54",
            "\11\55\2\uffff\2\55\1\uffff\22\55\1\uffff\7\55\2\uffff\2\55"+
            "\1\uffff\17\55\1\uffff\1\55\1\uffff\34\55\1\uffff\1\55\1\uffff"+
            "\35\55\1\uffff\1\55\1\uffff\uff82\55",
            "\11\60\2\61\2\60\1\61\22\60\1\61\7\60\2\61\1\57\1\60\1\61\17"+
            "\60\1\61\1\60\1\61\34\60\1\61\1\60\1\61\35\60\1\61\1\60\1\61"+
            "\uff82\60",
            "\11\43\2\46\2\43\1\46\22\43\1\46\1\43\1\45\5\43\2\46\2\43\1"+
            "\46\17\43\1\46\1\43\1\46\34\43\1\46\1\44\1\46\35\43\1\46\1\43"+
            "\1\46\uff82\43",
            "\11\62\2\46\2\62\1\46\22\62\1\46\7\62\2\46\2\62\1\46\17\62"+
            "\1\46\1\62\1\46\34\62\1\46\1\62\1\46\35\62\1\46\1\62\1\46\uff82"+
            "\62",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\24\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            "",
            "",
            "",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\11\55\2\uffff\2\55\1\uffff\22\55\1\uffff\7\55\2\uffff\2\55"+
            "\1\uffff\17\55\1\uffff\1\55\1\uffff\34\55\1\uffff\1\55\1\uffff"+
            "\35\55\1\uffff\1\55\1\uffff\uff82\55",
            "",
            "\11\60\2\61\2\60\1\61\22\60\1\61\7\60\2\61\1\57\1\60\1\61\2"+
            "\60\1\67\14\60\1\61\1\60\1\61\34\60\1\61\1\60\1\61\35\60\1\61"+
            "\1\60\1\61\uff82\60",
            "\11\60\2\61\2\60\1\61\22\60\1\61\7\60\2\61\1\57\1\60\1\61\17"+
            "\60\1\61\1\60\1\61\34\60\1\61\1\60\1\61\35\60\1\61\1\60\1\61"+
            "\uff82\60",
            "",
            "\11\43\2\46\2\43\1\46\22\43\1\46\1\43\1\45\5\43\2\46\2\43\1"+
            "\46\17\43\1\46\1\43\1\46\34\43\1\46\1\44\1\46\35\43\1\46\1\43"+
            "\1\46\uff82\43",
            "\1\70",
            "\1\71",
            "\1\72",
            "\1\73",
            "\11\60\2\uffff\2\60\1\uffff\22\60\1\uffff\7\60\2\uffff\1\57"+
            "\1\60\1\uffff\17\60\1\uffff\1\60\1\uffff\34\60\1\uffff\1\60"+
            "\1\uffff\35\60\1\uffff\1\60\1\uffff\uff82\60",
            "\1\74",
            "\1\75",
            "\1\76",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\24\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\24\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            "\1\101",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\24\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            "",
            "",
            "\1\103",
            "",
            "\11\24\2\uffff\2\24\1\uffff\22\24\1\uffff\7\24\2\uffff\2\24"+
            "\1\uffff\17\24\1\uffff\1\24\1\uffff\34\24\1\uffff\1\24\1\uffff"+
            "\35\24\1\uffff\1\24\1\uffff\uff82\24",
            ""
    };

    static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
    static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
    static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
    static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
    static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
    static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
    static final short[][] DFA6_transition;

    static {
        int numStates = DFA6_transitionS.length;
        DFA6_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
        }
    }

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = DFA6_eot;
            this.eof = DFA6_eof;
            this.min = DFA6_min;
            this.max = DFA6_max;
            this.accept = DFA6_accept;
            this.special = DFA6_special;
            this.transition = DFA6_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | WS | SINGLE_COMMENT | COMMENT | STRING | SIMPLE );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA6_50 = input.LA(1);

                        s = -1;
                        if ( (LA6_50=='\"') ) {s = 37;}

                        else if ( ((LA6_50 >= '\u0000' && LA6_50 <= '\b')||(LA6_50 >= '\u000B' && LA6_50 <= '\f')||(LA6_50 >= '\u000E' && LA6_50 <= '\u001F')||LA6_50=='!'||(LA6_50 >= '#' && LA6_50 <= '\'')||(LA6_50 >= '*' && LA6_50 <= '+')||(LA6_50 >= '-' && LA6_50 <= ';')||LA6_50=='='||(LA6_50 >= '?' && LA6_50 <= 'Z')||(LA6_50 >= '^' && LA6_50 <= 'z')||LA6_50=='|'||(LA6_50 >= '~' && LA6_50 <= '\uFFFF')) ) {s = 35;}

                        else if ( (LA6_50=='\\') ) {s = 36;}

                        else if ( ((LA6_50 >= '\t' && LA6_50 <= '\n')||LA6_50=='\r'||LA6_50==' '||(LA6_50 >= '(' && LA6_50 <= ')')||LA6_50==','||LA6_50=='<'||LA6_50=='>'||LA6_50=='['||LA6_50==']'||LA6_50=='{'||LA6_50=='}') ) {s = 38;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA6_8 = input.LA(1);

                        s = -1;
                        if ( ((LA6_8 >= '\u0000' && LA6_8 <= '\b')||(LA6_8 >= '\u000B' && LA6_8 <= '\f')||(LA6_8 >= '\u000E' && LA6_8 <= '\u001F')||(LA6_8 >= '!' && LA6_8 <= '\'')||(LA6_8 >= '*' && LA6_8 <= '+')||(LA6_8 >= '-' && LA6_8 <= ';')||LA6_8=='='||(LA6_8 >= '?' && LA6_8 <= 'Z')||LA6_8=='\\'||(LA6_8 >= '^' && LA6_8 <= 'z')||LA6_8=='|'||(LA6_8 >= '~' && LA6_8 <= '\uFFFF')) ) {s = 20;}

                        else s = 25;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA6_34 = input.LA(1);

                        s = -1;
                        if ( (LA6_34=='*') ) {s = 47;}

                        else if ( ((LA6_34 >= '\u0000' && LA6_34 <= '\b')||(LA6_34 >= '\u000B' && LA6_34 <= '\f')||(LA6_34 >= '\u000E' && LA6_34 <= '\u001F')||(LA6_34 >= '!' && LA6_34 <= '\'')||LA6_34=='+'||(LA6_34 >= '-' && LA6_34 <= ';')||LA6_34=='='||(LA6_34 >= '?' && LA6_34 <= 'Z')||LA6_34=='\\'||(LA6_34 >= '^' && LA6_34 <= 'z')||LA6_34=='|'||(LA6_34 >= '~' && LA6_34 <= '\uFFFF')) ) {s = 48;}

                        else if ( ((LA6_34 >= '\t' && LA6_34 <= '\n')||LA6_34=='\r'||LA6_34==' '||(LA6_34 >= '(' && LA6_34 <= ')')||LA6_34==','||LA6_34=='<'||LA6_34=='>'||LA6_34=='['||LA6_34==']'||LA6_34=='{'||LA6_34=='}') ) {s = 49;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA6_35 = input.LA(1);

                        s = -1;
                        if ( (LA6_35=='\"') ) {s = 37;}

                        else if ( ((LA6_35 >= '\u0000' && LA6_35 <= '\b')||(LA6_35 >= '\u000B' && LA6_35 <= '\f')||(LA6_35 >= '\u000E' && LA6_35 <= '\u001F')||LA6_35=='!'||(LA6_35 >= '#' && LA6_35 <= '\'')||(LA6_35 >= '*' && LA6_35 <= '+')||(LA6_35 >= '-' && LA6_35 <= ';')||LA6_35=='='||(LA6_35 >= '?' && LA6_35 <= 'Z')||(LA6_35 >= '^' && LA6_35 <= 'z')||LA6_35=='|'||(LA6_35 >= '~' && LA6_35 <= '\uFFFF')) ) {s = 35;}

                        else if ( (LA6_35=='\\') ) {s = 36;}

                        else if ( ((LA6_35 >= '\t' && LA6_35 <= '\n')||LA6_35=='\r'||LA6_35==' '||(LA6_35 >= '(' && LA6_35 <= ')')||LA6_35==','||LA6_35=='<'||LA6_35=='>'||LA6_35=='['||LA6_35==']'||LA6_35=='{'||LA6_35=='}') ) {s = 38;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA6_10 = input.LA(1);

                        s = -1;
                        if ( ((LA6_10 >= '\u0000' && LA6_10 <= '\b')||(LA6_10 >= '\u000B' && LA6_10 <= '\f')||(LA6_10 >= '\u000E' && LA6_10 <= '\u001F')||(LA6_10 >= '!' && LA6_10 <= '\'')||(LA6_10 >= '*' && LA6_10 <= '+')||(LA6_10 >= '-' && LA6_10 <= ';')||LA6_10=='='||(LA6_10 >= '?' && LA6_10 <= 'Z')||LA6_10=='\\'||(LA6_10 >= '^' && LA6_10 <= 'z')||LA6_10=='|'||(LA6_10 >= '~' && LA6_10 <= '\uFFFF')) ) {s = 20;}

                        else s = 28;

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA6_7 = input.LA(1);

                        s = -1;
                        if ( ((LA6_7 >= '\u0000' && LA6_7 <= '\b')||(LA6_7 >= '\u000B' && LA6_7 <= '\f')||(LA6_7 >= '\u000E' && LA6_7 <= '\u001F')||(LA6_7 >= '!' && LA6_7 <= '\'')||(LA6_7 >= '*' && LA6_7 <= '+')||(LA6_7 >= '-' && LA6_7 <= ';')||LA6_7=='='||(LA6_7 >= '?' && LA6_7 <= 'Z')||LA6_7=='\\'||(LA6_7 >= '^' && LA6_7 <= 'z')||LA6_7=='|'||(LA6_7 >= '~' && LA6_7 <= '\uFFFF')) ) {s = 20;}

                        else s = 24;

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA6_26 = input.LA(1);

                        s = -1;
                        if ( ((LA6_26 >= '\u0000' && LA6_26 <= '\b')||(LA6_26 >= '\u000B' && LA6_26 <= '\f')||(LA6_26 >= '\u000E' && LA6_26 <= '\u001F')||(LA6_26 >= '!' && LA6_26 <= '\'')||(LA6_26 >= '*' && LA6_26 <= '+')||(LA6_26 >= '-' && LA6_26 <= ';')||LA6_26=='='||(LA6_26 >= '?' && LA6_26 <= 'Z')||LA6_26=='\\'||(LA6_26 >= '^' && LA6_26 <= 'z')||LA6_26=='|'||(LA6_26 >= '~' && LA6_26 <= '\uFFFF')) ) {s = 20;}

                        else s = 40;

                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA6_45 = input.LA(1);

                        s = -1;
                        if ( ((LA6_45 >= '\u0000' && LA6_45 <= '\b')||(LA6_45 >= '\u000B' && LA6_45 <= '\f')||(LA6_45 >= '\u000E' && LA6_45 <= '\u001F')||(LA6_45 >= '!' && LA6_45 <= '\'')||(LA6_45 >= '*' && LA6_45 <= '+')||(LA6_45 >= '-' && LA6_45 <= ';')||LA6_45=='='||(LA6_45 >= '?' && LA6_45 <= 'Z')||LA6_45=='\\'||(LA6_45 >= '^' && LA6_45 <= 'z')||LA6_45=='|'||(LA6_45 >= '~' && LA6_45 <= '\uFFFF')) ) {s = 45;}

                        else s = 46;

                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA6_67 = input.LA(1);

                        s = -1;
                        if ( ((LA6_67 >= '\u0000' && LA6_67 <= '\b')||(LA6_67 >= '\u000B' && LA6_67 <= '\f')||(LA6_67 >= '\u000E' && LA6_67 <= '\u001F')||(LA6_67 >= '!' && LA6_67 <= '\'')||(LA6_67 >= '*' && LA6_67 <= '+')||(LA6_67 >= '-' && LA6_67 <= ';')||LA6_67=='='||(LA6_67 >= '?' && LA6_67 <= 'Z')||LA6_67=='\\'||(LA6_67 >= '^' && LA6_67 <= 'z')||LA6_67=='|'||(LA6_67 >= '~' && LA6_67 <= '\uFFFF')) ) {s = 20;}

                        else s = 68;

                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA6_48 = input.LA(1);

                        s = -1;
                        if ( (LA6_48=='*') ) {s = 47;}

                        else if ( ((LA6_48 >= '\u0000' && LA6_48 <= '\b')||(LA6_48 >= '\u000B' && LA6_48 <= '\f')||(LA6_48 >= '\u000E' && LA6_48 <= '\u001F')||(LA6_48 >= '!' && LA6_48 <= '\'')||LA6_48=='+'||(LA6_48 >= '-' && LA6_48 <= ';')||LA6_48=='='||(LA6_48 >= '?' && LA6_48 <= 'Z')||LA6_48=='\\'||(LA6_48 >= '^' && LA6_48 <= 'z')||LA6_48=='|'||(LA6_48 >= '~' && LA6_48 <= '\uFFFF')) ) {s = 48;}

                        else if ( ((LA6_48 >= '\t' && LA6_48 <= '\n')||LA6_48=='\r'||LA6_48==' '||(LA6_48 >= '(' && LA6_48 <= ')')||LA6_48==','||LA6_48=='<'||LA6_48=='>'||LA6_48=='['||LA6_48==']'||LA6_48=='{'||LA6_48=='}') ) {s = 49;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA6_4 = input.LA(1);

                        s = -1;
                        if ( ((LA6_4 >= '\u0000' && LA6_4 <= '\b')||(LA6_4 >= '\u000B' && LA6_4 <= '\f')||(LA6_4 >= '\u000E' && LA6_4 <= '\u001F')||(LA6_4 >= '!' && LA6_4 <= '\'')||(LA6_4 >= '*' && LA6_4 <= '+')||(LA6_4 >= '-' && LA6_4 <= ';')||LA6_4=='='||(LA6_4 >= '?' && LA6_4 <= 'Z')||LA6_4=='\\'||(LA6_4 >= '^' && LA6_4 <= 'z')||LA6_4=='|'||(LA6_4 >= '~' && LA6_4 <= '\uFFFF')) ) {s = 20;}

                        else s = 22;

                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA6_60 = input.LA(1);

                        s = -1;
                        if ( ((LA6_60 >= '\u0000' && LA6_60 <= '\b')||(LA6_60 >= '\u000B' && LA6_60 <= '\f')||(LA6_60 >= '\u000E' && LA6_60 <= '\u001F')||(LA6_60 >= '!' && LA6_60 <= '\'')||(LA6_60 >= '*' && LA6_60 <= '+')||(LA6_60 >= '-' && LA6_60 <= ';')||LA6_60=='='||(LA6_60 >= '?' && LA6_60 <= 'Z')||LA6_60=='\\'||(LA6_60 >= '^' && LA6_60 <= 'z')||LA6_60=='|'||(LA6_60 >= '~' && LA6_60 <= '\uFFFF')) ) {s = 20;}

                        else s = 64;

                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA6_5 = input.LA(1);

                        s = -1;
                        if ( ((LA6_5 >= '\u0000' && LA6_5 <= '\b')||(LA6_5 >= '\u000B' && LA6_5 <= '\f')||(LA6_5 >= '\u000E' && LA6_5 <= '\u001F')||(LA6_5 >= '!' && LA6_5 <= '\'')||(LA6_5 >= '*' && LA6_5 <= '+')||(LA6_5 >= '-' && LA6_5 <= ';')||LA6_5=='='||(LA6_5 >= '?' && LA6_5 <= 'Z')||LA6_5=='\\'||(LA6_5 >= '^' && LA6_5 <= 'z')||LA6_5=='|'||(LA6_5 >= '~' && LA6_5 <= '\uFFFF')) ) {s = 20;}

                        else s = 23;

                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA6_36 = input.LA(1);

                        s = -1;
                        if ( ((LA6_36 >= '\u0000' && LA6_36 <= '\b')||(LA6_36 >= '\u000B' && LA6_36 <= '\f')||(LA6_36 >= '\u000E' && LA6_36 <= '\u001F')||(LA6_36 >= '!' && LA6_36 <= '\'')||(LA6_36 >= '*' && LA6_36 <= '+')||(LA6_36 >= '-' && LA6_36 <= ';')||LA6_36=='='||(LA6_36 >= '?' && LA6_36 <= 'Z')||LA6_36=='\\'||(LA6_36 >= '^' && LA6_36 <= 'z')||LA6_36=='|'||(LA6_36 >= '~' && LA6_36 <= '\uFFFF')) ) {s = 50;}

                        else if ( ((LA6_36 >= '\t' && LA6_36 <= '\n')||LA6_36=='\r'||LA6_36==' '||(LA6_36 >= '(' && LA6_36 <= ')')||LA6_36==','||LA6_36=='<'||LA6_36=='>'||LA6_36=='['||LA6_36==']'||LA6_36=='{'||LA6_36=='}') ) {s = 38;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA6_9 = input.LA(1);

                        s = -1;
                        if ( (LA6_9=='=') ) {s = 26;}

                        else if ( ((LA6_9 >= '\u0000' && LA6_9 <= '\b')||(LA6_9 >= '\u000B' && LA6_9 <= '\f')||(LA6_9 >= '\u000E' && LA6_9 <= '\u001F')||(LA6_9 >= '!' && LA6_9 <= '\'')||(LA6_9 >= '*' && LA6_9 <= '+')||(LA6_9 >= '-' && LA6_9 <= ';')||(LA6_9 >= '?' && LA6_9 <= 'Z')||LA6_9=='\\'||(LA6_9 >= '^' && LA6_9 <= 'z')||LA6_9=='|'||(LA6_9 >= '~' && LA6_9 <= '\uFFFF')) ) {s = 20;}

                        else s = 27;

                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA6_59 = input.LA(1);

                        s = -1;
                        if ( ((LA6_59 >= '\u0000' && LA6_59 <= '\b')||(LA6_59 >= '\u000B' && LA6_59 <= '\f')||(LA6_59 >= '\u000E' && LA6_59 <= '\u001F')||(LA6_59 >= '!' && LA6_59 <= '\'')||(LA6_59 >= '*' && LA6_59 <= '+')||(LA6_59 >= '-' && LA6_59 <= ';')||LA6_59=='='||(LA6_59 >= '?' && LA6_59 <= 'Z')||LA6_59=='\\'||(LA6_59 >= '^' && LA6_59 <= 'z')||LA6_59=='|'||(LA6_59 >= '~' && LA6_59 <= '\uFFFF')) ) {s = 20;}

                        else s = 63;

                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA6_0 = input.LA(1);

                        s = -1;
                        if ( (LA6_0=='!') ) {s = 1;}

                        else if ( (LA6_0=='(') ) {s = 2;}

                        else if ( (LA6_0==')') ) {s = 3;}

                        else if ( (LA6_0=='*') ) {s = 4;}

                        else if ( (LA6_0=='+') ) {s = 5;}

                        else if ( (LA6_0==',') ) {s = 6;}

                        else if ( (LA6_0=='.') ) {s = 7;}

                        else if ( (LA6_0==':') ) {s = 8;}

                        else if ( (LA6_0=='=') ) {s = 9;}

                        else if ( (LA6_0=='?') ) {s = 10;}

                        else if ( (LA6_0=='I') ) {s = 11;}

                        else if ( (LA6_0=='O') ) {s = 12;}

                        else if ( (LA6_0=='P') ) {s = 13;}

                        else if ( (LA6_0=='R') ) {s = 14;}

                        else if ( (LA6_0=='{') ) {s = 15;}

                        else if ( (LA6_0=='}') ) {s = 16;}

                        else if ( ((LA6_0 >= '\t' && LA6_0 <= '\n')||LA6_0=='\r'||LA6_0==' ') ) {s = 17;}

                        else if ( (LA6_0=='/') ) {s = 18;}

                        else if ( (LA6_0=='\"') ) {s = 19;}

                        else if ( ((LA6_0 >= '\u0000' && LA6_0 <= '\b')||(LA6_0 >= '\u000B' && LA6_0 <= '\f')||(LA6_0 >= '\u000E' && LA6_0 <= '\u001F')||(LA6_0 >= '#' && LA6_0 <= '\'')||LA6_0=='-'||(LA6_0 >= '0' && LA6_0 <= '9')||LA6_0==';'||(LA6_0 >= '@' && LA6_0 <= 'H')||(LA6_0 >= 'J' && LA6_0 <= 'N')||LA6_0=='Q'||(LA6_0 >= 'S' && LA6_0 <= 'Z')||LA6_0=='\\'||(LA6_0 >= '^' && LA6_0 <= 'z')||LA6_0=='|'||(LA6_0 >= '~' && LA6_0 <= '\uFFFF')) ) {s = 20;}

                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA6_37 = input.LA(1);

                        s = -1;
                        if ( ((LA6_37 >= '\u0000' && LA6_37 <= '\b')||(LA6_37 >= '\u000B' && LA6_37 <= '\f')||(LA6_37 >= '\u000E' && LA6_37 <= '\u001F')||(LA6_37 >= '!' && LA6_37 <= '\'')||(LA6_37 >= '*' && LA6_37 <= '+')||(LA6_37 >= '-' && LA6_37 <= ';')||LA6_37=='='||(LA6_37 >= '?' && LA6_37 <= 'Z')||LA6_37=='\\'||(LA6_37 >= '^' && LA6_37 <= 'z')||LA6_37=='|'||(LA6_37 >= '~' && LA6_37 <= '\uFFFF')) ) {s = 20;}

                        else s = 38;

                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA6_55 = input.LA(1);

                        s = -1;
                        if ( (LA6_55=='*') ) {s = 47;}

                        else if ( ((LA6_55 >= '\u0000' && LA6_55 <= '\b')||(LA6_55 >= '\u000B' && LA6_55 <= '\f')||(LA6_55 >= '\u000E' && LA6_55 <= '\u001F')||(LA6_55 >= '!' && LA6_55 <= '\'')||LA6_55=='+'||(LA6_55 >= '-' && LA6_55 <= ';')||LA6_55=='='||(LA6_55 >= '?' && LA6_55 <= 'Z')||LA6_55=='\\'||(LA6_55 >= '^' && LA6_55 <= 'z')||LA6_55=='|'||(LA6_55 >= '~' && LA6_55 <= '\uFFFF')) ) {s = 48;}

                        else s = 49;

                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA6_62 = input.LA(1);

                        s = -1;
                        if ( ((LA6_62 >= '\u0000' && LA6_62 <= '\b')||(LA6_62 >= '\u000B' && LA6_62 <= '\f')||(LA6_62 >= '\u000E' && LA6_62 <= '\u001F')||(LA6_62 >= '!' && LA6_62 <= '\'')||(LA6_62 >= '*' && LA6_62 <= '+')||(LA6_62 >= '-' && LA6_62 <= ';')||LA6_62=='='||(LA6_62 >= '?' && LA6_62 <= 'Z')||LA6_62=='\\'||(LA6_62 >= '^' && LA6_62 <= 'z')||LA6_62=='|'||(LA6_62 >= '~' && LA6_62 <= '\uFFFF')) ) {s = 20;}

                        else s = 66;

                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA6_19 = input.LA(1);

                        s = -1;
                        if ( ((LA6_19 >= '\u0000' && LA6_19 <= '\b')||(LA6_19 >= '\u000B' && LA6_19 <= '\f')||(LA6_19 >= '\u000E' && LA6_19 <= '\u001F')||LA6_19=='!'||(LA6_19 >= '#' && LA6_19 <= '\'')||(LA6_19 >= '*' && LA6_19 <= '+')||(LA6_19 >= '-' && LA6_19 <= ';')||LA6_19=='='||(LA6_19 >= '?' && LA6_19 <= 'Z')||(LA6_19 >= '^' && LA6_19 <= 'z')||LA6_19=='|'||(LA6_19 >= '~' && LA6_19 <= '\uFFFF')) ) {s = 35;}

                        else if ( (LA6_19=='\\') ) {s = 36;}

                        else if ( (LA6_19=='\"') ) {s = 37;}

                        else if ( ((LA6_19 >= '\t' && LA6_19 <= '\n')||LA6_19=='\r'||LA6_19==' '||(LA6_19 >= '(' && LA6_19 <= ')')||LA6_19==','||LA6_19=='<'||LA6_19=='>'||LA6_19=='['||LA6_19==']'||LA6_19=='{'||LA6_19=='}') ) {s = 38;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA6_21 = input.LA(1);

                        s = -1;
                        if ( ((LA6_21 >= '\u0000' && LA6_21 <= '\b')||(LA6_21 >= '\u000B' && LA6_21 <= '\f')||(LA6_21 >= '\u000E' && LA6_21 <= '\u001F')||(LA6_21 >= '!' && LA6_21 <= '\'')||(LA6_21 >= '*' && LA6_21 <= '+')||(LA6_21 >= '-' && LA6_21 <= ';')||LA6_21=='='||(LA6_21 >= '?' && LA6_21 <= 'Z')||LA6_21=='\\'||(LA6_21 >= '^' && LA6_21 <= 'z')||LA6_21=='|'||(LA6_21 >= '~' && LA6_21 <= '\uFFFF')) ) {s = 20;}

                        else s = 39;

                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA6_33 = input.LA(1);

                        s = -1;
                        if ( ((LA6_33 >= '\u0000' && LA6_33 <= '\b')||(LA6_33 >= '\u000B' && LA6_33 <= '\f')||(LA6_33 >= '\u000E' && LA6_33 <= '\u001F')||(LA6_33 >= '!' && LA6_33 <= '\'')||(LA6_33 >= '*' && LA6_33 <= '+')||(LA6_33 >= '-' && LA6_33 <= ';')||LA6_33=='='||(LA6_33 >= '?' && LA6_33 <= 'Z')||LA6_33=='\\'||(LA6_33 >= '^' && LA6_33 <= 'z')||LA6_33=='|'||(LA6_33 >= '~' && LA6_33 <= '\uFFFF')) ) {s = 45;}

                        else s = 46;

                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA6_47 = input.LA(1);

                        s = -1;
                        if ( (LA6_47=='/') ) {s = 55;}

                        else if ( (LA6_47=='*') ) {s = 47;}

                        else if ( ((LA6_47 >= '\u0000' && LA6_47 <= '\b')||(LA6_47 >= '\u000B' && LA6_47 <= '\f')||(LA6_47 >= '\u000E' && LA6_47 <= '\u001F')||(LA6_47 >= '!' && LA6_47 <= '\'')||LA6_47=='+'||(LA6_47 >= '-' && LA6_47 <= '.')||(LA6_47 >= '0' && LA6_47 <= ';')||LA6_47=='='||(LA6_47 >= '?' && LA6_47 <= 'Z')||LA6_47=='\\'||(LA6_47 >= '^' && LA6_47 <= 'z')||LA6_47=='|'||(LA6_47 >= '~' && LA6_47 <= '\uFFFF')) ) {s = 48;}

                        else if ( ((LA6_47 >= '\t' && LA6_47 <= '\n')||LA6_47=='\r'||LA6_47==' '||(LA6_47 >= '(' && LA6_47 <= ')')||LA6_47==','||LA6_47=='<'||LA6_47=='>'||LA6_47=='['||LA6_47==']'||LA6_47=='{'||LA6_47=='}') ) {s = 49;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 6, _s, input);
            error(nvae);
            throw nvae;
        }

    }
 

}