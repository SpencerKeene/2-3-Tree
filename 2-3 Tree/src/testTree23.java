
public class testTree23 {

	public static void main(String[] args) {
		Tree23 tree = new Tree23();
		
		tree.insert(20);
		tree.insert(1);
		tree.insert(5);
		tree.insert(2);
		tree.insert(14);
		tree.insert(15);
		tree.insert(8);
		tree.insert(19);
		tree.insert(17);
		tree.insert(13);
		tree.insert(9);
		tree.insert(18);
		tree.insert(25);
		tree.insert(22);
		
		tree.printInorder();
		System.out.println();
		
		tree.printPreorder();
		System.out.println("\nTree height: " + tree.getHeight());
		
		System.out.println("\nFinding values:");
		tree.find(20);
		tree.find(14);
		tree.find(5);
		tree.find(14);
		tree.find(9);
	}

}
