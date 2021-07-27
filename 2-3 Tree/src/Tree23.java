
public class Tree23 {
	private class Node23 {
		private int items[] = new int[3];
		private Node23 children[] = new Node23[4];
		private int numItems = 0;

		public Node23() {}
		
		public Node23(int item) {
			items[0] = item;
			numItems = 1;
		}
		
		public void insert(int k) {
			int i = numItems-1;
			while (i >= 0) {
				if (k < items[i]) {
					items[i+1] = items[i];
					--i;
				}
				else break;
			}
			items[i+1] = k;
			++numItems;
		}
		
		public void replace(Node23 n, Node23 n1, Node23 n2) {
			if (n == children[0]) {
				children[3] = children[2];
				children[2] = children[1];
				children[1] = n2;
				children[0] = n1;
			}
			else if (n == children[1]) {
				children[3] = children[2];
				children[2] = n2;
				children[1] = n1;
			}
			else {
				children[3] = n2;
				children[2] = n1;
			}
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
			return "Node values:  First: " + items[0] + (numItems == 2 ? "   Second: " + items[1] : "");
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
		
		if (r.numItems == 1) {
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
			if (root.numItems == 3) root = split(root, null);	
		}
	}
	
	private Node23 insert(int k, Node23 r) {
		if (r.isLeaf()) {
			r.insert(k);
			return r;
		}
		
		Node23 child = null;
		if (r.numItems == 1) {
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
		
		if (child.numItems == 3) return split(child, r);
		return r;
	}
	
	private Node23 split(Node23 n, Node23 p) {
		if (p == null) {
			p = new Node23();
			p.children[0] = n;
		}
		
		Node23 n1 = new Node23(n.items[0]);
		Node23 n2 = new Node23(n.items[2]);
		
		if (!n.isLeaf()) {
			n1.children[0] = n.children[0];
			n1.children[1] = n.children[1];
			n2.children[0] = n.children[2];
			n2.children[1] = n.children[3];
		}
		
		p.insert(n.items[1]);
		p.replace(n, n1, n2);
		return p;
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
		if (r.numItems == 2) {
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
