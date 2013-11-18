package name.kazennikov.annotations.patterns;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;

import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.AnnotationList;

import com.google.common.io.Files;

public class JapeEngineUtils {
	
	public static Phase compilePhase(File file) throws Exception {
		String s = Files.toString(file, Charset.forName("UTF-8"));
		Phase phase = JapeNGASTParser.parse(s);
		phase.compile();
		return phase;
	}
	
	public static int[] computeFollowingAnnotationIndex(AnnotationList input) {
		int nextAnnotationIndex[] = new int[input.size()];
		
		for(int i = 0; i < input.size(); i++) {
			int index = Collections.binarySearch(input, input.get(i), new Comparator<Annotation>() {

				@Override
				public int compare(Annotation o1, Annotation o2) {
					return Integer.compare(o1.getStart(), o2.getEnd());
				}
			});
			
			if(index < 0) {
				index = -index - 1;
			}
			nextAnnotationIndex[i] = index;
		}

		return nextAnnotationIndex;
	}

	
	


}
