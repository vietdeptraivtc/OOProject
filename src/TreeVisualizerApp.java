import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class TreeVisualizerApp {
    private JFrame frame;
    private JComboBox<String> treeTypeComboBox, traverseOrderComboBox;
    private JButton insertButton, deleteButton, updateButton, traverseButton, searchButton, createButton;
    private JButton helpButton, exitButton; // New buttons
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

        JPanel controlPanel = new JPanel(new GridLayout(7, 2)); // Adjusted for more buttons

        treeTypeComboBox = new JComboBox<>(new String[]{"Generic Tree", "Binary Tree", "Balanced Tree", "Balanced Binary Tree"});
        controlPanel.add(new JLabel("Tree Type:"));
        controlPanel.add(treeTypeComboBox);

        createButton = new JButton("Create Tree");
        createButton.addActionListener(e -> createTree());
        controlPanel.add(createButton);

        controlPanel.add(new JLabel("Node Value:"));
        nodeValueField = new JTextField();
        controlPanel.add(nodeValueField);

        controlPanel.add(new JLabel("Parent Node (for Generic Tree):"));
        parentNodeField = new JTextField();
        controlPanel.add(parentNodeField);

        insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> performInsert());
        controlPanel.add(insertButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> performDelete());
        controlPanel.add(deleteButton);

        controlPanel.add(new JLabel("Old Value:"));
        oldValueField = new JTextField();
        controlPanel.add(oldValueField);

        controlPanel.add(new JLabel("New Value:"));
        newValueField = new JTextField();
        controlPanel.add(newValueField);

        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> performUpdate());
        controlPanel.add(updateButton);

        traverseOrderComboBox = new JComboBox<>(new String[]{"Preorder", "Inorder", "Postorder"});
        controlPanel.add(new JLabel("Traversal Order:"));
        controlPanel.add(traverseOrderComboBox);

        traverseButton = new JButton("Traverse");
        traverseButton.addActionListener(e -> performTraverse());
        controlPanel.add(traverseButton);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
        controlPanel.add(searchButton);

        // Adding Help and Exit buttons
        helpButton = new JButton("Help");
        helpButton.addActionListener(e -> showHelp());
        controlPanel.add(helpButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exitApplication());
        controlPanel.add(exitButton);

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

    private void showHelp() {
        JOptionPane.showMessageDialog(frame, "This is a tree visualizer application.\n" +
                "You can create different types of trees, insert nodes, delete nodes,\n" +
                "update nodes, traverse the tree, and search for nodes.\n" +
                "Select the tree type and perform operations accordingly.");
    }

    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
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
