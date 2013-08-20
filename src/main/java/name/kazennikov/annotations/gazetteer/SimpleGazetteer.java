package name.kazennikov.annotations.gazetteer;

import gnu.trove.list.array.TIntArrayList;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import name.kazennikov.dafsa.IntDAFSAObject;

public class SimpleGazetteer extends AbstractSimpleGazetteer {
	int count;
	
	IntDAFSAObject<Feats> trieFeats = new IntDAFSAObject<>();
	TIntArrayList temp = new TIntArrayList();
	
	public static class Feats {
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((feats == null) ? 0 : feats.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Feats))
				return false;
			Feats other = (Feats) obj;
			if (feats == null) {
				if (other.feats != null)
					return false;
			} else if (!feats.equals(other.feats))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}
		
		String type;
		Map<String, Object> feats = new HashMap<>();
		
		
	}
	
	public static void expand(TIntArrayList l, String s) {
		l.clear();
		for(int i = 0; i < s.length(); i++) {
			l.add(s.charAt(i));
		}
	}
	@Override
	public void processEntry(GazetteerEntry entry) {
		count++;
		Feats f = new Feats();
		f.type = "Lookup";
		
		if(entry.def.annotation != null)
			f.type = entry.def.annotation;
		if(entry.def.majorType != null)
			f.feats.put("majorType", entry.def.majorType);
		
		if(entry.def.minorType != null)
			f.feats.put("majorType", entry.def.minorType);
		
		for(int i = 0; i < entry.featNames.size(); i++) {
			f.feats.put(entry.featNames.get(i), entry.featValues.get(i));
			
		}
		
		
		
		//trie.setFinalValue(true);
		trieFeats.setFinalValue(f);
		expand(temp, entry.entry.toLowerCase());
		//trie.addMinWord(temp);
		trieFeats.addMinWord(temp);
	}
	
	public static void main(String[] args) throws IOException {
		for(int i = 0; i < 10; i++) {
			SimpleGazetteer g = new SimpleGazetteer();
			g.setSep('#');
			g.setDefFile(new File("data/gazetteer/lists.def"));
			long st = System.currentTimeMillis();
			g.read();
			System.out.printf("Entries: %d%n", g.count);
			System.out.printf("FSM1 size: %d%n", g.trieFeats.size());
			System.out.printf("Elapsed: %dms%n", System.currentTimeMillis() - st);
		}
	}

}
