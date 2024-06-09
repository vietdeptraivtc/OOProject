import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

public class BalancedTree extends Tree {
    class BalancedTreeNode extends TreeNode {
        List<BalancedTreeNode> children;

        public BalancedTreeNode(int value) {
            super(value);
            this.children = new ArrayList<>();
        }
    }

    public BalancedTree() {
        this.root = null;
    }

    @Override
    public void insert(int value) {
        if (root == null) {
            root = new BalancedTreeNode(value);
        } else {
            insert((BalancedTreeNode) root, value);
        }
        balance((BalancedTreeNode) root);
    }

    private void insert(BalancedTreeNode node, int value) {
        if (node.children.isEmpty()) {
            node.children.add(new BalancedTreeNode(value));
        } else {
            BalancedTreeNode child = node.children.get(0);
            for (BalancedTreeNode n : node.children) {
                if (n.children.size() < child.children.size()) {
                    child = n;
                }
            }
            insert(child, value);
        }
    }

    @Override
    public void delete(int value) {
        root = delete((BalancedTreeNode) root, value);
        balance((BalancedTreeNode) root);
    }

    private BalancedTreeNode delete(BalancedTreeNode node, int value) {
        if (node == null) return null;
        if (node.getValue() == value) return null;

        List<BalancedTreeNode> newChildren = new ArrayList<>();
        for (BalancedTreeNode child : node.children) {
            BalancedTreeNode result = delete(child, value);
            if (result != null) {
                newChildren.add(result);
            }
        }
        node.children = newChildren;
        return node;
    }

    @Override
    public void update(int oldValue, int newValue) {
        BalancedTreeNode node = (BalancedTreeNode) searchNode((BalancedTreeNode) root, oldValue);
        if (node != null) {
            node.setValue(newValue);
        }
        balance((BalancedTreeNode) root);
    }

    @Override
    public void traverse(String order, StringBuilder result) {
        if ("preorder".equalsIgnoreCase(order)) {
            preorder((BalancedTreeNode) root, result);
        } else if ("postorder".equalsIgnoreCase(order)) {
            postorder((BalancedTreeNode) root, result);
        }
    }

    private void preorder(BalancedTreeNode node, StringBuilder result) {
        if (node == null) return;
        result.append(node.getValue()).append(" ");
        for (BalancedTreeNode child : node.children) {
            preorder(child, result);
        }
    }

    private void postorder(BalancedTreeNode node, StringBuilder result) {
        if (node == null) return;
        for (BalancedTreeNode child : node.children) {
            postorder(child, result);
        }
        result.append(node.getValue()).append(" ");
    }

    @Override
    public boolean search(int value) {
        return searchNode((BalancedTreeNode) root, value) != null;
    }

    private BalancedTreeNode searchNode(BalancedTreeNode node, int value) {
        if (node == null) return null;
        if (node.getValue() == value) return node;
        for (BalancedTreeNode child : node.children) {
            BalancedTreeNode result = searchNode(child, value);
            if (result != null) return result;
        }
        return null;
    }

    private void balance(BalancedTreeNode node) {
        if (node == null) return;
        Collections.sort(node.children, Comparator.comparingInt(TreeNode::getValue));
        for (BalancedTreeNode child : node.children) {
            balance(child);
        }
    }
}
