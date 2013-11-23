package name.kazennikov.annotations.patterns;

import java.util.Map;

import name.kazennikov.annotations.AnnotationList;
import name.kazennikov.annotations.Document;

/**
 * Right Hand Side of a JAPE rule. 
 * Mostly for side-effect actions on the matched annotation list
 * 
 * @author Anton Kazennikov
 *
 */
public interface RHS {
	public final static EmptyRHS EMPTY = new EmptyRHS();
	
	/**
	 * Executes the actions of the matched document
	 * 
	 * @param doc processed document
	 * @param input input annotation list
	 * @param bindings found bindings
	 * 
	 * @return true on successful RHS execution
	 */
	public boolean execute(Document doc, AnnotationList input, Map<String, AnnotationList> bindings);
}
