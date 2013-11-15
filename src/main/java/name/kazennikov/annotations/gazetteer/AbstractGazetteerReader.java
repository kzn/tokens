package name.kazennikov.annotations.gazetteer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class AbstractGazetteerReader {
	GazetteerDefinition rec;
	char sep;
	
	public AbstractGazetteerReader(GazetteerDefinition rec, char sep) {
		this.rec = rec;
		this.sep = sep;
	}
	
	public void read() throws IOException {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(rec.file), Charset.forName("UTF-8")));
			
			while(true) {
				String s = br.readLine();
				if(s == null)
					break;
				s = s.trim();
				
				if(s.isEmpty())
					continue;
				
				String[] parts = s.split(Pattern.quote("" + sep));
				String word = parts[0];
				
				List<String> featNames = new ArrayList<>();
				List<String> featValues = new ArrayList<>();
				
				for(int i = 1; i < parts.length; i++) {
					int sep = parts[i].indexOf('=');
					if(sep == -1)
						throw new IllegalStateException();
					
					String featName = parts[i].substring(0, sep).trim();
					String featValue = parts[i].substring(sep + 1).trim();
					
					featNames.add(featName);
					featValues.add(featValue);
				}
				
				process(word, featNames, featValues);
			}
			
		} finally {
			if(br != null)
				br.close();
		}
	}
	
	public abstract void process(String entry, List<String> featNames, List<String> featValues);
}
