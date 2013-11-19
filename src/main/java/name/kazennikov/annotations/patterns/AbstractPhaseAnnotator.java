package name.kazennikov.annotations.patterns;

import java.io.File;
import java.nio.charset.Charset;

import name.kazennikov.annotations.Annotator;
import name.kazennikov.annotations.Document;

import com.google.common.io.Files;

public abstract class AbstractPhaseAnnotator implements Annotator {
	protected Phase phase;

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}
	
	public void init() {
		if(!phase.isCompiled())
			phase.compile();
	}
	
	@Override
	public String getName() {
		return phase.name;
	}
	
	@Override
	public boolean isApplicable(Document doc) {
		return true;
	}
	
	
	public static <E extends AbstractPhaseAnnotator> E newAnnotator(JapeConfiguration config, File file, 
			Class<E> cls) throws Exception {
		E a = cls.newInstance();
		String source = Files.toString(file, Charset.forName("UTF-8"));
		Phase p = SinglePhaseJapeASTParser.parsePhase(config, source);
		a.setPhase(p);
		a.init();
		return a;
	}



}
