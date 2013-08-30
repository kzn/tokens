package name.kazennikov.annotations.gazetteer;

import gnu.trove.list.array.TIntArrayList;

import java.util.Map;

import name.kazennikov.annotations.Document;
import name.kazennikov.dafsa.IntDAFSAInt;
import name.kazennikov.fsa.walk.WalkFSAInt;
import name.kazennikov.tools.Alphabet;

public class DAFSAGazetteer extends BaseGazetteer {
	IntDAFSAInt fsa = new IntDAFSAInt();
	Alphabet<Map<String, String>> feats = new Alphabet<>();
	WalkFSAInt walkFSA;

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
		WalkFSAInt.Builder builder = new WalkFSAInt.Builder();
		fsa.emit(builder);
		walkFSA = builder.build();
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
		
		fsa.setFinalValue(this.feats.get(feats));
		fsa.addMinWord(l);
	}

}
