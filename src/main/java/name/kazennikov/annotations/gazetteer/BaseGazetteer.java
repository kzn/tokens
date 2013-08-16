package name.kazennikov.annotations.gazetteer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import name.kazennikov.annotations.Annotator;

/**
 * Base gazetteer class. Reads and parses gazetteers in GATE file format:
 * <br>
 * There is a root definition line-based file with format: <b>[fileName]:[majorType]:[minorType]:[annotation]</b>
 * <br>
 * The gazetteer entry file has following format: <b>[entry]([sep][feature name]=[featureValue])*</b>
 * 
 * @author Anton Kazennikov
 *
 */
public abstract class BaseGazetteer implements Annotator {
	File rootFile;
	
	char featSep = '#';
	
	public void setRootFile(File rootFile) {
		this.rootFile = rootFile;
	}
	
	public void setFeatSep(char sep) {
		this.featSep = sep;
	}
	
	public void init() throws Exception {
		if(rootFile == null)
			throw new IllegalStateException("root file not set");
		
		if(!rootFile.exists())
			throw new IllegalStateException("root file doesn't exist");
		
		processRootFile();
	}
	
	protected void processRootFile() throws IOException {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(rootFile), Charset.forName("UTF-8")));

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
				
				File file = new File(rootFile.getParent(), parts[0]);
				String majorType = parts.length > 1? parts[1] : null;
				String minorType = parts.length > 2? parts[2] : null;
				String annotation = parts.length > 3? parts[3] : null;
				
				processGazetteerFile(file, majorType, minorType, annotation);
			}

		} finally {
			if(br != null)
				br.close();
		}
	}
	
	protected void processGazetteerFile(File file, String majorType, String minorType, String annotation) throws IOException {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(rootFile), Charset.forName("UTF-8")));
			int line = 0;
			while(true) {
				String s = br.readLine();
				if(s == null)
					break;
				line++;
				s = s.trim();

				if(s.isEmpty())
					continue;

				String[] parts = s.trim().split("" + featSep);
				
				Map<String, String> feats = new HashMap<>();
				String entry = parts[0];
				
				for(int i = 1; i < parts.length; i++) {
					int eqOffset = parts[i].indexOf('=');
					if(eqOffset == -1) {
						throw new IllegalStateException(String.format("illegal feature syntax at line=%d file=%s", line, file));
					}
					
					String name = parts[i].substring(0, eqOffset);
					String value = parts[i].substring(eqOffset + 1);
					feats.put(name.trim(), value.trim());
				}

				processEntry(entry, feats, majorType, minorType, annotation);

			}

		} finally {
			if(br != null)
				br.close();
		}
	}

	protected abstract void processEntry(String entry, Map<String, String> feats, 
										 String majorType, String minorType, String annotation);
}
