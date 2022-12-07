public class HuffNode {
    private HuffNode leftChild, rightChild, parent;
    private char key;
    private int value;

    public HuffNode(char key, int value) {
        this.leftChild = this.rightChild = this.parent = null;
        this.key = key;
        this.value = value;
    }

    public HuffNode(int value) {
        this.leftChild = null;
        this.rightChild = null;
        this.key = '\\';
        this.value = value;
    }

    public void getData() {
        System.out.println("key : " + this.key);
        System.out.println("value : " + this.value);
        System.out.println("parent : " + this.parent);
        System.out.println("leftChild : " + leftChild);
        System.out.println("rightChild : " + rightChild);
    }

    public HuffNode getParent() {
        return this.parent;
    }

    public HuffNode getLeftChild() {
        return this.leftChild;
    }

    public HuffNode getRightChild() {
        return this.rightChild;
    }

    public char getKey() {
        return this.key;
    }

    public int getValue() {
        return this.value;
    }

    public void setParent(HuffNode newParent) {
        this.parent = newParent;
    }

    public void setLeftChild(HuffNode newLeftChild) {
        this.leftChild = newLeftChild;
    }

    public void setRightChild(HuffNode newRightChild) {
        this.rightChild = newRightChild;
    }

    public boolean hasKey() {
        if (this.key == '\\') {
            return false;
        }
        return true;
    }

    public void printParentAndChildren() {
        System.out.println("  " + this.getValue());

        if (this.getValue() > 10 && this.getValue() < 100) {
            System.out.println(" /" + "  " + "\\ ");
            System.out.println(this.getLeftChild().getValue() + "    " + this.getRightChild().getValue());
        } else if (this.getValue() > 100) {
            return;
        } else {
            System.out.println(" /" + " " + "\\ ");
            System.out.println(this.getLeftChild().getValue() + "   " + this.getRightChild().getValue());
        }
    }

}
