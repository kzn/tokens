package name.kazennikov.annotations.patterns;

import java.util.ArrayList;
import java.util.List;

import name.kazennikov.annotations.JapeNGLexer;
import name.kazennikov.annotations.JapeNGParser;
import name.kazennikov.annotations.patterns.PatternElement.Operator;
import name.kazennikov.annotations.patterns.SimpleRHS.Value;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;


public class JapeNGASTParser {
	String src;
	CharStream charStream;
	JapeNGLexer lexer;
	CommonTokenStream tokenStream;
	JapeNGParser parser;
	CommonTree tree;
	
	public static Phase parse(String source) throws Exception {
		JapeNGASTParser parser = new JapeNGASTParser(source);
		return parser.parse();
	}
	
	private JapeNGASTParser(String source) throws RecognitionException {
		this.src = source;
		charStream = new ANTLRStringStream(src);
		lexer = new JapeNGLexer(charStream);
		tokenStream = new CommonTokenStream(lexer);
		parser = new JapeNGParser(tokenStream);
		tree = (CommonTree) parser.jape().getTree();
		System.out.printf("%n%s%n%n", tree.toStringTree());
	}
	
	
	private Phase parse() throws Exception {
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

	private Rule parseRule(Tree r) throws Exception {
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
				break;
			case "EMPTY_RHS":
				rule.rhs.add(RHS.EMPTY);
				break;
			case "JAVA":
				rule.rhs.add(parseJavaRHS(child));
				break;
			case "SIMPLE_RHS":
				rule.rhs.add(parseSimpleRHS(child));
				break;
				
				
			}
			
		}

		return rule;
	}

	private RHS parseSimpleRHS(Tree rhsTree) {
		SimpleRHS rhs = new SimpleRHS();
		
		for(int i = 0; i < rhsTree.getChildCount(); i++) {
			Tree child = rhsTree.getChild(i);
			switch(child.getText()) {
			case "NAME":
				parseRHSName(child, rhs);
				break;
			case "ATTR": 
				rhs.values.add(parseAttr(child));
			}
		}
		

		return rhs;
	}

	private Value parseAttr(Tree attr) {
		String name = parseVal(attr.getChild(0)).toString();
		Tree value = attr.getChild(1);
		
		switch(value.getText()) {
		case "VAL":
			return new SimpleRHS.SimpleValue(name, parseVal(value.getChild(0)));
		case "REF_VAL":
			return new SimpleRHS.BindingValue(name, 
					value.getChild(0).getText(), 
					value.getChild(1).getText(), 
					value.getChild(2).getText());
		}

		return null;
	}

	private void parseRHSName(Tree child, SimpleRHS rhs) {
		String group = child.getChild(0).getText();
		String type = parseVal(child.getChild(1)).toString();
		rhs.bindingName = group;
		rhs.type = type;
	}

	private RHS parseJavaRHS(Tree child) throws Exception {
		List<CommonToken> tokens = tokenStream.get(child.getTokenStartIndex(), child.getTokenStopIndex());
		String s = src.substring(tokens.get(0).getStartIndex(), tokens.get(tokens.size() - 1).getStopIndex() + 1);
		
		
		

		return JavaRHSBuilder.build("japeng", null, s);
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
				matchers.add(new AnnotationMatchers.NegativeAnnotationMatcher(parseAnnot(child)));
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
	
	private Object parseVal(Tree val) {
		assert val.getChildCount() == 1;
		String str = val.getText();
		
		switch(val.getText()) {
		case "IDENT":
			return val.getChild(0).getText();
		case "STRING":
			StringBuilder sb = new StringBuilder();
			String s = val.getChild(0).getText();
			for(int i = 1; i < s.length() - 1; i++) {
				char ch = s.charAt(i);
				if(ch == '\\') {
					i++;
					ch = s.charAt(i);
					switch(ch) {
					case 'n':
						sb.append('\n');
						break;
					case '\t':
						sb.append('\t');
					default:
						sb.append(ch);
					}
				} else {
					sb.append(ch);
				}
			}
			return sb.toString();
		case "INTEGER":
			return Integer.parseInt(str);
			
		case "FLOAT":
			if(str.endsWith("f") ||str.endsWith("F"))
				return Float.parseFloat(str);
			return Double.parseDouble(str);
		}
		return val.getText();
	}

	private AnnotationMatcher parseAnFeat(Tree feats) {
		String op = feats.getChild(0).getText();
		Object val = parseVal(feats.getChild(1));
		String type = feats.getChild(2).getText();
		String feat = feats.getChild(3).getText();
		switch(op) {
		case "eq":
			return new AnnotationMatchers.FeatureEqMatcher(type, feat, val);
		case "neq":
			return new AnnotationMatchers.FeatureNEqMatcher(type, feat, val);
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
