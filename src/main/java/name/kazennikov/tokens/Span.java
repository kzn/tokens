package name.kazennikov.tokens;

public class Span{
	public static final Span NULL = new Span(null, 0, 0);
	String text;
	int start;
	int end;
	
	public Span(String text, int start, int end) {
		this.text = text;
		this.start = start;
		this.end = end;
	}


	public int length() {
		return end - start;
	}


	public char charAt(int index) {
		return text.charAt(start + index);
	}

	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public void translate(int offset) {
		start += offset;
		end += offset;
	}
	
	public static Span make(String text, int start, int end) {
		return new Span(text, start, end);
	}
	
	public static Span make(String text, int pos) {
		return new Span(text, pos, pos);
	}
	
	public String toText() {
		return text.subSequence(start, end).toString();
	}
	
	public String getSource() {
		return text;
	}
	
	public Span subSpan(int start, int end) {
		return make(text, this.start + start, this.start + end);
	}
}
