package name.kazennikov.annotations.gazetteer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public abstract class AbstractGazetteerListReader {
	public void read(File f) throws IOException {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(f), Charset.forName("UTF-8")));
			
			while(true) {
				String s = br.readLine();
				if(s == null)
					break;
				if(s.startsWith("# "))
					continue;
				
				s = s.trim();
				
				if(s.isEmpty())
					continue;
				
				String[] parts = s.trim().split(":");
				
				
				File file = new File(f.getParent(), parts[0]);
				String majorType = parts.length > 1? parts[1] : null;
				String minorType = parts.length > 2? parts[2] : null;
				String annotation = parts.length > 3? parts[3] : null;
				GazetteerDef rec = new GazetteerDef(file, minorType, majorType, annotation);
				process(rec);
				
			}
			
		} finally {
			if(br != null)
				br.close();
		}
	}
	
	public abstract void process(GazetteerDef rec);
	
	

}
