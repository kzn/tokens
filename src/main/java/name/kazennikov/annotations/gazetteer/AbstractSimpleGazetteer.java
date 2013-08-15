package name.kazennikov.annotations.gazetteer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import name.kazennikov.logger.Logger;

public abstract class AbstractSimpleGazetteer {

	public File getDefFile() {
		return defFile;
	}

	public void setDefFile(File defFile) {
		this.defFile = defFile;
	}



	private static final Logger logger = Logger.getLogger();
	
	protected class GazetteerListReader extends AbstractGazetteerListReader {

		@Override
		public void process(GazetteerDef rec) {
			GazetteerReader r = new GazetteerReader(rec, sep);
			try {
				r.read();
			} catch(IOException e) {
				logger.warn(e);
			}
		}

	}
	
	protected class GazetteerReader extends AbstractGazetteerReader {

		public GazetteerReader(GazetteerDef rec, char sep) {
			super(rec, sep);
		}

		@Override
		public void process(String entry, List<String> featNames, List<String> featValues) {
			processEntry(new GazetteerEntry(entry, rec, featNames, featValues));
		}
		
	}
	
	
	char sep;
	File defFile;
	GazetteerListReader gazetteerListReader = new GazetteerListReader();

	
	
	public abstract void processEntry(GazetteerEntry entry);
	
	public char getSep() {
		return sep;
	}
	
	public void read() throws IOException {
		gazetteerListReader.read(defFile);
	}



	public void setSep(char sep) {
		this.sep = sep;
	}

	
	

}
