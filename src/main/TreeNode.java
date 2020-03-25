//A. Stewart-
//G. Watson-
//Chevis Hutchinson -1601446

package main;

public class TreeNode {
	private Data data;
	private TreeNode leftSubTree;
	private TreeNode rightSubTree;

	public TreeNode() {
		this.data = new Data();
		this.leftSubTree = this.rightSubTree = null;
	}

	public TreeNode(Data data) {
		this.data = data;
		this.leftSubTree = null;
		this.rightSubTree = null;
	}

	public final Data getData() {
		return data;
	}

	public final void setData(Data data) {
		this.data = data;
	}

	public final TreeNode getLeftSubTree() {
		return leftSubTree;
	}

	public final void setLeftSubTree(TreeNode leftSubTree) {
		this.leftSubTree = leftSubTree;
	}

	public final TreeNode getRightSubTree() {
		return rightSubTree;
	}

	public final void setRightSubTree(TreeNode rightSubTree) {
		this.rightSubTree = rightSubTree;
	}
}