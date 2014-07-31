package sandbox;

import com.google.common.io.Files;
import name.kazennikov.annotations.JapeNG4BaseVisitor;
import name.kazennikov.annotations.JapeNG4Lexer;
import name.kazennikov.annotations.JapeNG4Parser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by kzn on 7/22/14.
 */
public class TestAntlr4 {
    public static JapeNG4Parser getParser(String data) {
        ANTLRInputStream ss = new ANTLRInputStream(data);
        JapeNG4Lexer lexer = new JapeNG4Lexer(ss);
        CommonTokenStream ts = new CommonTokenStream(lexer);
        JapeNG4Parser parser = new JapeNG4Parser(ts);
        return parser;
    }


    public static ParseTree parseGrammar(String data) {
        JapeNG4Parser parser = getParser(data);
        return parser.jape();
    }

    public static void walkRec(File baseDir) throws IOException {
        for(File f : baseDir.listFiles()) {
            if(f.isDirectory())
                walkRec(f);

            if(f.getName().endsWith(".jape")) {
                System.err.printf("Parsing: %s%n", f);
                parseFile(f);
            }
        }
    }

    public static ParseTree parseFile(File f) throws IOException {
        return parseGrammar(Files.toString(f, Charset.forName("UTF-8")));
    }

    public static class TestVisitor extends JapeNG4BaseVisitor<Integer> {
        @Override
        public Integer visitPhase(@NotNull JapeNG4Parser.PhaseContext ctx) {
            return super.visitPhase(ctx);
        }
    }



    public static void main(String[] args) throws IOException {
        //ParseTree pt =
        //parseFile(new File("../gate/misc/jape/grammars/date2.jape"));
       //walkRec(new File("../gate"));
        ParseTree pt = parseGrammar(Files.toString(new File("jape/parser/0.jape"), Charset.forName("UTF-8")));
        TestVisitor test = new TestVisitor();
        test.visit(pt);


    }
}
