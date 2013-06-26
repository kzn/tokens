package name.kazennikov.annotations;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

import com.google.common.io.Files;

public class TestGrammar {
	public static void main(String... args) throws RecognitionException, IOException {
		String s = Files.toString(new File("jape/test.jape"), Charset.forName("UTF-8"));
		CharStream stream = new ANTLRStringStream(s);
		JapeNGLexer lexer = new JapeNGLexer(stream);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		JapeNGParser parser = new JapeNGParser(tokenStream);
		Tree tree = parser.jape().tree;
		System.out.printf("%s%n", tree.toStringTree());

	}

}
