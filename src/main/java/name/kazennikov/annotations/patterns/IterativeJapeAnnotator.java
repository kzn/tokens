package name.kazennikov.annotations.patterns;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.array.TIntArrayList;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.AnnotationEngineException;
import name.kazennikov.annotations.AnnotationList;
import name.kazennikov.annotations.Annotator;
import name.kazennikov.annotations.Document;
import name.kazennikov.annotations.annotators.BasicTokenizer;
import name.kazennikov.annotations.fsm.JapePlusFSM;
import name.kazennikov.annotations.fsm.JapePlusFSM.State;
import name.kazennikov.annotations.fsm.JapePlusFSM.Transition;
import name.kazennikov.annotations.fsm.JapePlusFSM.TypeMatcher;
import name.kazennikov.logger.Logger;

import org.apache.log4j.BasicConfigurator;

import com.google.common.base.Predicate;

public class IterativeJapeAnnotator implements Annotator {
	private static final Logger logger = Logger.getLogger();


	public static class FSMInstance {
		List<TIntArrayList> stack;
		List<String> keys;
		List<TIntArrayList> values;
		int position = 0;
		JapePlusFSM.State state;
		Rule rule;

		public void init() {
			keys = new ArrayList<>();
			values = new ArrayList<>();
			stack = new ArrayList<>();
		}	

		public void push() {
			stack.add(new TIntArrayList());
		}

		public void addMatching(int a) {
			for(TIntArrayList l : stack) {
				l.add(a);
			}
		}

		public void pop(String groupName) {
			TIntArrayList l = stack.get(stack.size() - 1);
			stack.remove(stack.size() - 1);
			keys.add(groupName);
			values.add(l);
		}

		public static FSMInstance newInstance() {
			FSMInstance inst = new FSMInstance();
			inst.init();
			return inst;
		}
		

		public Map<String, AnnotationList> bindings(AnnotationList input) {
			Map<String, AnnotationList> map = new HashMap<String, AnnotationList>();
			for(int i = 0; i < keys.size(); i++) {
				AnnotationList l = new AnnotationList();
				TIntIterator it = values.get(i).iterator();
				while(it.hasNext()) {
					l.add(input.get(it.next()));
				}
				map.put(keys.get(i), l);
			}
			return map;
		}


	}


	public static class Matcher {
		AnnotationList input;
		int[] nextAnnotationIndex;
		Document doc;
		Phase phase;
		
		Deque<FSMInstance> activeInstances = new ArrayDeque<>();//new LinkedList<>();
		List<FSMInstance> finalInstances = new ArrayList<>();

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
			nextAnnotationIndex = computeNextAnnotationIndex(input);
		}

		public static int[] computeNextAnnotationIndex(AnnotationList input) {
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

		public void execute() {
			int index = 0;

			while(index < input.size()) {
				FSMInstance initInst = FSMInstance.newInstance();
				initInst.position = index;
				initInst.state = phase.fsm.getStart();
				activeInstances.add(initInst);
				
				while(!activeInstances.isEmpty()) {
					FSMInstance inst = activeInstances.removeFirst();
					if(tryAdvance(inst))
						break;
				}
				
				// if something has matched
				if(!finalInstances.isEmpty()) {
					index = applyRules(index);
					finalInstances.clear();
				} else {
					index = skipToNextIndex(index);
				}
				
				if(index < 0)
					break;				
			}
		}
		
		
		public int execOnce() {
			FSMInstance inst = finalInstances.get(0);
			for(RHS rhs : inst.rule.rhs()) {
				rhs.execute(doc, input, inst.bindings(input));
			}

			return -1;
		}
		
		public int execFirst() {
			FSMInstance inst = finalInstances.get(0);
			for(RHS rhs : inst.rule.rhs()) {
				rhs.execute(doc, input, inst.bindings(input));
			}

			return inst.position;
		}
		
		public int execAll(int startIndex) {
			for(int i = 0; i < finalInstances.size(); i++) {
				FSMInstance inst = finalInstances.get(i);
				for(RHS rhs : inst.rule.rhs()) {
					rhs.execute(doc, input, inst.bindings(input));
				}
			}
			
			return skipToNextIndex(startIndex);
		}
		
		public int execBrill() {
			int maxPos = Integer.MIN_VALUE;
			for(int i = 0; i < finalInstances.size(); i++) {
				FSMInstance inst = finalInstances.get(i);
				for(RHS rhs : inst.rule.rhs()) {
					rhs.execute(doc, input, inst.bindings(input));
				}

				maxPos = Math.max(maxPos, inst.position);
			}
			
			return maxPos;
		}
		
		public int execAppelt() {
			Collections.sort(finalInstances, new Comparator<FSMInstance>() {

				@Override
				public int compare(FSMInstance o1, FSMInstance o2) {
					int res = o2.position - o1.position;
					if(res != 0)
						return res;
					
					res = o2.rule.getPriority() - o1.rule.getPriority();
					if(res != 0)
						return res;
					
					res = o2.rule.number - o1.rule.number;
								
					return res;
				}
			});
			
			for(RHS rhs : finalInstances.get(0).rule.rhs()) {
				rhs.execute(doc, input, finalInstances.get(0).bindings(input));
			}

			return finalInstances.get(0).position;
		}

		
		public int applyRules(int startIndex) {
			switch(phase.mode) {
			case ONCE:
				return execOnce();
			case FIRST:
				return execFirst();
			case ALL:
				return execAll(startIndex);
			case BRILL:
				return execBrill();
			case APPELT:
				return execAppelt();
			}
			
			return -1;
		}
		
		public FSMInstance copy(FSMInstance src) {
			FSMInstance copy = new FSMInstance();

			copy.keys = new ArrayList<>(src.keys.size());
			copy.values = new ArrayList<>(src.values.size());
			copy.stack = new ArrayList<>();
			copy.position = src.position;
			copy.state = src.state;

			for(TIntArrayList l : src.stack) {
				copy.stack.add(new TIntArrayList(l));
			}

			for(int i = 0; i < src.keys.size(); i++) {
				copy.keys.add(src.keys.get(i));
				copy.values.add(new TIntArrayList(src.values.get(i)));
			}

			return copy;
		}
		
		/**
		 * Try to advance current FSMInstance
		 * @param inst instance to advance
		 * @return true, if we should stop advancing the FSM at current position
		 */
		public boolean tryAdvance(FSMInstance instance) {
			State state = instance.state;
			
			if(state.isFinal()) {
				for(Rule r : state.getRules()) {
					FSMInstance inst = copy(instance);
					inst.rule = r;	
					finalInstances.add(inst);
				}
				
				if(phase.mode == MatchMode.FIRST || phase.mode == MatchMode.ONCE)
					return true;
			}
			boolean singleTr = state.getTransitions().size() == 1;
			
			for(Transition t : state.getTransitions()) {
				int type = t.getType();
							
				if(type == JapePlusFSM.GROUP_START) {
					FSMInstance inst = singleTr? instance : copy(instance);
					inst.state = t.getDest();
					inst.push();
					activeInstances.addLast(inst);
				} else if(type < 0) { // group end
					String groupName = phase.fsm.getGroupName(-type - 1);
					FSMInstance inst = singleTr? instance : copy(instance);
					inst.pop(groupName);
					inst.state = t.getDest();
					activeInstances.addLast(inst);
				} else {
					generateMatching(t, instance, singleTr);
				}			
			}

			return false;			
		}


		public void generateMatching(Transition t, FSMInstance instance, boolean singleTr) {
			if(instance.position >= input.size())
				return;

			List<TypeMatcher> matchers = t.getMatchers();
			if(matchers.size() == 1) {
				trySingleConstraintMatch(instance, t.getDest(), matchers.get(0), singleTr);
			} else {
				int[] matchedAnnotations = new int[matchers.size()];
				tryConstraintMatch(instance, t.getDest(), matchers, 0, matchedAnnotations);
			}
		}

		
		public void trySingleConstraintMatch(FSMInstance instance, State dest, TypeMatcher typeMatcher, boolean singleTr) {
			List<AnnotationMatcher> matchers = typeMatcher.getMatchers();
			TIntArrayList flags = typeMatcher.getFlags();
			TIntArrayList matchedAnnots = new TIntArrayList();
			int startPos = input.get(instance.position).getStart();

			for(int annotIndex = instance.position; annotIndex < input.size(); annotIndex++) {
				Annotation a = input.get(annotIndex);

				if(a.getStart() != startPos)
					break;
				
				boolean res = true;

				for(int matcherIndex = 0; matcherIndex < matchers.size(); matcherIndex++) {
					AnnotationMatcher matcher = matchers.get(matcherIndex);
					int flag = flags.get(matcherIndex);
					boolean res0 = matcher.match(a);

					if(flag == 1)
						res0 = !res0;

					if(!res0) {
						res = false;
						break;
					}
				}

				if(res) {
					matchedAnnots.add(annotIndex);
				}
			}
			
			if(matchedAnnots.isEmpty())
				return;
			
			if(singleTr && matchedAnnots.size() == 1) {
				int annotIndex = matchedAnnots.get(0);
				instance.addMatching(annotIndex);
				int nextIndex = nextAnnotationIndex[annotIndex];
				
				instance.position = nextIndex;
				instance.state = dest;
				activeInstances.addLast(instance);
			} else {
				TIntIterator it = matchedAnnots.iterator();
				while(it.hasNext()) {
					int annotIndex = it.next();
					FSMInstance inst = copy(instance);
					inst.addMatching(annotIndex);
					int nextIndex = nextAnnotationIndex[annotIndex];
					
					inst.position = nextIndex;
					inst.state = dest;
					activeInstances.addLast(inst);
				}
			}
		}


		
		

		public void tryConstraintMatch(FSMInstance instance, State dest,
				List<TypeMatcher> typeMatchers, int tmIndex, int[] matched) {
			if(tmIndex == typeMatchers.size()) {
				FSMInstance inst = copy(instance);
				int nextIndex = Integer.MIN_VALUE;
			
				for(int annotIndex : matched) {
					inst.addMatching(annotIndex);
					nextIndex = Math.max(nextIndex, nextAnnotationIndex[annotIndex]);
				}
				
				inst.position = nextIndex;
				inst.state = dest;
				activeInstances.addLast(inst);
				return;
			} else {
				TypeMatcher m = typeMatchers.get(tmIndex);
				List<AnnotationMatcher> matchers = m.getMatchers();
				TIntArrayList flags = m.getFlags();
				
				int startPos = input.get(instance.position).getStart();

				for(int annotIndex = instance.position; annotIndex < input.size(); annotIndex++) {
					Annotation a = input.get(annotIndex);
					
					if(a.getStart() != startPos)
						break;
					boolean res = true;
					
					for(int matcherIndex = 0; matcherIndex < matchers.size(); matcherIndex++) {
						AnnotationMatcher matcher = matchers.get(matcherIndex);
						int flag = flags.get(matcherIndex);
						boolean res0 = matcher.match(a);

						if(flag == 1)
							res0 = !res0;
						
						if(!res0) {
							res = false;
							break;
						}
					}
					
					if(res) {
						matched[tmIndex] = annotIndex;
						tryConstraintMatch(instance, dest, typeMatchers, tmIndex + 1, matched);
					}

				}
			}
		}


		public int skipToNextIndex(int index) {
			if(index >= input.size())
				return index;
			return nextAnnotationIndex[index];
		}
	}

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		Phase fsm = JapeEngineUtils.compilePhase(new File("jape/parser/4.jape"));
		BasicTokenizer t = new BasicTokenizer();
		t.setSeparator(",.!?()[]\"'$%^&*#{}\\|/-");
		Document d = new Document("doc", "this--is table.");
		t.annotate(d);
		Matcher m = new Matcher(d, fsm);
		m.execute();

		System.out.printf("Done%n");
	}
	
	File japeFile;
	Phase phase;
	
	public File getJapeFile() {
		return japeFile;
	}

	public void setJapeFile(File japeFile) {
		this.japeFile = japeFile;
	}

	
	public void init() {
		try {
			phase = JapeEngineUtils.compilePhase(japeFile);
		} catch(Exception e) {
			logger.warn(e);
			throw new AnnotationEngineException(e);
		}
	}
	
	
	

	@Override
	public boolean isApplicable(Document doc) {
		return true;
	}

	@Override
	public void annotate(Document doc) {
		Matcher m = new Matcher(doc, phase);
		m.execute();
		
	}



}
