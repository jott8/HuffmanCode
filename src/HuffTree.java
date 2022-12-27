import java.util.ArrayList;
import java.util.HashMap;

public class HuffTree {

	private HuffNode root = null;
	private ArrayList<HuffNode> nodes;
	private int depth;
	private int amountNodes;
	private HashMap<Character, Integer> entries;
	private HashMap<Character, String> letterCodes = new HashMap<>();

	HuffTree(HashMap<Character, Integer> entries) {
		this.entries = entries;
		this.nodes = entriesToNodes(entries);
		this.amountNodes = nodes.size();
		this.constructTree();
	}

	// Entries HashMap gets converted to ArrayList of Nodes
	private ArrayList<HuffNode> entriesToNodes(HashMap<Character, Integer> entriesHMap) {
		ArrayList<HuffNode> nodes = new ArrayList<>();

		for (char tempKey : entriesHMap.keySet()) {
			nodes.add(new HuffNode(tempKey, entriesHMap.get(tempKey)));
		}
		return nodes;
	}

	// Builds binary tree based on the Arraylist of Huffnodes
	private void constructTree() {
		ArrayList<HuffNode> tempNodes = this.nodes;
		sortList(tempNodes);
		HuffNode[] twoLeastValuedNodes = new HuffNode[2];
		HuffNode newParent;
		this.depth = 1;

		while (tempNodes.size() >= 2) {
			for (int i = 0; i < 2; i++) {
				twoLeastValuedNodes[i] = tempNodes.get(0);
				tempNodes.remove(0);
			}

			newParent = new HuffNode(twoLeastValuedNodes[0].getValue() + twoLeastValuedNodes[1].getValue());
			tempNodes.add(newParent);
			newParent.setLeftChild(twoLeastValuedNodes[0]);
			newParent.setRightChild(twoLeastValuedNodes[1]);

			twoLeastValuedNodes[0].setParent(newParent);
			twoLeastValuedNodes[1].setParent(newParent);

			this.amountNodes += 1;
			this.depth += 1;
			this.root = newParent;
		}

		for (Character key : entries.keySet()) {
			this.letterCodes.put(key, "");
		}

		generateCodes();
	}

	// Sorts Arraylist of Huffnodes from lowest to highest value using quicksort
	// algorithm
	private void sortList(ArrayList<HuffNode> list) {
		if (list.isEmpty() || list.size() == 1) {
			return;
		}

		HuffNode pivot = list.get(0);
		ArrayList<HuffNode> helper = list;
		ArrayList<HuffNode> lower = new ArrayList<>();
		ArrayList<HuffNode> higher = new ArrayList<>();

		for (int i = 1; i < helper.size(); i++) {
			if (helper.get(i).getValue() < pivot.getValue()) {
				lower.add(helper.get(i));
			} else {
				higher.add(helper.get(i));
			}
		}

		list.clear();
		sortList(lower);
		list.addAll(lower);
		list.add(pivot);
		sortList(higher);
		list.addAll(higher);
	}

	// Get path from root to node for one specific key (character)
	private boolean getPath(HuffNode node, char key, ArrayList<Integer> path) {
		if (node == null) {
			return false;
		}

		if (node.getParent() != null && node == node.getParent().getLeftChild()) {
			path.add(0);
		} else if (node.getParent() != null && node == node.getParent().getRightChild()) {
			path.add(1);
		}

		if (node.getKey() == key) {
			return true;
		}

		if (getPath(node.getLeftChild(), key, path) || getPath(node.getRightChild(), key, path)) {
			return true;
		}

		path.remove(path.size() - 1);
		return false;
	}

	private void generateCodes() {
		ArrayList<Integer> path = new ArrayList<>();
		String helper;

		for (Character key : letterCodes.keySet()) {
			helper = "";
			path = new ArrayList<>();
			getPath(root, key, path);

			for (int i : path) {
				helper += i;
			}

			letterCodes.put(key, helper);
		}
	}

	public void printTree() {
		printTree(this.root, 0);
	}

	// Helper for printTree recursion
	private void printTree(HuffNode node, int space) {
		final int count = 5;

		if (node == null) {
			return;
		}

		space += count;

		this.printTree(node.getRightChild(), space);

		System.out.print("\n");

		for (int i = count; i < space; i++) {
			System.out.print(" ");
		}

		System.out.print(node.getValue() + "\n");
		this.printTree(node.getLeftChild(), space);
	}

	public HashMap<Character, String> getLetterCodes() {
		return this.letterCodes;
	}

	public int getAmountNodes() {
		return this.amountNodes;
	}

	public int getDepth() {
		return this.depth;
	}
}
