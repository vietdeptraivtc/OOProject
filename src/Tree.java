public abstract class Tree {
    protected TreeNode root;

    public abstract void insert(int value);

    public abstract void delete(int value);

    public abstract void update(int oldValue, int newValue);

    public abstract void traverse(String order, StringBuilder result);

    public abstract boolean search(int value);

    public TreeNode getRoot() {
        return root;
    }
}
