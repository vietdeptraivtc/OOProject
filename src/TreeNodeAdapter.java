import javax.swing.tree.DefaultMutableTreeNode;

public class TreeNodeAdapter {
    public static DefaultMutableTreeNode adapt(TreeNode node) {
        if (node == null) return null;
        DefaultMutableTreeNode swingNode = new DefaultMutableTreeNode(node.getValue());
        if (node instanceof GenericTree.GenericTreeNode) {
            GenericTree.GenericTreeNode genericNode = (GenericTree.GenericTreeNode) node;
            for (GenericTree.GenericTreeNode child : genericNode.children) {
                swingNode.add(adapt(child));
            }
        } else if (node instanceof BinaryTree.BinaryTreeNode) {
            BinaryTree.BinaryTreeNode binaryNode = (BinaryTree.BinaryTreeNode) node;
            if (binaryNode.left != null) swingNode.add(adapt(binaryNode.left));
            if (binaryNode.right != null) swingNode.add(adapt(binaryNode.right));
        } else if (node instanceof BalancedTree.BalancedTreeNode) {
            BalancedTree.BalancedTreeNode balancedNode = (BalancedTree.BalancedTreeNode) node;
            for (BalancedTree.BalancedTreeNode child : balancedNode.children) {
                swingNode.add(adapt(child));
            }
        } else if (node instanceof BalancedBinaryTree.AVLTreeNode) {
            BalancedBinaryTree.AVLTreeNode avlNode = (BalancedBinaryTree.AVLTreeNode) node;
            if (avlNode.left != null) swingNode.add(adapt(avlNode.left));
            if (avlNode.right != null) swingNode.add(adapt(avlNode.right));
        }
        return swingNode;
    }
}
