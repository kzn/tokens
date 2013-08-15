package name.kazennikov.annotations.gazetteer;

import java.io.File;

import com.google.common.base.Objects;

public class GazetteerDef {
	File file;
	
	String minorType;
	String majorType;
	String annotation;
	
	public GazetteerDef(File file, String minorType, String majorType, String annotation) {
		super();
		this.file = file;
		this.minorType = minorType;
		this.majorType = majorType;
		this.annotation = annotation;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("file", file)
				.add("minorType", minorType)
				.add("majorType", majorType)
				.add("annotation", annotation)
				.toString();
	}
	
	
}