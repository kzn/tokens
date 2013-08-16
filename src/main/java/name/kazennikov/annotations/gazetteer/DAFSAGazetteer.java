package name.kazennikov.annotations.gazetteer;

import gnu.trove.list.array.TIntArrayList;

import java.util.Map;

import name.kazennikov.annotations.Document;
import name.kazennikov.dafsa.CharTrie;
import name.kazennikov.dafsa.IntFSA;
import name.kazennikov.dafsa.IntTrie;
import name.kazennikov.dafsa.Nodes;
import name.kazennikov.tools.Alphabet;

public class DAFSAGazetteer extends BaseGazetteer {
	IntFSA fsa = new IntFSA.Simple(new Nodes.IntSimpleNode());
	CharTrie trie;
	Alphabet<Map<String, String>> feats = new Alphabet<>();
	

	@Override
	public boolean isApplicable(Document doc) {
		return doc.contains("Token");
	}

	@Override
	public void annotate(Document doc) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public void init() throws Exception {
		super.init();
		IntTrie.SimpleBuilder<CharTrie> b = new IntTrie.SimpleBuilder<>(new CharTrie());
		fsa.write(b);
		trie = b.build();
		fsa = null;
	}

	@Override
	protected void processEntry(String entry, Map<String, String> feats, String majorType,
			String minorType, String annotation) {
		
		if(majorType != null)
			feats.put("majorType", majorType);
		
		if(minorType != null)
			feats.put("minorType", minorType);
		
		TIntArrayList l = new TIntArrayList();
		entry = entry.trim().toLowerCase();
		for(int i = 0; i < entry.length(); i++) {
			l.add(entry.charAt(i));
		}		
	
		fsa.addMinWord(l, this.feats.get(feats));
	}

}
