//inspired by: https://stackoverflow.com/questions/9452701/ukkonens-suffix-tree-algorithm-in-plain-english

public class suffixTree {
	private Node root;
	private Node suffixstate;
	int suffixlink;
	String s;
	private ActivePoint activepoint;
	private int remainder;

	public suffixTree() {
		root = new Node(-1, -1);// split node
		activepoint = new ActivePoint(root, -1, 0); // (active_node,active_edge,active_length)
	}

	public void build(String s1) {
		this.s = s1 + "$";
		int stringlength = s.length();
		for (int i = 0; i < s.length(); i++) {
			insert(s.charAt(i), i, stringlength);
		}
		System.out.println("output:  ");
		printTree(root);
	}

	public void printTree(Node n) {
		for (Character key : n.children.keySet()) {
			Node child = n.children.get(key);
			System.out.println(
					n.getlabel(s) + "-- " + key + " ---> " + child.getlabel(s) + " " + child.start + " " + child.end);//
			if (child.suffixlink != null)
				System.out.println(" suffixlink: " + child.getlabel(s) + "  --> " + child.suffixlink.getlabel(s));
			printTree(child);
		}
	}

	public int edgelength(Node node) {
		if (node == root) {
			return 0;
		}
		return node.end - node.start + 1;
	}

	public void addsuffixlink(Node node) {
		if (suffixstate != null) {
			suffixstate.suffixlink = node;
		}
		suffixstate = node;
	}

	// **creat a suffix tree*//
	public void insert(Character c, int index, int stringlength) {
		suffixstate = null;
		remainder++; // reminder+1，activePoint.length+1
		suffixlink = 0;
		Node split;

		while (remainder > 0) {
			System.out.println(" reaminder " + " " + remainder + " activepoint. edge " + activepoint.edge + " "
					+ " character " + " " + c + " activelenth" + " " + activepoint.length);

			if (activepoint.length == 0) {
				activepoint.edge = index;
			}
			if (activepoint.point.children.containsKey(s.charAt(activepoint.edge))) { // if a suffix is found in this
				Node next = activepoint.point.children.get(s.charAt(activepoint.edge));
				if (activepoint.length >= next.end - next.start) { // if activepoint.length is longer than edge
					activepoint.length -= next.end - next.start;
					activepoint.edge += next.end - next.start;
					activepoint.point = next;
					continue;
				}

				if (s.charAt(next.start + activepoint.length) == c) { // activepoint verschieben
					activepoint.length++;
					addsuffixlink(activepoint.point);
					break;
				}
				// ***case1: innersplit** //
				split = new Node(next.start, next.start + activepoint.length);
				activepoint.point.children.put(s.charAt(activepoint.edge), split);

				System.out.println("innersplit edge at " + activepoint.edge + ":");
				System.out.println("------ " + s.substring(index) + " " + index);

				Node newleaf = new Node(index, stringlength);

				split.children.put(c, newleaf);// from split to new leaf

				next.start += activepoint.length;
				Character chara = s.charAt(next.start);
				split.children.put(chara, next);
				addsuffixlink(split);
			} else { // **case3 insert a new edge **//
				Node newleaf = new Node(index, stringlength);
				activepoint.point.children.put(s.charAt(activepoint.edge), newleaf);
				addsuffixlink(activepoint.point);
			}

			remainder--;
			if (activepoint.point == root && activepoint.length > 0) {
				activepoint.length--;
				activepoint.edge = index - remainder + 1;
			} else {
				if (activepoint.point.suffixlink != null) {
					activepoint.point = activepoint.point.suffixlink;
				} else {
					activepoint.point = root;
					break;
				}
			}
		}
		System.out.println("  -----------> ");
	}

	private class ActivePoint {
		public Node point;
		public int edge;

		public int length;

		public ActivePoint(Node point, int edge, int length) {
			this.point = point;
			this.edge = edge;
			this.length = length;
		}
	}

	public boolean containsSuffix(String str) {
		str = str + "$";
		Node node = root;
		while (true) {
			if (str.isEmpty()) {
				return node.children.isEmpty();
			}
			node = node.children.get(str.charAt(0));
			if (node == null) {
				return false;
			}
			String label = node.getlabel(s);
			if (str.startsWith(label)) {
				str = str.substring(label.length());
			} else {
				return false;
			}
		}
	}
}
