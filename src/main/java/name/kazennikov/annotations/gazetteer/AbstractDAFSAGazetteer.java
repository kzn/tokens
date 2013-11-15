package name.kazennikov.annotations.gazetteer;

import gnu.trove.list.array.TIntArrayList;

import java.util.HashMap;
import java.util.Map;

import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.Document;
import name.kazennikov.dafsa.IntDAFSAInt;
import name.kazennikov.fsa.walk.WalkFSAInt;
import name.kazennikov.tools.Alphabet;

public abstract class AbstractDAFSAGazetteer extends BaseGazetteer {
	
	protected IntDAFSAInt fsa = new IntDAFSAInt();
	protected Alphabet<Map<String, String>> feats = new Alphabet<>();
	protected WalkFSAInt walkFSA;
	
	protected boolean caseSensitive;
	protected boolean longestMatchOnly = true;


	@Override
	public boolean isApplicable(Document doc) {
		return doc.contains(Annotation.TOKEN);
	}
	
	protected boolean isWhitespace(char ch) {
		return Character.isSpaceChar(ch) || Character.isWhitespace(ch);
	}
	
	@Override
	public void init() throws Exception {
		super.init();
		WalkFSAInt.Builder builder = new WalkFSAInt.Builder();
		fsa.emit(builder);
		walkFSA = builder.build();
		fsa = null; // as needed only on initialization
	}

	@Override
	protected void processEntry(String entry, Map<String, String> feats, String majorType,
			String minorType, String annotation) {
		
		if(majorType != null)
			feats.put("majorType", majorType);
		
		if(minorType != null)
			feats.put("minorType", minorType);
		
		TIntArrayList l = new TIntArrayList();
		entry = entry.trim();
		
		if(!caseSensitive)
			entry = entry.toLowerCase();

		for(int i = 0; i < entry.length(); i++) {
			l.add(entry.charAt(i));
		}		
		
		fsa.setFinalValue(this.feats.get(feats));
		fsa.addMinWord(l);
	}
	
	public void createLookup(int state, Document d, int start, int end) {
		int[] finals = walkFSA.getFinals(state);

		if(finals == null)
			return;
		
		for(int i = 0; i < finals.length; i++) {
			Map<String, Object> f = new HashMap<String, Object>(feats.get(finals[i]));
			d.addAnnotation(Annotation.LOOKUP, start, end, f);
		}
	}

}
