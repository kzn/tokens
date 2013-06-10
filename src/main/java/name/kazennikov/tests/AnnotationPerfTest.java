package name.kazennikov.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.BasicTokenizer;
import name.kazennikov.annotations.Document;

public class AnnotationPerfTest {
	public static void main(String[] args) throws Exception {
		File data = new File("../fb2nlp/sents.txt");
		
		BufferedReader br = new BufferedReader(new FileReader(data));
		int count = 0;
		long start = System.currentTimeMillis();
		BasicTokenizer tokenizer = new BasicTokenizer();
		while(true) {
			String line = br.readLine();
			
			if(line == null)
				break;
			
			line = line.trim();
			
			if(line.isEmpty())
				continue;
			
			Document doc = new Document(line);
			
			tokenizer.annotate(doc);
			count += doc.get(Annotation.TOKEN).size();
			
		}
		
		System.out.printf("%d tokens%n", count);
		System.out.printf("Elapsed: %d ms%n", System.currentTimeMillis() - start);
	}

}
