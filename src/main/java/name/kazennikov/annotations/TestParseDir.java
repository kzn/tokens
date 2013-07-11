package name.kazennikov.annotations;

import java.io.File;
import java.nio.charset.Charset;

import name.kazennikov.annotations.patterns.JapeNGASTParser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import com.google.common.io.Files;

public class TestParseDir {
	public static void testParseDirs(File f) throws Exception {
		if(!f.exists())
			return;
		
		if(f.isFile()) {
			JapeNGASTParser p = new JapeNGASTParser();
			String s = Files.toString(f, Charset.forName("UTF-8"));
			CharStream stream = new ANTLRStringStream(s);
			JapeNGLexer lexer = new JapeNGLexer(stream);
			CommonTokenStream tokenStream = new CommonTokenStream(lexer);
			JapeNGParser parser = new JapeNGParser(tokenStream);
			CommonTree tree = (CommonTree) parser.jape().getTree();
			p.parse(tree);
			System.out.printf("file:%s%n%s%n%n", f, tree.toStringTree());

		} else if(f.isDirectory()) {
			for(File file : f.listFiles()) {
				testParseDirs(file);
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		testParseDirs(new File("jape/annie"));
	}

}
