import java.util.ArrayList;
import java.util.List;

public class GenericTree extends Tree {
    class GenericTreeNode extends TreeNode {
        List<GenericTreeNode> children;

        public GenericTreeNode(int value) {
            super(value);
            this.children = new ArrayList<>();
        }
    }

    public GenericTree() {
        this.root = null;
    }

    @Override
    public void insert(int value) {
        if (root == null) {
            root = new GenericTreeNode(value);
        } else {
            insert((GenericTreeNode) root, value);
        }
    }

    public void insert(int value, Integer parentValue) {
        if (parentValue == null) {
            insert(value);
        } else {
            GenericTreeNode parentNode = (GenericTreeNode) searchNode((GenericTreeNode) root, parentValue);
            if (parentNode != null) {
                parentNode.children.add(new GenericTreeNode(value));
            }
        }
    }

    private void insert(GenericTreeNode node, int value) {
        node.children.add(new GenericTreeNode(value));
    }

    @Override
    public void delete(int value) {
        root = delete((GenericTreeNode) root, value);
    }

    private GenericTreeNode delete(GenericTreeNode node, int value) {
        if (node == null) return null;
        if (node.getValue() == value) return null;

        List<GenericTreeNode> newChildren = new ArrayList<>();
        for (GenericTreeNode child : node.children) {
            GenericTreeNode result = delete(child, value);
            if (result != null) {
                newChildren.add(result);
            }
        }
        node.children = newChildren;
        return node;
    }

    @Override
    public void update(int oldValue, int newValue) {
        GenericTreeNode node = (GenericTreeNode) searchNode((GenericTreeNode) root, oldValue);
        if (node != null) {
            node.setValue(newValue);
        }
    }

    @Override
    public void traverse(String order, StringBuilder result) {
        if ("preorder".equalsIgnoreCase(order)) {
            preorder((GenericTreeNode) root, result);
        } else if ("postorder".equalsIgnoreCase(order)) {
            postorder((GenericTreeNode) root, result);
        }
    }

    private void preorder(GenericTreeNode node, StringBuilder result) {
        if (node == null) return;
        result.append(node.getValue()).append(" ");
        for (GenericTreeNode child : node.children) {
            preorder(child, result);
        }
    }

    private void postorder(GenericTreeNode node, StringBuilder result) {
        if (node == null) return;
        for (GenericTreeNode child : node.children) {
            postorder(child, result);
        }
        result.append(node.getValue()).append(" ");
    }

    @Override
    public boolean search(int value) {
        return searchNode((GenericTreeNode) root, value) != null;
    }

    private GenericTreeNode searchNode(GenericTreeNode node, int value) {
        if (node == null) return null;
        if (node.getValue() == value) return node;
        for (GenericTreeNode child : node.children) {
            GenericTreeNode result = searchNode(child, value);
            if (result != null) return result;
        }
        return null;
    }
}
