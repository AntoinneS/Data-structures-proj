//A. Stewart-
//G. Watson-
//Chevis Hutchinson -1601446

package main;

import java.io.File;
import java.io.FileReader;

public class Tree {
	private TreeNode root;
	private int count;
	private boolean isSorted;

	public Tree() {
		this.root = null;
		this.count = 0;
		this.isSorted = false;
	}

	public void setSorted(boolean isSorted) {
		this.isSorted = isSorted;
	}

	public void read(String file) {
		try {
			this.destroy(this.getRoot());

			FileReader reader = new FileReader(new File(file));

			String sentence = "";
			char letter;
			int val = 0;

			while ((val = reader.read()) != -1) {
				letter = (char) val;

				if (letter == '\n') {
					this.parse(sentence);
					sentence = "";
				} else {
					sentence += letter;
				}
			}

			if (!sentence.isEmpty()) {
				this.parse(sentence);
			}

			reader.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void parse(String sentence) {
		String array[] = sentence.split("	");
		String word = "", definition = "", pos = "";

		if (array.length == 3) {
			word = array[0].replace("-", "");
			word = word.replace("-", "");
			String array2[] = array[1].split(". ");
			int count = 0;

			for (String string : array2) {
				String newSpeech = Driver.parsePOS(string.replace(".", ""));
				pos += (count == 0 ? "" : ", ") + newSpeech;
				count++;
			}

			definition = array[2].replace("-", "");
			definition = definition.replace("-", "");

			if ((!word.trim().isEmpty() && !definition.trim().isEmpty() && !pos.trim().isEmpty())
					&& (this.search(word) == null)) {

				word = Driver.upperCaseFirstLetter(word.trim());
				definition = Driver.upperCaseFirstLetter(definition.trim());
				pos = Driver.upperCaseFirstLetter(pos.trim());

				this.insertNode(new Data(word, definition, pos));
			}
		}
	}

	public Data search(String word) {
		return searchFor(this.getRoot(), word);
	}

	public Data searchFor(TreeNode node, String word) {
		Data data = null;

		if (node == null) {
			return data;
		} else {
			if (node.getData().getWord().equals(word)) {
				data = node.getData();
				return data;
			} else if (word.compareTo(node.getData().getWord()) < 0) {
				return searchFor(node.getLeftSubTree(), word);
			} else {
				return searchFor(node.getRightSubTree(), word);
			}
		}
	}

	public void insertNode(Data data) {
		TreeNode node = new TreeNode(data);
		TreeNode tempRoot;

		if (node != null) {
			if (this.root == null) {
				this.root = node;
				this.count += 1;
			} else {
				tempRoot = this.root;

				while (true) {
					if (node.getData().getWord().compareTo(tempRoot.getData().getWord()) < 0) {
						if (tempRoot.getLeftSubTree() == null) {
							tempRoot.setLeftSubTree(node);
							this.count += 1;
							break;
						} else {
							tempRoot = tempRoot.getLeftSubTree();
						}
					} else {
						if (tempRoot.getRightSubTree() == null) {
							tempRoot.setRightSubTree(node);
							this.count += 1;
							break;
						} else {
							tempRoot = tempRoot.getRightSubTree();
						}
					}
				}
			}
		}
	}

	public LinkedList getAll() {
		LinkedList linkedList = new LinkedList();

		if (this.isSorted) {
			return this.getInOrderTreeNodes(this.getRoot(), linkedList);
		} else {
			return this.getAllTreeNodes(this.getRoot(), linkedList);
		}
	}

	public LinkedList getAllTreeNodes(TreeNode node, LinkedList linkedList) {
		if (node != null) {
			linkedList.insertAtBack(node.getData());
			getAllTreeNodes(node.getLeftSubTree(), linkedList);
			getAllTreeNodes(node.getRightSubTree(), linkedList);
		}

		return linkedList;
	}

	public LinkedList getInOrderTreeNodes(TreeNode node, LinkedList linkedList) {
		if (node != null) {
			getInOrderTreeNodes(node.getLeftSubTree(), linkedList);
			linkedList.insertAtBack(node.getData());
			getInOrderTreeNodes(node.getRightSubTree(), linkedList);
		}

		return linkedList;
	}

	public boolean sortTree(TreeNode node) {
		if (node != null) {
			sortTree(node.getLeftSubTree());
			sortTree(node.getRightSubTree());
		}

		return true;
	}

	public boolean sort() {
		return sortTree(this.getRoot());
	}

	public void preOrderTraversal(TreeNode node) {
		if (node != null) {
			System.out.println("Word: " + node.getData().getWord());
			preOrderTraversal(node.getLeftSubTree());
			preOrderTraversal(node.getRightSubTree());
		}
	}

	public void inOrderTraversal(TreeNode node) {
		if (node != null) {
			inOrderTraversal(node.getLeftSubTree());
			System.out.println("Word: " + node.getData().getWord());
			inOrderTraversal(node.getRightSubTree());
		}
	}

	public void postOrderTraversal(TreeNode node) {
		if (node != null) {
			postOrderTraversal(node.getLeftSubTree());
			postOrderTraversal(node.getRightSubTree());
			System.out.println("Word: " + node.getData().getWord());
		}
	}

	public void destroy(TreeNode node) {
		if (node != null) {
			destroy(node.getLeftSubTree());
			destroy(node.getRightSubTree());
			this.count -= 1;
			node = null;
		}
	}

	public int getCount() {
		return this.count;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getRoot() {
		return this.root;
	}
}
