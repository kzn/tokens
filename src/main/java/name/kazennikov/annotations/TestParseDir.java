package name.kazennikov.annotations;

import java.io.File;
import java.nio.charset.Charset;

import name.kazennikov.annotations.fsm.JapePlusFSM;
import name.kazennikov.annotations.patterns.JapeConfiguration;
import name.kazennikov.annotations.patterns.JapeNGASTParser;
import name.kazennikov.annotations.patterns.Phase;
import name.kazennikov.annotations.patterns.Rule;

import org.apache.log4j.BasicConfigurator;

import com.google.common.io.Files;

public class TestParseDir {
	public static void testParseDirs(JapeConfiguration config, File f) throws Exception {
		if(!f.exists())
			return;
		
		if(f.isFile()) {
			String s = Files.toString(f, Charset.forName("UTF-8"));
			Phase phase = JapeNGASTParser.parsePhase(config, s);


			for(Rule r : phase.getRules()) {
				JapePlusFSM.Builder fsmBuilder = new JapePlusFSM.Builder();
				fsmBuilder.addRule(r);

				fsmBuilder.toDot("test_pre.dot");
				fsmBuilder.determinize();
				fsmBuilder.toDot("test_det.dot");
				fsmBuilder.minimize();
				fsmBuilder.toDot("test_min.dot");
				fsmBuilder.toDot("test.dot");
				
				JapePlusFSM fsm1 = fsmBuilder.build();
			}

		} else if(f.isDirectory()) {
			for(File file : f.listFiles()) {
				testParseDirs(config, file);
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		JapeConfiguration config = new JapeConfiguration();
		testParseDirs(config, new File("jape/parser"));

	}

}
