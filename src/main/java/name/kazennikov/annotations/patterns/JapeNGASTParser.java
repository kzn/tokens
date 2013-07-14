package name.kazennikov.annotations.patterns;

import java.util.ArrayList;
import java.util.List;

import name.kazennikov.annotations.patterns.PatternElement.Operator;

import org.antlr.runtime.tree.Tree;

public class JapeNGASTParser {
	public Phase parse(Tree tree) {
		Phase phase = new Phase();
		
		for(int i = 0; i < tree.getChildCount(); i++) {
			Tree child = tree.getChild(i);
			
			String val = child.getText();
			
			switch(val) {
			case "PHASE":
				assert child.getChildCount() == 1;
				phase.name = child.getChild(0).getText();
				break;
			case "INPUT":
				phase.input.clear();
				for(int j = 0; j < child.getChildCount(); j++) {
					phase.input.add(child.getChild(j).getText());
				}
				break;
				
			case "OPTIONS":
				parseOptions(phase, child);
				break;
				
			case "RULE":
				phase.rules.add(parseRule(child));
				break;
			}


			
		}
		
		
		return phase;
	}

	private Rule parseRule(Tree r) {
		Rule rule = new Rule();
		
		for(int i = 0; i < r.getChildCount(); i++) {
			Tree child = r.getChild(i);
			String val = child.getText();
			
			switch(val) {
			case "NAME":
				assert child.getChildCount() == 1;
				rule.name = child.getChild(0).getText();
				break;
			case "PRIORITY":
				assert child.getChildCount() == 1;
				rule.priority = Integer.parseInt(child.getChild(0).getText());
				break;
				
			case "GROUP_MATCHER":
				rule.lhs.add(parsePatternElement(child));
				
			}
			
		}

		return rule;
	}

	private PatternElement parsePatternElement(Tree bpe) {
		String name = null;
		PatternElement.Operator op = bpe.getText().equals("OR")? Operator.OR : Operator.SEQ;
		RangePatternElement enclosing = null;
		
		List<PatternElement> args = new ArrayList<>();
		
		for(int i = 0; i < bpe.getChildCount(); i++) {
			Tree child = bpe.getChild(i);
			switch(child.getText()) {
			
			case "GROUP_OP":
				switch(child.getChild(0).getText()) {
				case "named":
					name = child.getChild(1).getText();
					break;
				case "?":
					enclosing = new RangePatternElement();
					enclosing.min = 0;
					enclosing.max = 1;
					break;
				case "*":
					enclosing = new RangePatternElement();
					enclosing.min = 0;
					enclosing.max = RangePatternElement.INFINITE;
					break;
				case "+":
					enclosing = new RangePatternElement();
					enclosing.min = 1;
					enclosing.max = RangePatternElement.INFINITE;
					break;
				case "range":
					enclosing = new RangePatternElement();
					enclosing.min = Math.max(0, Integer.parseInt(child.getChild(1).getText()));
					if(child.getChildCount() > 2) {
						enclosing.max = Integer.parseInt(child.getChild(2).getText());
					} else {
						enclosing.max = RangePatternElement.INFINITE;
					}
					break;
				}
				break;
				

			case "OR":
			case "GROUP_MATCHER":
				args.add(parsePatternElement(child));
				break;
			
			case "ANNOT":
				args.add(new AnnotationMatcherPatternElement(parseAnnot(child)));
				break;
			
			}
		}
		
		PatternElement pe = args.size() == 1 && name == null? args.get(0) : new BasePatternElement(name, op, args);
		if(enclosing != null) {
			enclosing.element = pe;
			return enclosing;
		}
			
		return pe;
	}

	private AnnotationMatcher parseAnnot(Tree annTree) {

		List<AnnotationMatcher> matchers = new ArrayList<>();
		for(int i = 0; i < annTree.getChildCount(); i++) {
			Tree child = annTree.getChild(i);
			switch(child.getText()) {
			
			case "NOT":
				matchers.add(parseAnnot(child).complement());
				break;
			
			case "AN_TYPE":
				assert child.getChildCount() == 1;
				matchers.add(new AnnotationMatchers.TypeMatcher(child.getChild(0).getText()));
				break;
			case "AN_FEAT":
				matchers.add(parseAnFeat(child));
				break;
				

			}
		}




		return matchers.size() == 1? matchers.get(0) : new AnnotationMatchers.ANDMatcher(matchers);

	}

	private AnnotationMatcher parseAnFeat(Tree feats) {
		String op = feats.getChild(0).getText();
		String val = feats.getChild(1).getText();
		String type = feats.getChild(2).getText();
		String feat = feats.getChild(3).getText();
		switch(op) {
		case "eq":
			return new AnnotationMatchers.FeatureEqMatcher(type, feat, val);
		case "neq":
			return new AnnotationMatchers.FeatureEqMatcher(type, feat, val).complement();
		}
		throw new IllegalStateException("illegal annotation type feature operation " + op);
	}

	private void parseOptions(Phase phase, Tree options) {
		for(int i = 0; i < options.getChildCount(); i++) {
			Tree child = options.getChild(i);
			
			String val = child.getText();
			if(val.equals("control")) {
				assert child.getChildCount() == 1;
				String ctrl = child.getChild(0).getText().toUpperCase();
				phase.mode = MatchMode.valueOf(ctrl);
				
			}
			
		}
	}
	


}
