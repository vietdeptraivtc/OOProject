import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class TreeVisualizerApp {
    private JFrame frame;
    private JComboBox<String> treeTypeComboBox, traverseOrderComboBox;
    private JButton insertButton, deleteButton, updateButton, traverseButton, searchButton, createButton;
    private JButton pauseButton, continueButton, stepForwardButton, stepBackwardButton, undoButton, redoButton, helpButton, quitButton, backButton;
    private JPanel treePanel;
    private JTextField nodeValueField, parentNodeField, oldValueField, newValueField;
    private JTextArea codePanel;
    private JProgressBar progressBar;
    private Tree currentTree;
    private DefaultTreeModel treeModel;
    private JTree jTree;

    public TreeVisualizerApp() {
        frame = new JFrame("Tree Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(new GridLayout(3, 2));

        treeTypeComboBox = new JComboBox<>(new String[]{"Generic Tree", "Binary Tree", "Balanced Tree", "Balanced Binary Tree"});
        controlPanel.add(new JLabel("Tree Type:"));
        controlPanel.add(treeTypeComboBox);

        createButton = new JButton("Create Tree");
        createButton.addActionListener(e -> createTree());
        controlPanel.add(createButton);

        traverseOrderComboBox = new JComboBox<>(new String[]{"Preorder", "Postorder", "Inorder"});
        controlPanel.add(new JLabel("Traversal Order:"));
        controlPanel.add(traverseOrderComboBox);

        insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> performInsert());
        controlPanel.add(insertButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> performDelete());
        controlPanel.add(deleteButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> performUpdate());
        controlPanel.add(updateButton);

        traverseButton = new JButton("Traverse");
        traverseButton.addActionListener(e -> performTraverse());
        controlPanel.add(traverseButton);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
        controlPanel.add(searchButton);

        nodeValueField = new JTextField();
        controlPanel.add(new JLabel("Node Value:"));
        controlPanel.add(nodeValueField);

        parentNodeField = new JTextField();
        controlPanel.add(new JLabel("Parent Node (for Generic Tree):"));
        controlPanel.add(parentNodeField);

        oldValueField = new JTextField();
        controlPanel.add(new JLabel("Old Value:"));
        controlPanel.add(oldValueField);

        newValueField = new JTextField();
        controlPanel.add(new JLabel("New Value:"));
        controlPanel.add(newValueField);

        frame.add(controlPanel, BorderLayout.NORTH);

        treePanel = new JPanel(new BorderLayout());
        frame.add(treePanel, BorderLayout.CENTER);

        codePanel = new JTextArea(5, 30);
        frame.add(new JScrollPane(codePanel), BorderLayout.SOUTH);

        progressBar = new JProgressBar();
        frame.add(progressBar, BorderLayout.EAST);

        frame.setVisible(true);
    }

    private void createTree() {
        String selectedTree = treeTypeComboBox.getSelectedItem().toString();
        if ("Generic Tree".equals(selectedTree)) {
            currentTree = new GenericTree();
        } else if ("Binary Tree".equals(selectedTree)) {
            currentTree = new BinaryTree();
        } else if ("Balanced Tree".equals(selectedTree)) {
            currentTree = new BalancedTree();
        } else if ("Balanced Binary Tree".equals(selectedTree)) {
            currentTree = new BalancedBinaryTree();
        }
        updateTreeVisualization(currentTree.getRoot());
    }


    private void performInsert() {
        try {
            int value = Integer.parseInt(nodeValueField.getText());
            String parentValueStr = parentNodeField.getText();
            Integer parentValue = parentValueStr.isEmpty() ? null : Integer.parseInt(parentValueStr);

            if (currentTree instanceof GenericTree) {
                if (parentValue == null) {
                    currentTree.insert(value);
                } else {
                    ((GenericTree) currentTree).insert(value, parentValue);
                }
            } else if (currentTree instanceof BinaryTree ||
                    currentTree instanceof BalancedBinaryTree ||
                    currentTree instanceof BalancedTree) {
                currentTree.insert(value);
            } else {
                JOptionPane.showMessageDialog(frame, "Unsupported tree type.");
            }

            updateTreeVisualization(currentTree.getRoot());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid integer.");
        }
    }


    private void performDelete() {
        int value = Integer.parseInt(nodeValueField.getText());
        currentTree.delete(value);
        updateTreeVisualization(currentTree.getRoot());
    }

    private void performUpdate() {
        int oldValue = Integer.parseInt(oldValueField.getText());
        int newValue = Integer.parseInt(newValueField.getText());
        currentTree.update(oldValue, newValue);
        updateTreeVisualization(currentTree.getRoot());
    }

    private void performTraverse() {
        String order = traverseOrderComboBox.getSelectedItem().toString().toLowerCase();
        StringBuilder traversalResult = new StringBuilder(order + " traversal: ");
        currentTree.traverse(order, traversalResult);
        JOptionPane.showMessageDialog(frame, traversalResult.toString());
        codePanel.setText(traversalResult.toString());
    }

    private void performSearch() {
        int value = Integer.parseInt(nodeValueField.getText());
        boolean found = currentTree.search(value);
        String message = "Node " + value + (found ? " found." : " not found.");
        JOptionPane.showMessageDialog(frame, message);
        codePanel.setText(message);
    }

    private void updateTreeVisualization(TreeNode root) {
        DefaultMutableTreeNode swingRoot = TreeNodeAdapter.adapt(root);
        treeModel = new DefaultTreeModel(swingRoot);
        if (jTree == null) {
            jTree = new JTree(treeModel);
            treePanel.add(new JScrollPane(jTree), BorderLayout.CENTER);
        } else {
            jTree.setModel(treeModel);
        }
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TreeVisualizerApp::new);
    }
}
