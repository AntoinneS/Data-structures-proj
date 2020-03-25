//A. Stewart-
//G. Watson-
//Chevis Hutchinson -1601446
package main;

public class Node {
	private Data data;
	private Node nextNode;

	public Node() {
		this.data = null;
		this.nextNode = null;
	}

	public Node(Data data, Node nextNode) {
		this.data = data;
		this.nextNode = nextNode;
	}

	// Accessors
	public final Data getData() {
		return this.data;
	}

	public final Node getNextNode() {
		return this.nextNode;
	}

	// Mutators
	public void setData(Data data) {
		this.data = data;
	}

	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
}
