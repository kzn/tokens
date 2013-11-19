package name.kazennikov.annotations.patterns;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import name.kazennikov.annotations.AnnotationEngineException;
import name.kazennikov.annotations.Annotator;
import name.kazennikov.annotations.AnnotatorSequence;

import org.antlr.runtime.RecognitionException;

import com.google.common.io.Files;

public class MultiPhaseJapeASTParser {
	
	public static class MultiPhaseDecl {
		String name;
		List<String> phases = new ArrayList<>();
	}
	
	protected class MultiPhaseASTParser extends SinglePhaseJapeASTParser {
		
		protected MultiPhaseASTParser(JapeConfiguration config, File file)
				throws RecognitionException, IOException {
			super(config, Files.toString(file, Charset.forName("UTF-8")));
		}
		
		MultiPhaseDecl parseMultiPhase() {
			if(!getType().equals("MULTIPHASE"))
				return null;
			
			MultiPhaseDecl decl = new MultiPhaseDecl();
			decl.name = getName();
			for(int i = 0; i < tree.getChild(1).getChildCount(); i++) {
				decl.phases.add(tree.getChild(1).getChild(i).getText());
			}
			
			return decl;
		}
	}
	
	
	JapeConfiguration config;
	File file;
	Class<? extends AbstractPhaseAnnotator> phaseAnnotatorClass;
	
	public Class<? extends AbstractPhaseAnnotator> getPhaseAnnotatorClass() {
		return phaseAnnotatorClass;
	}

	public void setPhaseAnnotatorClass(Class<? extends AbstractPhaseAnnotator> phaseAnnotatorClass) {
		this.phaseAnnotatorClass = phaseAnnotatorClass;
	}

	public JapeConfiguration getConfig() {
		return config;
	}

	public void setConfig(JapeConfiguration config) {
		this.config = config;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	

	public Annotator makePhaseAnnotator(Phase p) throws Exception {
		AbstractPhaseAnnotator a = phaseAnnotatorClass.newInstance();
		a.setPhase(p);
		a.init();
		return a;
	}
	
	public Annotator init() throws Exception {
		MultiPhaseASTParser parser = new MultiPhaseASTParser(config, file);
		
		String type = parser.getType();
		if(type.equals("PHASE")) {
			Phase p = parser.parsePhase();
			return makePhaseAnnotator(p);
		} else if(type.equals("MULTIPHASE")) {
			return makeMultiPhaseAnnotator(parser.parseMultiPhase());
		}
		
		throw new AnnotationEngineException("undefined grammar type");
	}
	
	public AnnotatorSequence makeMultiPhaseAnnotator(MultiPhaseDecl decl) throws Exception {
		AnnotatorSequence seq = new AnnotatorSequence();
		seq.setName(decl.name);
		
		for(String s : decl.phases) {
			MultiPhaseJapeASTParser parser = new MultiPhaseJapeASTParser();
			parser.config = config;
			parser.file = new File(this.file.getParent(), s + ".jape");
			parser.setPhaseAnnotatorClass(phaseAnnotatorClass);
			seq.add(parser.init());
		}
		
		return seq;
	}
	
	
	

}
