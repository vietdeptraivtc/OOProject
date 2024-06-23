public class BinaryTree extends Tree {
    class BinaryTreeNode extends TreeNode {
        BinaryTreeNode left;
        BinaryTreeNode right;

        public BinaryTreeNode(int value) {
            super(value);
            this.left = null;
            this.right = null;
        }
    }

    public BinaryTree() {
        this.root = null;
    }

    @Override
    public void insert(int value) {
        root = insertRec((BinaryTreeNode) root, value);
    }

    private BinaryTreeNode insertRec(BinaryTreeNode root, int value) {
        if (root == null) {
            root = new BinaryTreeNode(value);
            return root;
        }
        if (value < root.getValue()) {
            root.left = insertRec(root.left, value);
        } else if (value > root.getValue()) {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    @Override
    public void delete(int value) {
        root = deleteRec((BinaryTreeNode) root, value);
    }

    private BinaryTreeNode deleteRec(BinaryTreeNode root, int value) {
        if (root == null) return root;
        if (value < root.getValue()) {
            root.left = deleteRec(root.left, value);
        } else if (value > root.getValue()) {
            root.right = deleteRec(root.right, value);
        } else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            root.setValue(minValue(root.right));
            root.right = deleteRec(root.right, root.getValue());
        }
        return root;
    }

    private int minValue(BinaryTreeNode root) {
        int minValue = root.getValue();
        while (root.left != null) {
            minValue = root.left.getValue();
            root = root.left;
        }
        return minValue;
    }

    @Override
    public void update(int oldValue, int newValue) {
        delete(oldValue);
        insert(newValue);
    }

    @Override
    public void traverse(String order, StringBuilder result) {
        if ("preorder".equalsIgnoreCase(order)) {
            preorder((BinaryTreeNode) root, result);
        } else if ("postorder".equalsIgnoreCase(order)) {
            postorder((BinaryTreeNode) root, result);
        } else if ("inorder".equalsIgnoreCase(order)) {
            inorder((BinaryTreeNode) root, result);
        }
    }

    private void preorder(BinaryTreeNode node, StringBuilder result) {
        if (node == null) return;
        result.append(node.getValue()).append(" ");
        preorder(node.left, result);
        preorder(node.right, result);
    }

    private void postorder(BinaryTreeNode node, StringBuilder result) {
        if (node == null) return;
        postorder(node.left, result);
        postorder(node.right, result);
        result.append(node.getValue()).append(" ");
    }

    private void inorder(BinaryTreeNode node, StringBuilder result) {
        if (node == null) return;
        inorder(node.left, result);
        result.append(node.getValue()).append(" ");
        inorder(node.right, result);
    }

    @Override
    public boolean search(int value) {
        return searchRec((BinaryTreeNode) root, value) != null;
    }

    private BinaryTreeNode searchRec(BinaryTreeNode root, int value) {
        if (root == null || root.getValue() == value) return root;
        if (root.getValue() > value) return searchRec(root.left, value);
        return searchRec(root.right, value);
    }
}
