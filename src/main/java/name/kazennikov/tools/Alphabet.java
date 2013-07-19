package name.kazennikov.tools;

import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.ArrayList;
import java.util.List;

public class Alphabet<E> {
	TObjectIntHashMap<E> m = new TObjectIntHashMap<>();
	List<E> l = new ArrayList<>();
	boolean readOnly;
	
	public int get(E key) {
		int v = m.get(key);	
		if(v == 0 && !readOnly) {
			v = m.size() + 1;
			m.put(key, v);
			l.add(key);
		}
		
		return v;
	}
	
	public E get(int key) {
		if(key > 0 && key <= l.size())
			return l.get(key - 1);
		
		return null;
	}
	
	public int size() {
		return m.size();
	}
	

}
