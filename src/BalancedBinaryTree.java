public class BalancedBinaryTree extends Tree {
    class AVLTreeNode extends TreeNode {
        AVLTreeNode left;
        AVLTreeNode right;
        int height;

        public AVLTreeNode(int value) {
            super(value);
            this.height = 1;
        }
    }

    public BalancedBinaryTree() {
        this.root = null;
    }

    private int height(AVLTreeNode node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(AVLTreeNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    @Override
    public void insert(int value) {
        root = insertRec((AVLTreeNode) root, value);
    }

    private AVLTreeNode insertRec(AVLTreeNode node, int value) {
        if (node == null) {
            return new AVLTreeNode(value);
        }
        if (value < node.getValue()) {
            node.left = insertRec(node.left, value);
        } else if (value > node.getValue()) {
            node.right = insertRec(node.right, value);
        } else {
            return node; // Duplicate values not allowed
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);
        if (balance > 1 && value < node.left.getValue()) {
            return rotateRight(node);
        }
        if (balance < -1 && value > node.right.getValue()) {
            return rotateLeft(node);
        }
        if (balance > 1 && value > node.left.getValue()) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && value < node.right.getValue()) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    @Override
    public void delete(int value) {
        root = deleteRec((AVLTreeNode) root, value);
    }

    private AVLTreeNode deleteRec(AVLTreeNode root, int value) {
        if (root == null) return root;

        if (value < root.getValue()) {
            root.left = deleteRec(root.left, value);
        } else if (value > root.getValue()) {
            root.right = deleteRec(root.right, value);
        } else {
            if ((root.left == null) || (root.right == null)) {
                AVLTreeNode temp = root.left != null ? root.left : root.right;
                root = temp;
            } else {
                AVLTreeNode temp = minValueNode(root.right);
                root.setValue(temp.getValue());
                root.right = deleteRec(root.right, temp.getValue());
            }
        }

        if (root == null) return root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;

        int balance = getBalance(root);
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rotateRight(root);
        }
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }
        if (balance < -1 && getBalance(root.right) <= 0) {
            return rotateLeft(root);
        }
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }

    private AVLTreeNode rotateLeft(AVLTreeNode z) {
        AVLTreeNode y = z.right;
        AVLTreeNode T2 = y.left;
        y.left = z;
        z.right = T2;
        z.height = Math.max(height(z.left), height(z.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    private AVLTreeNode rotateRight(AVLTreeNode y) {
        AVLTreeNode x = y.left;
        AVLTreeNode T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private AVLTreeNode minValueNode(AVLTreeNode node) {
        AVLTreeNode current = node;
        while (current.left != null) current = current.left;
        return current;
    }

    @Override
    public void update(int oldValue, int newValue) {
        delete(oldValue);
        insert(newValue);
    }

    @Override
    public void traverse(String order, StringBuilder result) {
        if ("preorder".equalsIgnoreCase(order)) {
            preorder((AVLTreeNode) root, result);
        } else if ("postorder".equalsIgnoreCase(order)) {
            postorder((AVLTreeNode) root, result);
        } else if ("inorder".equalsIgnoreCase(order)) {
            inorder((AVLTreeNode) root, result);
        }
    }

    private void preorder(AVLTreeNode node, StringBuilder result) {
        if (node == null) return;
        result.append(node.getValue()).append(" ");
        preorder(node.left, result);
        preorder(node.right, result);
    }

    private void postorder(AVLTreeNode node, StringBuilder result) {
        if (node == null) return;
        postorder(node.left, result);
        postorder(node.right, result);
        result.append(node.getValue()).append(" ");
    }

    private void inorder(AVLTreeNode node, StringBuilder result) {
        if (node == null) return;
        inorder(node.left, result);
        result.append(node.getValue()).append(" ");
        inorder(node.right, result);
    }

    @Override
    public boolean search(int value) {
        return searchNode((AVLTreeNode) root, value) != null;
    }

    private AVLTreeNode searchNode(AVLTreeNode node, int value) {
        if (node == null) return null;
        if (node.getValue() == value) return node;
        if (value < node.getValue()) return searchNode(node.left, value);
        return searchNode(node.right, value);
    }
}
