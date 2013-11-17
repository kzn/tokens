package name.kazennikov.annotations.patterns;

import gnu.trove.list.array.TIntArrayList;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.AnnotationList;
import name.kazennikov.annotations.Document;
import name.kazennikov.annotations.fsm.JapePlusFSM;
import name.kazennikov.annotations.fsm.JapePlusFSM.State;
import name.kazennikov.annotations.fsm.JapePlusFSM.Transition;
import name.kazennikov.annotations.fsm.JapePlusFSM.TypeMatcher;

import org.apache.log4j.BasicConfigurator;

import com.google.common.base.Predicate;
import com.google.common.io.Files;

public class ParserSandbox {
	public static Phase compileFSM(File file) throws Exception {
		String s = Files.toString(file, Charset.forName("UTF-8"));
		Phase phase = JapeNGASTParser.parse(s);
		phase.compile();

		return phase;

	}

	public static class FSMInstance {
		Map<String, AnnotationList> bindings;
		List<AnnotationList> stack;

		public void init() {
			bindings = new HashMap<>();
			stack = new ArrayList<>();
		}

		public FSMInstance copy() {
			FSMInstance copy = new FSMInstance();
			copy.bindings = new HashMap<String, AnnotationList>();
			copy.stack = new ArrayList<>();

			for(AnnotationList l : stack) {
				copy.stack.add(l.copy());
			}

			for(Map.Entry<String, AnnotationList> e : bindings.entrySet()) {
				copy.bindings.put(e.getKey(), e.getValue().copy());
			}


			return copy;
		}

		public void push() {
			stack.add(new AnnotationList());
		}

		public void addMatching(Annotation a) {
			for(AnnotationList l : stack) {
				l.add(a);
			}
		}

		public void pop(String groupName) {
			AnnotationList l = stack.get(stack.size() - 1);
			stack.remove(stack.size() - 1);
			bindings.put(groupName, l);
		}

		public static FSMInstance newInstance() {
			FSMInstance inst = new FSMInstance();
			inst.bindings = new HashMap<>();
			inst.stack = new ArrayList<>();
			return inst;
		}


	}


	public static class Matcher {
		AnnotationList input;
		Document doc;
		Phase phase;
		
		List<FSMInstance> instances = new ArrayList<>();
		List<Set<Rule>> rules = new ArrayList<>();

		public Matcher(Document doc, final Phase phase) {
			this.phase = phase;
			this.doc = doc;
			input = doc.get(new Predicate<Annotation>() {

				@Override
				public boolean apply(Annotation input) {
					return phase.input.contains(input.getType());
				}

			}); 

			input.sort();
		}

		public void execute() {
			int index = 0;

			State state = phase.fsm.getStart();

			while(index < input.size()) {
				
				tryExecute(index, state, FSMInstance.newInstance());
				index = skipToNextIndex(index);
			}
		}
		


		/**
		 * Попробовать отматчить fsm начиная с данной позиции
		 * @param index
		 * @return true, if we need to continue
		 */
		public boolean tryExecute(int index, State state, FSMInstance instance) {
			if(state.isFinal()) {
				instances.add(instance);
				rules.add(state.getRules());
				if(phase.mode == MatchMode.FIRST || phase.mode == MatchMode.ONCE)
					return false;

			}
			
			if(index >= input.size())
				return true;

			int nextIndex = skipToNextIndex(index);

			for(Transition t : state.getTransitions()) {
				int type = t.getType();
				if(type == JapePlusFSM.GROUP_START) {
					FSMInstance inst = instance.copy();
					inst.push();
					boolean res = tryExecute(index, t.getDest(), instance.copy());
					if(!res)
						return res;
				} else if(type < 0) { // group end
					String groupName = phase.fsm.getGroupName(-type);
					FSMInstance inst = instance.copy();
					inst.pop(groupName);
				} else {
					boolean res = tryMatch(index, nextIndex, t, instance);
					if(!res)
						return res;

				}
			}

			return true;
		}

		public boolean tryMatch(int startIndex, int endIndex, Transition t, FSMInstance instance) {
			List<TypeMatcher> matchers = t.getMatchers();
			Annotation[] matchedAnnotations = new Annotation[matchers.size()];
			return trySingleMatch(startIndex, endIndex, matchers, 0, matchedAnnotations, t.getDest(), instance);
		}

		public boolean trySingleMatch(int startIndex, int endIndex, 
				List<TypeMatcher> typeMatchers, int tmIndex, Annotation[] matched,
				State dest, FSMInstance instance) {

			if(tmIndex == typeMatchers.size()) {
				FSMInstance inst = instance.copy();
				for(Annotation a : matched) {
					inst.addMatching(a);
				}

				return tryExecute(endIndex, dest, instance);
			} else {
				TypeMatcher m = typeMatchers.get(tmIndex);
				TIntArrayList matchersIndex = m.getMatchers();
				TIntArrayList flags = m.getFlags();

				for(int annotIndex = startIndex; annotIndex < endIndex; annotIndex++) {
					Annotation a = input.get(annotIndex);

					for(int matcherIndex = 0; matcherIndex < matchersIndex.size(); matcherIndex++) {
						AnnotationMatcher matcher = phase.fsm.getMatcher(matchersIndex.get(matcherIndex));
						int flag = flags.get(matcherIndex);
						boolean res = matcher.match(a);

						if(flag == 1)
							res = !res;

						if(res) {
							matched[tmIndex] = a;
							trySingleMatch(startIndex, endIndex, typeMatchers, tmIndex + 1, matched, dest, instance);
						}
					}
				}				
			}
			
			return true;
		}


		public int skipToNextIndex(int index) {
			int pos = input.get(index).getStart();
			while(++index < input.size()) {
				if(input.get(index).getStart() != pos)
					return index;
			}

			return input.size();
		}


	}

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		Phase fsm = compileFSM(new File("jape/parser/4.jape"));
		Document d = new Document("doc", "this-is table.");
		/*Matcher m = new Matcher(d, fsm);
		m.execute();*/

		System.out.printf("Done%n");
	}

}
