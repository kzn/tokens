package name.kazennikov.annotations.patterns;

import java.io.File;

import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.Document;
import name.kazennikov.annotations.annotators.BasicTokenizer;

import org.apache.log4j.BasicConfigurator;

public class DebugJape {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		String s = "66.1V (1), 422-426";

		BasicTokenizer t = new BasicTokenizer();
		t.setSeparator(",.!?()[]\"'$%^&*#{}\\|/-");
		RecursiveJapeAnnotator jape = new RecursiveJapeAnnotator();
		jape.setJapeFile(new File("../tokens/jape/parser/4.jape"));
		jape.init();
		
		Document d = new Document(s);
		t.annotate(d);
		jape.annotate(d);
		
		for(Annotation a : d.get("Dashed")) {
			System.out.println(a);
		}

	}

}
