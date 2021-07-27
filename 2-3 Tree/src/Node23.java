
public class Node23 {
	private int[] items;
	private Node23[] children;
	private int numItems;

	public Node23() {
		items = new int[3];
		children = new Node23[4];
		numItems = 0;
	}
	
	public Node23(int item) {
		this();
		items[0] = item;
		numItems = 1;
	}
	
	public Node23(Node23 child) {
		this();
		children[0] = child;
	}
	
	public static Node23[] split(Node23 node) {
		Node23[] newNodes = new Node23[2];
		
		newNodes[0] = new Node23(node.items[0]);
		newNodes[1] = new Node23(node.items[2]);
		
		if (!node.isLeaf()) {
			newNodes[0].children[0] = node.children[0];
			newNodes[0].children[1] = node.children[1];
			newNodes[1].children[0] = node.children[2];
			newNodes[1].children[1] = node.children[3];
		}
		
		return newNodes;
	}
	
	public void insert(int k) {
		int i = numItems-1;
		while (i >= 0) {
			if (items[i] < k) break;
			items[i+1] = items[i];
			--i;
		}
		items[i+1] = k;
		++numItems;
	}
	
	public void replaceChild(Node23 oldChild, Node23 newChild1, Node23 newChild2) {
		if (oldChild == children[0]) {
			children[3] = children[2];
			children[2] = children[1];
			children[1] = newChild2;
			children[0] = newChild1;
		}
		else if (oldChild == children[1]) {
			children[3] = children[2];
			children[2] = newChild2;
			children[1] = newChild1;
		}
		else {
			children[3] = newChild2;
			children[2] = newChild1;
		}
	}
	
	public int getNumItems() {
		return numItems;
	}
	
	public int getSmallItem() {
		return items[0];
	}
	
	public int getLargeItem() {
		return items[1];
	}

	public Node23 getLeft() {
		return children[0];
	}
	
	public Node23 getMiddle() {
		return children[1];
	}
	
	public Node23 getRight() {
		return children[2];
	}
	
	public boolean isLeaf() {
		return children[0] == null;
	}
	
	@Override
	public String toString() {
		return "Node values:  First: " + items[0] 
				+ (numItems == 2 ? "   Second: " + items[1] : "");
	}
}
