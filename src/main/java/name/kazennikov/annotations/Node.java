package name.kazennikov.annotations;

import com.google.common.base.Objects;

public class Node implements IdBearer {
	int id;
	int offset;
	
	public Node(int id, int offset) {
		this.id = id;
		this.offset = offset;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	public int getOffset() {
		return offset;
	}

	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("id", id)
				.add("offset", offset)
				.toString();
	}
}
