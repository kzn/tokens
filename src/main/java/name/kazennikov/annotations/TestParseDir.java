package name.kazennikov.annotations;

import java.io.File;
import java.nio.charset.Charset;

import name.kazennikov.annotations.patterns.JapeNGASTParser;
import name.kazennikov.annotations.patterns.Phase;

import com.google.common.io.Files;

public class TestParseDir {
	public static void testParseDirs(File f) throws Exception {
		if(!f.exists())
			return;
		
		if(f.isFile()) {
			String s = Files.toString(f, Charset.forName("UTF-8"));
			Phase phase = JapeNGASTParser.parse(s);
			phase = phase;
			

		} else if(f.isDirectory()) {
			for(File file : f.listFiles()) {
				testParseDirs(file);
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		testParseDirs(new File("jape/parser"));
	}

}
