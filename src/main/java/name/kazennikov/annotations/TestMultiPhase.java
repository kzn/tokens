package name.kazennikov.annotations;


import java.io.File;

import name.kazennikov.annotations.patterns.IterativeJapeAnnotator;
import name.kazennikov.annotations.patterns.JapeConfiguration;
import name.kazennikov.annotations.patterns.MultiPhaseJapeASTParser;
import name.kazennikov.annotations.patterns.Phase;


public class TestMultiPhase {
	public static void main(String[] args) throws Exception {
		JapeConfiguration conf = new JapeConfiguration();
		MultiPhaseJapeASTParser parser = new MultiPhaseJapeASTParser() {

			@Override
			public Annotator makePhaseAnnotator(Phase p) {
				IterativeJapeAnnotator a = new IterativeJapeAnnotator();
				a.setPhase(p);
				a.init();
				return a;
			}
			
		};
		parser.setConfig(conf);
		parser.setPhaseAnnotatorClass(IterativeJapeAnnotator.class);
		parser.setFile(new File("jape/multiphase/main.jape"));
		
		Annotator a = parser.init();
	}

}
