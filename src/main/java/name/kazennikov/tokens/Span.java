package name.kazennikov.tokens;

public class Span implements CharSequence {
	String text;
	int start;
	int end;
	
	public Span(String text, int start, int end) {
		this.text = text;
		this.start = start;
		this.end = end;
	}

	@Override
	public int length() {
		return end - start;
	}

	@Override
	public char charAt(int index) {
		return text.charAt(start + index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return text.subSequence(this.start + start, this.start + end);
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
}
