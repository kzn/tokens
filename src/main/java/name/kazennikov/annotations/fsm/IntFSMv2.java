package name.kazennikov.annotations.fsm;

import gnu.trove.list.array.TIntArrayList;

import java.util.BitSet;

public class IntFSMv2 {
	public static final int EPSILON_LABEL = 0;
	
	TIntArrayList src = new TIntArrayList();
	TIntArrayList dest = new TIntArrayList();
	TIntArrayList labels = new TIntArrayList();

	BitSet finals;
	int lastState = 0;
	int start = 0;
	
	public IntFSMv2() {
		addState();
	}
	
	public int addState() {
		return lastState++;
	}
	
	public int addTransition(int from, int label, int to) {
		this.src.add(from);
		this.dest.add(to);
		this.labels.add(label);
		
		return src.size();
	}
	
	public boolean isFinal(int state) {
		return finals.get(state);
	}
	
	public void setFinal(int state) {
		finals.set(state);
	}
	
	public boolean isEpsilon(int transition) {
		return labels.get(transition) == EPSILON_LABEL;
	}
	
	/**
	 * Reverse direction of the transitions
	 */
	public void reverse() {
		for(int i = 0; i < src.size(); i++) {
			int temp = src.get(i);
			src.set(i, dest.get(i));
			dest.set(i, temp);
		}
	}
	
	/**
	 * Comparse two transitions
	 * Returns -1 if i < j, 0, if i = j, and 1 if i > j
	 * @param i trans
	 * @param j
	 * @return
	 */
	public int compareTransitions(int i, int j) {
		int res = Integer.compare(src.get(i), src.get(j));
		if(res != 0)
			return res;
		
		res = Integer.compare(labels.get(i), labels.get(j));
		if(res != 0)
			return res;
		
		return Integer.compare(dest.get(i), dest.get(j));
	}
	
	public void swapTransitions(int i, int j) {
		int temp = src.get(i);
		src.set(i, src.get(j));
		src.set(j, temp);
		
		temp = labels.get(i);
		labels.set(i, labels.get(j));
		labels.set(j, temp);
		
		temp = dest.get(i);
		dest.set(i, dest.get(j));
		dest.set(j, temp);
	}
	
	/**
	 * Sort transitions by:
	 * <ul>
	 * <li> source state
	 * <li> label
	 * <li> destination state
	 * </ul>
	 */
	public void sortTransitions() {
		for(int i = 0; i < src.size(); i++) {
			for(int j = i + 1; j < src.size(); i++) {
				if(compareTransitions(i, j) < 0)
					swapTransitions(i, j);
			}
		}
	}
	
	
//	public TIntSet lambdaClosure(TIntSet states) {
//		//LinkedList<State> list = new LinkedList<State>(states);
//		TIntLinkedList list = new TIntLinkedList();
//		TIntSet closure = new TIntHashSet();
//		list.addAll(states);
//
//		while(!list.isEmpty()) {
//			int current = list.removeAt(0);
//			for(Transition t : current.transitions) {
//				if(t.matcher == null) {
//					State target = t.target;
//					if(!closure.contains(target)) {
//						closure.add(target);
//						list.addFirst(target);
//					}
//				}
//			}
//		}
//		return closure;
//	}
//	


	
	
	
	
	
	

}
