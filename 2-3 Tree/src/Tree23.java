
public class Tree23 {
	private static class Node23 {
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
	private Node23 root;
	
	public Tree23() {
		root = null;
	}
	
	public void find(int k) throws Tree23Exception {
		Node23 n = find(k, root);
		if (n == null) throw new Tree23Exception("Element cannot be found.");
		System.out.println(k + " is " 
				+ (k == n.getSmallItem() ? "first" : "second") 
				+ " data in " + n.toString());
	}
	
	private Node23 find(int k, Node23 r) {
		if (r == null) return null;
		
		if (r.getNumItems() == 1) {
			if (k == r.getSmallItem()) return r;
			
			if (k < r.getSmallItem()) return find(k, r.getLeft());
			return find(k, r.getMiddle());
		}
		else {
			if (k == r.getSmallItem() || k == r.getLargeItem()) return r;
			
			if (k < r.getSmallItem()) return find(k, r.getLeft());
			if (k > r.getLargeItem()) return find(k, r.getRight());
			return find(k, r.getMiddle());
		}
	}
	
	public void insert(int k) throws Tree23Exception {
		if (root == null) {
			root = new Node23(k);
		}
		else {
			root = insert(k, root);
			if (root.getNumItems() == 3) root = split(root, null);	
		}
	}
	
	private Node23 insert(int k, Node23 r) {
		if (r.isLeaf()) {
			r.insert(k);
			return r;
		}
		
		Node23 child = null;
		if (r.getNumItems() == 1) {
			if (k == r.getSmallItem())
				throw new Tree23Exception("Cannot insert duplicate element.");
			
			if (k < r.getSmallItem()) child = insert(k, r.getLeft());
			else child = insert(k, r.getMiddle());
		}
		else {
			if (k == r.getSmallItem() || k == r.getLargeItem())
				throw new Tree23Exception("Cannot insert duplicate element.");
			
			if (k < r.getSmallItem()) child = insert(k, r.getLeft());
			else if (k > r.getLargeItem()) child = insert(k, r.getRight());
			else child = insert(k, r.getMiddle());
		}
		
		if (child.getNumItems() == 3) return split(child, r);
		return r;
	}
	
	private Node23 split(Node23 node, Node23 parent) {
		if (parent == null)
			parent = new Node23(node);
		parent.insert(node.getLargeItem());
		
		Node23[] newNodes = Node23.split(node);
		parent.replaceChild(node, newNodes[0], newNodes[1]);
		
		return parent;
	}

	public void emptyTree() {
		root = null;
	}
	
	public void printPreorder() {
		System.out.println("Pre order traversal: ");
		printPreorder(root);
	}
	
	private void printPreorder(Node23 r) {
		if (r == null) return;
		System.out.println(r.toString());
		printPreorder(r.getLeft());
		printPreorder(r.getMiddle());
		printPreorder(r.getRight());
	}
	
	public void printInorder() {
		System.out.println("In order traversal:");
		printInorder(root);
	}
	
	private void printInorder(Node23 r) {
		if (r == null) return;
		
		printInorder(r.getLeft());
		System.out.println(r.getSmallItem());
		printInorder(r.getMiddle());
		if (r.getNumItems() == 2) {
			System.out.println(r.getLargeItem());
			printInorder(r.getRight());
		}
	}
	
	public int getHeight() {
		return getHeight(root);
	}
	
	private int getHeight(Node23 r) {
		if (r == null) return 0;
		return 1 + getHeight(r.getLeft());
	}
	
}

class Tree23Exception extends RuntimeException {
	public Tree23Exception(String message) {
		super(message);
	}
}
