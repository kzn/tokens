package name.kazennikov.annotations.gazetteer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.google.common.base.Objects;

public class GazetteerListReader {
	public static class GazetterRecord {
		File file;
		
		String minorType;
		String majorType;
		String annotation;
		public GazetterRecord(File file, String minorType, String majorType, String annotation) {
			super();
			this.file = file;
			this.minorType = minorType;
			this.majorType = majorType;
			this.annotation = annotation;
		}
		
		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("file", file)
					.add("minorType", minorType)
					.add("majorType", majorType)
					.add("annotation", annotation)
					.toString();
		}
		
		
	}
	
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
				
				
				if(parts.length < 2)
					throw new IllegalStateException("Incorrect number of fields in line " + s);
				File file = new File(f.getParent(), parts[0]);
				String majorType = parts.length > 1? parts[1] : null;
				String minorType = parts.length > 2? parts[2] : null;
				String annotation = parts.length > 3? parts[3] : null;
				GazetterRecord rec = new GazetterRecord(file, minorType, majorType, annotation);
				
				
			}
			
		} finally {
			if(br != null)
				br.close();
		}
	}

}
