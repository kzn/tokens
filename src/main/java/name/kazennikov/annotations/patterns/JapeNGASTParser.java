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
		PatternElement.Operator op = Operator.SEQ;
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
					op = Operator.OR;
					break;
				case "*":
					op = Operator.STAR;
				case "+":
					op = Operator.NONGREEDY_PLUS;
				}
				
			
			
			}
		}
		
		
		
		

		return new BasePatternElement(name, op, args);
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
