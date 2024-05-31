package structures.trees;

public class BST implements Tree {
    private BTreeNode root; // única entrada al árbol

    public BST() {
        this.root = null;
    }

    @Override
    public int size() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty.");
        }
        return size(root);
    }

    private int size(BTreeNode node) {
        if (node == null)
            return 0;
        else
            return 1 + size(node.left) + size(node.right);
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object element) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty.");
        }
        return binarySearch(root, element);
    }

    private boolean binarySearch(BTreeNode node, Object element) {
        if (node == null)
            return false;
        else if (util.Utility.compare(node.data, element) == 0)
            return true; // ya lo encontró
        else if (util.Utility.compare(element, node.data) < 0)
            return binarySearch(node.left, element);
        else return binarySearch(node.right, element);
    }

    @Override
    public void add(Object element) {
        this.root = add(root, element);
    }

    private BTreeNode add(BTreeNode node, Object element) {
        if (node == null) node = new BTreeNode(element);
        else{
            if (util.Utility.compare(element,node.data) < 0)
                node.left = add(node.left,element);
            else if (util.Utility.compare(element, node.data) > 0)
                node.right = add(node.right,element);
        }
        return node;
    }

    @Override
    public void remove(Object element) throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Search Tree is empty.");
        root = remove(root, element);
    }

    private BTreeNode remove(BTreeNode node, Object element) {
        if (node != null)
            if (util.Utility.compare(element,node.data) <0)
                node.left = remove(node.left, element);
            else if (util.Utility.compare(element,node.data) > 0)
                node.right = remove(node.right, element);
            else if (util.Utility.compare(node.data, element) == 0)
                // Caso 1. Si es una hoja (nodo sin hijos)
                if (node.left == null && node.right == null)
                    return null;
                // Caso 2. El nodo solo tiene un hijo
                else if (node.left == null && node.right != null)
                    return node.right;
                else if (node.left != null && node.right == null)
                    return node.left;
                // Caso 3. El nodo tiene dos hijos
                else if (node.left != null && node.right != null) {
                    Object value = min(node.right);
                    node.data = value;
                    node.right = remove(node.right, value);
                }
        return node;
    }

    @Override
    public int height(Object element) throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty.");
        return height(root, element, 0);
    }

    private int height(BTreeNode node, Object element, int counter) {
        if (node == null) return 0; // el elemento no existe
        else if (util.Utility.compare(node.data, element) == 0) return counter;
        else if (util.Utility.compare(element, node.data) < 0)
            return height(node.left, element, ++counter);
        else return height(node.right, element, ++counter);
        //else return Math.max(height(node.left, element, ++counter), height(node.right, element, counter));
    }

    @Override
    public int height() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty.");
        return height(root) - 1;
    }

    private int height(BTreeNode node) {
        if (node == null) return 0;
        else return Math.max(height(node.left), height(node.right)) + 1;
    }

    @Override
    public Object min() throws TreeException {
        if (isEmpty()) throw  new TreeException("Binary Search Tree is empty.");
        return min(root);
    }

    private Object min(BTreeNode node) {
        // Método interno.
        if (node.left != null) return min(node.left);
        return node.data;

    }

    @Override
    public Object max() throws TreeException {
        if (isEmpty()) throw  new TreeException("Binary Search Tree is empty.");
        return max(root);
    }

    private Object max(BTreeNode node) {
        if (node.right != null) return max(node.right);
        return node.data;
    }

    @Override
    public String preOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty.");
        return preOrder(root) + "\n";
    }

    // nodo-izq-der
    private String preOrder(BTreeNode node) {
        String result = "";
        if (node != null) {
            result = node.data + " ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String inOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty.");
        return inOrder(root) + "\n";
    }

    // izq-nodo-der
    private String inOrder(BTreeNode node) {
        String result = "";
        if (node != null) {
            result = inOrder(node.left);
            result += node.data + "(" + node.path + ") ";
            result += inOrder(node.right);
        }
        return result;
    }

    @Override
    public String postOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty.");
        return postOrder(root) + "\n";
    }

    // izq-der-nodo
    private String postOrder(BTreeNode node) {
        String result = "";
        if (node != null) {
            result = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data + "(" + node.path + ") ";
        }
        return result;
    }

    public boolean isBalanced() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty.");
        }
        return isBalanced(root);
    }

    private boolean isBalanced(BTreeNode node) {
        if (node == null) return true;

        return Math.abs(height(node.left) - height(node.right)) <= 1
                && isBalanced(node.left)
                && isBalanced(node.right);
    }

    @Override
    public String toString() {
        if (isEmpty())
            return "Binary Search Tree is empty.";
        String result = "BINARY SEARCH TREE TOUR...\n";
        result += "PreOrder: " + preOrder(root) + "\n";
        result += "InOrder: " + inOrder(root) + "\n";
        result += "PostOrder: " + postOrder(root) + "\n";
        return result;
    }

    public BTreeNode getRoot() {
        return root;
    }
}