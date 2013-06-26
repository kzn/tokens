// $ANTLR 3.4 /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g 2013-06-26 22:21:47

  package name.kazennikov.annotations;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class JapeNGParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "INPUT", "OPTION", "OPTIONS", "PHASE", "SIMPLE", "SINGLE_COMMENT", "STRING", "WS", "'!='", "'('", "')'", "'*'", "'+'", "','", "'.'", "':'", "'='", "'=='", "'?'", "'Input:'", "'Options:'", "'Phase:'", "'Rule:'", "'{'", "'}'"
    };

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
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public JapeNGParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public JapeNGParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return JapeNGParser.tokenNames; }
    public String getGrammarFileName() { return "/home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g"; }


    public static class jape_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "jape"
    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:26:1: jape : phase input opts ( rule )+ ;
    public final JapeNGParser.jape_return jape() throws RecognitionException {
        JapeNGParser.jape_return retval = new JapeNGParser.jape_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        JapeNGParser.phase_return phase1 =null;

        JapeNGParser.input_return input2 =null;

        JapeNGParser.opts_return opts3 =null;

        JapeNGParser.rule_return rule4 =null;



        try {
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:26:5: ( phase input opts ( rule )+ )
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:26:7: phase input opts ( rule )+
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_phase_in_jape91);
            phase1=phase();

            state._fsp--;

            adaptor.addChild(root_0, phase1.getTree());

            pushFollow(FOLLOW_input_in_jape93);
            input2=input();

            state._fsp--;

            adaptor.addChild(root_0, input2.getTree());

            pushFollow(FOLLOW_opts_in_jape95);
            opts3=opts();

            state._fsp--;

            adaptor.addChild(root_0, opts3.getTree());

            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:26:24: ( rule )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==27) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:26:24: rule
            	    {
            	    pushFollow(FOLLOW_rule_in_jape97);
            	    rule4=rule();

            	    state._fsp--;

            	    adaptor.addChild(root_0, rule4.getTree());

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


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "jape"


    public static class phase_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "phase"
    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:27:1: phase : 'Phase:' SIMPLE -> ^( PHASE SIMPLE ) ;
    public final JapeNGParser.phase_return phase() throws RecognitionException {
        JapeNGParser.phase_return retval = new JapeNGParser.phase_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal5=null;
        Token SIMPLE6=null;

        CommonTree string_literal5_tree=null;
        CommonTree SIMPLE6_tree=null;
        RewriteRuleTokenStream stream_26=new RewriteRuleTokenStream(adaptor,"token 26");
        RewriteRuleTokenStream stream_SIMPLE=new RewriteRuleTokenStream(adaptor,"token SIMPLE");

        try {
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:27:6: ( 'Phase:' SIMPLE -> ^( PHASE SIMPLE ) )
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:27:8: 'Phase:' SIMPLE
            {
            string_literal5=(Token)match(input,26,FOLLOW_26_in_phase104);  
            stream_26.add(string_literal5);


            SIMPLE6=(Token)match(input,SIMPLE,FOLLOW_SIMPLE_in_phase106);  
            stream_SIMPLE.add(SIMPLE6);


            // AST REWRITE
            // elements: SIMPLE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 27:24: -> ^( PHASE SIMPLE )
            {
                // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:27:27: ^( PHASE SIMPLE )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(PHASE, "PHASE")
                , root_1);

                adaptor.addChild(root_1, 
                stream_SIMPLE.nextNode()
                );

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "phase"


    public static class input_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "input"
    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:28:1: input : 'Input:' ( SIMPLE )+ -> ^( INPUT ( SIMPLE )+ ) ;
    public final JapeNGParser.input_return input() throws RecognitionException {
        JapeNGParser.input_return retval = new JapeNGParser.input_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal7=null;
        Token SIMPLE8=null;

        CommonTree string_literal7_tree=null;
        CommonTree SIMPLE8_tree=null;
        RewriteRuleTokenStream stream_24=new RewriteRuleTokenStream(adaptor,"token 24");
        RewriteRuleTokenStream stream_SIMPLE=new RewriteRuleTokenStream(adaptor,"token SIMPLE");

        try {
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:28:6: ( 'Input:' ( SIMPLE )+ -> ^( INPUT ( SIMPLE )+ ) )
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:28:8: 'Input:' ( SIMPLE )+
            {
            string_literal7=(Token)match(input,24,FOLLOW_24_in_input120);  
            stream_24.add(string_literal7);


            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:28:17: ( SIMPLE )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==SIMPLE) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:28:17: SIMPLE
            	    {
            	    SIMPLE8=(Token)match(input,SIMPLE,FOLLOW_SIMPLE_in_input122);  
            	    stream_SIMPLE.add(SIMPLE8);


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            // AST REWRITE
            // elements: SIMPLE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 28:25: -> ^( INPUT ( SIMPLE )+ )
            {
                // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:28:28: ^( INPUT ( SIMPLE )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(INPUT, "INPUT")
                , root_1);

                if ( !(stream_SIMPLE.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_SIMPLE.hasNext() ) {
                    adaptor.addChild(root_1, 
                    stream_SIMPLE.nextNode()
                    );

                }
                stream_SIMPLE.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "input"


    public static class opts_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "opts"
    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:29:1: opts : 'Options:' option ( ',' option )* -> ^( OPTIONS ( option )+ ) ;
    public final JapeNGParser.opts_return opts() throws RecognitionException {
        JapeNGParser.opts_return retval = new JapeNGParser.opts_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal9=null;
        Token char_literal11=null;
        JapeNGParser.option_return option10 =null;

        JapeNGParser.option_return option12 =null;


        CommonTree string_literal9_tree=null;
        CommonTree char_literal11_tree=null;
        RewriteRuleTokenStream stream_18=new RewriteRuleTokenStream(adaptor,"token 18");
        RewriteRuleTokenStream stream_25=new RewriteRuleTokenStream(adaptor,"token 25");
        RewriteRuleSubtreeStream stream_option=new RewriteRuleSubtreeStream(adaptor,"rule option");
        try {
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:29:5: ( 'Options:' option ( ',' option )* -> ^( OPTIONS ( option )+ ) )
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:29:7: 'Options:' option ( ',' option )*
            {
            string_literal9=(Token)match(input,25,FOLLOW_25_in_opts138);  
            stream_25.add(string_literal9);


            pushFollow(FOLLOW_option_in_opts140);
            option10=option();

            state._fsp--;

            stream_option.add(option10.getTree());

            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:29:25: ( ',' option )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==18) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:29:26: ',' option
            	    {
            	    char_literal11=(Token)match(input,18,FOLLOW_18_in_opts143);  
            	    stream_18.add(char_literal11);


            	    pushFollow(FOLLOW_option_in_opts145);
            	    option12=option();

            	    state._fsp--;

            	    stream_option.add(option12.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            // AST REWRITE
            // elements: option
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 29:39: -> ^( OPTIONS ( option )+ )
            {
                // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:29:42: ^( OPTIONS ( option )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(OPTIONS, "OPTIONS")
                , root_1);

                if ( !(stream_option.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_option.hasNext() ) {
                    adaptor.addChild(root_1, stream_option.nextTree());

                }
                stream_option.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "opts"


    public static class option_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "option"
    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:30:1: option : SIMPLE '=' SIMPLE -> ^( OPTION SIMPLE SIMPLE ) ;
    public final JapeNGParser.option_return option() throws RecognitionException {
        JapeNGParser.option_return retval = new JapeNGParser.option_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token SIMPLE13=null;
        Token char_literal14=null;
        Token SIMPLE15=null;

        CommonTree SIMPLE13_tree=null;
        CommonTree char_literal14_tree=null;
        CommonTree SIMPLE15_tree=null;
        RewriteRuleTokenStream stream_21=new RewriteRuleTokenStream(adaptor,"token 21");
        RewriteRuleTokenStream stream_SIMPLE=new RewriteRuleTokenStream(adaptor,"token SIMPLE");

        try {
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:30:7: ( SIMPLE '=' SIMPLE -> ^( OPTION SIMPLE SIMPLE ) )
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:30:9: SIMPLE '=' SIMPLE
            {
            SIMPLE13=(Token)match(input,SIMPLE,FOLLOW_SIMPLE_in_option162);  
            stream_SIMPLE.add(SIMPLE13);


            char_literal14=(Token)match(input,21,FOLLOW_21_in_option164);  
            stream_21.add(char_literal14);


            SIMPLE15=(Token)match(input,SIMPLE,FOLLOW_SIMPLE_in_option166);  
            stream_SIMPLE.add(SIMPLE15);


            // AST REWRITE
            // elements: SIMPLE, SIMPLE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 30:27: -> ^( OPTION SIMPLE SIMPLE )
            {
                // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:30:30: ^( OPTION SIMPLE SIMPLE )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(OPTION, "OPTION")
                , root_1);

                adaptor.addChild(root_1, 
                stream_SIMPLE.nextNode()
                );

                adaptor.addChild(root_1, 
                stream_SIMPLE.nextNode()
                );

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "option"


    public static class rule_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "rule"
    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:32:1: rule : rule_header matcher_group ;
    public final JapeNGParser.rule_return rule() throws RecognitionException {
        JapeNGParser.rule_return retval = new JapeNGParser.rule_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        JapeNGParser.rule_header_return rule_header16 =null;

        JapeNGParser.matcher_group_return matcher_group17 =null;



        try {
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:32:5: ( rule_header matcher_group )
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:32:7: rule_header matcher_group
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_rule_header_in_rule183);
            rule_header16=rule_header();

            state._fsp--;

            adaptor.addChild(root_0, rule_header16.getTree());

            pushFollow(FOLLOW_matcher_group_in_rule185);
            matcher_group17=matcher_group();

            state._fsp--;

            adaptor.addChild(root_0, matcher_group17.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "rule"


    public static class rule_header_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "rule_header"
    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:33:1: rule_header : 'Rule:' SIMPLE ;
    public final JapeNGParser.rule_header_return rule_header() throws RecognitionException {
        JapeNGParser.rule_header_return retval = new JapeNGParser.rule_header_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal18=null;
        Token SIMPLE19=null;

        CommonTree string_literal18_tree=null;
        CommonTree SIMPLE19_tree=null;

        try {
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:33:12: ( 'Rule:' SIMPLE )
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:33:14: 'Rule:' SIMPLE
            {
            root_0 = (CommonTree)adaptor.nil();


            string_literal18=(Token)match(input,27,FOLLOW_27_in_rule_header191); 
            string_literal18_tree = 
            (CommonTree)adaptor.create(string_literal18)
            ;
            adaptor.addChild(root_0, string_literal18_tree);


            SIMPLE19=(Token)match(input,SIMPLE,FOLLOW_SIMPLE_in_rule_header193); 
            SIMPLE19_tree = 
            (CommonTree)adaptor.create(SIMPLE19)
            ;
            adaptor.addChild(root_0, SIMPLE19_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "rule_header"


    public static class matcher_element_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "matcher_element"
    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:34:1: matcher_element : '{' SIMPLE ( '.' SIMPLE )? ( op ( SIMPLE | STRING ) )? '}' ;
    public final JapeNGParser.matcher_element_return matcher_element() throws RecognitionException {
        JapeNGParser.matcher_element_return retval = new JapeNGParser.matcher_element_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal20=null;
        Token SIMPLE21=null;
        Token char_literal22=null;
        Token SIMPLE23=null;
        Token set25=null;
        Token char_literal26=null;
        JapeNGParser.op_return op24 =null;


        CommonTree char_literal20_tree=null;
        CommonTree SIMPLE21_tree=null;
        CommonTree char_literal22_tree=null;
        CommonTree SIMPLE23_tree=null;
        CommonTree set25_tree=null;
        CommonTree char_literal26_tree=null;

        try {
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:34:16: ( '{' SIMPLE ( '.' SIMPLE )? ( op ( SIMPLE | STRING ) )? '}' )
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:34:18: '{' SIMPLE ( '.' SIMPLE )? ( op ( SIMPLE | STRING ) )? '}'
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal20=(Token)match(input,28,FOLLOW_28_in_matcher_element199); 
            char_literal20_tree = 
            (CommonTree)adaptor.create(char_literal20)
            ;
            adaptor.addChild(root_0, char_literal20_tree);


            SIMPLE21=(Token)match(input,SIMPLE,FOLLOW_SIMPLE_in_matcher_element201); 
            SIMPLE21_tree = 
            (CommonTree)adaptor.create(SIMPLE21)
            ;
            adaptor.addChild(root_0, SIMPLE21_tree);


            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:34:29: ( '.' SIMPLE )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==19) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:34:30: '.' SIMPLE
                    {
                    char_literal22=(Token)match(input,19,FOLLOW_19_in_matcher_element204); 
                    char_literal22_tree = 
                    (CommonTree)adaptor.create(char_literal22)
                    ;
                    adaptor.addChild(root_0, char_literal22_tree);


                    SIMPLE23=(Token)match(input,SIMPLE,FOLLOW_SIMPLE_in_matcher_element206); 
                    SIMPLE23_tree = 
                    (CommonTree)adaptor.create(SIMPLE23)
                    ;
                    adaptor.addChild(root_0, SIMPLE23_tree);


                    }
                    break;

            }


            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:34:43: ( op ( SIMPLE | STRING ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==13||LA5_0==22) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:34:44: op ( SIMPLE | STRING )
                    {
                    pushFollow(FOLLOW_op_in_matcher_element211);
                    op24=op();

                    state._fsp--;

                    adaptor.addChild(root_0, op24.getTree());

                    set25=(Token)input.LT(1);

                    if ( input.LA(1)==SIMPLE||input.LA(1)==STRING ) {
                        input.consume();
                        adaptor.addChild(root_0, 
                        (CommonTree)adaptor.create(set25)
                        );
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }


            char_literal26=(Token)match(input,29,FOLLOW_29_in_matcher_element221); 
            char_literal26_tree = 
            (CommonTree)adaptor.create(char_literal26)
            ;
            adaptor.addChild(root_0, char_literal26_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "matcher_element"


    public static class op_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "op"
    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:35:1: op : ( '!=' | '==' );
    public final JapeNGParser.op_return op() throws RecognitionException {
        JapeNGParser.op_return retval = new JapeNGParser.op_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token set27=null;

        CommonTree set27_tree=null;

        try {
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:35:3: ( '!=' | '==' )
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:
            {
            root_0 = (CommonTree)adaptor.nil();


            set27=(Token)input.LT(1);

            if ( input.LA(1)==13||input.LA(1)==22 ) {
                input.consume();
                adaptor.addChild(root_0, 
                (CommonTree)adaptor.create(set27)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "op"


    public static class matcher_group_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "matcher_group"
    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:1: matcher_group : '(' ( matcher_group | matcher_element )+ ')' ( ( ':' SIMPLE ) | '?' | '*' | '+' ) ;
    public final JapeNGParser.matcher_group_return matcher_group() throws RecognitionException {
        JapeNGParser.matcher_group_return retval = new JapeNGParser.matcher_group_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal28=null;
        Token char_literal31=null;
        Token char_literal32=null;
        Token SIMPLE33=null;
        Token char_literal34=null;
        Token char_literal35=null;
        Token char_literal36=null;
        JapeNGParser.matcher_group_return matcher_group29 =null;

        JapeNGParser.matcher_element_return matcher_element30 =null;


        CommonTree char_literal28_tree=null;
        CommonTree char_literal31_tree=null;
        CommonTree char_literal32_tree=null;
        CommonTree SIMPLE33_tree=null;
        CommonTree char_literal34_tree=null;
        CommonTree char_literal35_tree=null;
        CommonTree char_literal36_tree=null;

        try {
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:14: ( '(' ( matcher_group | matcher_element )+ ')' ( ( ':' SIMPLE ) | '?' | '*' | '+' ) )
            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:16: '(' ( matcher_group | matcher_element )+ ')' ( ( ':' SIMPLE ) | '?' | '*' | '+' )
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal28=(Token)match(input,14,FOLLOW_14_in_matcher_group238); 
            char_literal28_tree = 
            (CommonTree)adaptor.create(char_literal28)
            ;
            adaptor.addChild(root_0, char_literal28_tree);


            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:20: ( matcher_group | matcher_element )+
            int cnt6=0;
            loop6:
            do {
                int alt6=3;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==14) ) {
                    alt6=1;
                }
                else if ( (LA6_0==28) ) {
                    alt6=2;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:21: matcher_group
            	    {
            	    pushFollow(FOLLOW_matcher_group_in_matcher_group241);
            	    matcher_group29=matcher_group();

            	    state._fsp--;

            	    adaptor.addChild(root_0, matcher_group29.getTree());

            	    }
            	    break;
            	case 2 :
            	    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:37: matcher_element
            	    {
            	    pushFollow(FOLLOW_matcher_element_in_matcher_group245);
            	    matcher_element30=matcher_element();

            	    state._fsp--;

            	    adaptor.addChild(root_0, matcher_element30.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            char_literal31=(Token)match(input,15,FOLLOW_15_in_matcher_group249); 
            char_literal31_tree = 
            (CommonTree)adaptor.create(char_literal31)
            ;
            adaptor.addChild(root_0, char_literal31_tree);


            // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:59: ( ( ':' SIMPLE ) | '?' | '*' | '+' )
            int alt7=4;
            switch ( input.LA(1) ) {
            case 20:
                {
                alt7=1;
                }
                break;
            case 23:
                {
                alt7=2;
                }
                break;
            case 16:
                {
                alt7=3;
                }
                break;
            case 17:
                {
                alt7=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;

            }

            switch (alt7) {
                case 1 :
                    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:60: ( ':' SIMPLE )
                    {
                    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:60: ( ':' SIMPLE )
                    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:61: ':' SIMPLE
                    {
                    char_literal32=(Token)match(input,20,FOLLOW_20_in_matcher_group253); 
                    char_literal32_tree = 
                    (CommonTree)adaptor.create(char_literal32)
                    ;
                    adaptor.addChild(root_0, char_literal32_tree);


                    SIMPLE33=(Token)match(input,SIMPLE,FOLLOW_SIMPLE_in_matcher_group255); 
                    SIMPLE33_tree = 
                    (CommonTree)adaptor.create(SIMPLE33)
                    ;
                    adaptor.addChild(root_0, SIMPLE33_tree);


                    }


                    }
                    break;
                case 2 :
                    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:75: '?'
                    {
                    char_literal34=(Token)match(input,23,FOLLOW_23_in_matcher_group260); 
                    char_literal34_tree = 
                    (CommonTree)adaptor.create(char_literal34)
                    ;
                    adaptor.addChild(root_0, char_literal34_tree);


                    }
                    break;
                case 3 :
                    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:81: '*'
                    {
                    char_literal35=(Token)match(input,16,FOLLOW_16_in_matcher_group264); 
                    char_literal35_tree = 
                    (CommonTree)adaptor.create(char_literal35)
                    ;
                    adaptor.addChild(root_0, char_literal35_tree);


                    }
                    break;
                case 4 :
                    // /home/ant/workspace/tokens/src/main/antlr3/name/kazennikov/annotations/JapeNG.g:36:87: '+'
                    {
                    char_literal36=(Token)match(input,17,FOLLOW_17_in_matcher_group268); 
                    char_literal36_tree = 
                    (CommonTree)adaptor.create(char_literal36)
                    ;
                    adaptor.addChild(root_0, char_literal36_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "matcher_group"

    // Delegated rules


 

    public static final BitSet FOLLOW_phase_in_jape91 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_input_in_jape93 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_opts_in_jape95 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_rule_in_jape97 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_26_in_phase104 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIMPLE_in_phase106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_input120 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIMPLE_in_input122 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_25_in_opts138 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_option_in_opts140 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_18_in_opts143 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_option_in_opts145 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_SIMPLE_in_option162 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_option164 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIMPLE_in_option166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_header_in_rule183 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_matcher_group_in_rule185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule_header191 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIMPLE_in_rule_header193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_matcher_element199 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIMPLE_in_matcher_element201 = new BitSet(new long[]{0x0000000020482000L});
    public static final BitSet FOLLOW_19_in_matcher_element204 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIMPLE_in_matcher_element206 = new BitSet(new long[]{0x0000000020402000L});
    public static final BitSet FOLLOW_op_in_matcher_element211 = new BitSet(new long[]{0x0000000000000A00L});
    public static final BitSet FOLLOW_set_in_matcher_element213 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_matcher_element221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_matcher_group238 = new BitSet(new long[]{0x0000000010004000L});
    public static final BitSet FOLLOW_matcher_group_in_matcher_group241 = new BitSet(new long[]{0x000000001000C000L});
    public static final BitSet FOLLOW_matcher_element_in_matcher_group245 = new BitSet(new long[]{0x000000001000C000L});
    public static final BitSet FOLLOW_15_in_matcher_group249 = new BitSet(new long[]{0x0000000000930000L});
    public static final BitSet FOLLOW_20_in_matcher_group253 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIMPLE_in_matcher_group255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_matcher_group260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_matcher_group264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_matcher_group268 = new BitSet(new long[]{0x0000000000000002L});

}