package name.kazennikov.annotations.gazetteer;

import java.util.List;

public class GazetteerEntry {
	String entry;
	
	GazetteerDefinition def;
	List<String> featNames;
	List<String> featValues;
	
	public GazetteerEntry(String entry, GazetteerDefinition def, List<String> featNames, List<String> featValues) {
		this.entry = entry;
		this.def = def;
		this.featNames = featNames;
		this.featValues = featValues;
	}

}
