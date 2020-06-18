import java.util.HashMap;

public class Node {
	int start;
	int end;
	Node suffixlink;
	HashMap<Character, Node> children = new HashMap<>();

	public Node(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public String getlabel(String s) {
		if (this.start == -1)
			return "root";
		return s.substring(this.start, this.end);
	}
}
