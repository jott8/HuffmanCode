import java.util.HashMap;
import java.util.ArrayList;

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

    // Build binary tree based on the Node Arraylist
    private void constructTree() {
        ArrayList<HuffNode> tempNodes = this.nodes;
        HuffNode[] twoLeastValuedNodes = new HuffNode[2];
        HuffNode newParent;
        this.depth = 1;

        while (tempNodes.size() >= 2) {
            twoLeastValuedNodes = twoLeastValuedNodes(tempNodes);
            newParent = new HuffNode(twoLeastValuedNodes[0].getValue() +
                    twoLeastValuedNodes[1].getValue());
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

    // Returns two valuedNodes of nodeList, removes them and returns them as array
    private HuffNode[] twoLeastValuedNodes(ArrayList<HuffNode> nodeList) {
        HuffNode[] result = new HuffNode[2];
        int minValue = nodeList.get(0).getValue();
        int pos = 0;

        // if size == 2, nodes get directly added to the array and removed from the list
        if (nodeList.size() == 2) {
            for (int i = 0; i < 2; i++) {
                result[i] = nodeList.get(i);

            }
            nodeList.remove(0);
            nodeList.remove(0);
        }

        // minValue is set to the first value in nodeList, checks for lower value and
        // replaces them, add lowest one to array and delete it
        else {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < nodeList.size(); j++) {
                    if (nodeList.get(j).getValue() < minValue) {
                        minValue = nodeList.get(j).getValue();
                        pos = j;
                    }
                }
                result[i] = nodeList.get(pos);
                nodeList.remove(nodeList.get(pos));
                minValue = nodeList.get(0).getValue();
                pos = 0;
            }
        }

        return result;
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
