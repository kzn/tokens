package name.kazennikov.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import name.kazennikov.annotations.gate.BaseGateTokenizer;
import gate.Document;
import gate.Gate;
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;
import gate.util.GateException;

public class GatePerfTest {
	public static void main(String[] args) throws Exception {
		Gate.init();
		

		
		File data = new File("../fb2nlp/sents.txt");
		
		BufferedReader br = new BufferedReader(new FileReader(data));
		int count = 0;
		int lineCount = 0;
		long start = System.currentTimeMillis();
		BaseGateTokenizer tokenizer = new BaseGateTokenizer();
		while(true) {
			String line = br.readLine();
			
			if(line == null)
				break;
			
			line = line.trim();
			
			if(line.isEmpty())
				continue;
			
			Document doc = new DocumentImpl();
			doc.setContent(new DocumentContentImpl(line));
			
			tokenizer.setDocument(doc);
			tokenizer.execute();
			count += doc.getAnnotations().size();
			doc.cleanup();
		}
		
		System.out.printf("%d tokens%n", count);
		System.out.printf("Elapsed: %d ms%n", System.currentTimeMillis() - start);


	}

}
