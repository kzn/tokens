package name.kazennikov.annotations.patterns;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import name.kazennikov.annotations.Annotator;
import name.kazennikov.annotations.Document;

public abstract class AbstractJapeTransducer implements Annotator {
	File fileName;
	JapeConfiguration config;
	
	List<Annotator> annotators = new ArrayList<>();
	
	public File getFileName() {
		return fileName;
	}
	public void setFileName(File fileName) {
		this.fileName = fileName;
	}
	public JapeConfiguration getConfig() {
		return config;
	}
	public void setConfig(JapeConfiguration config) {
		this.config = config;
	}
	
	public void init() {
		
		
	}
	
	
	
	
	@Override
	public boolean isApplicable(Document doc) {
		return true;
	}
	
	@Override
	public void annotate(Document doc) {
		for(Annotator a : annotators) {
			a.annotate(doc);
		}
	}
	
	
	
	
	
	
	
	

}
