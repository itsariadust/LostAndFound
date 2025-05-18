package views;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JTable itemsTable;
    private JTable claimsTable;
    private JTabbedPane tabbedPane;

    public MainView() {
        setTitle("Lost and Found System");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel - using BoxLayout for vertical arrangement
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Header with user info
        JPanel headerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.insets = new Insets(5, 5, 5, 5);

        // Title label
        JLabel titleLabel = new JLabel("LOST AND FOUND SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerGbc.gridx = 0;
        headerGbc.gridy = 0;
        headerGbc.weightx = 1.0;
        headerGbc.anchor = GridBagConstraints.WEST;
        headerPanel.add(titleLabel, headerGbc);

        // User panel with logout button
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.add(new JLabel("Welcome"));
        JButton logoutButton = new JButton("Logout");
        userPanel.add(logoutButton);

        headerGbc.gridx = 1;
        headerGbc.weightx = 0;
        headerGbc.anchor = GridBagConstraints.EAST;
        headerPanel.add(userPanel, headerGbc);

        // Add header to main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(headerPanel, gbc);

        // Toolbar with search components
        JPanel toolBar = new JPanel(new GridBagLayout());
        GridBagConstraints toolGbc = new GridBagConstraints();
        toolGbc.insets = new Insets(2, 2, 2, 2);
        toolGbc.fill = GridBagConstraints.HORIZONTAL;

        // Search field
        JTextField searchField = new JTextField();
        toolGbc.gridx = 0;
        toolGbc.gridy = 0;
        toolGbc.weightx = 0.7;
        toolBar.add(searchField, toolGbc);

        // Search button
        JButton searchButton = new JButton("Search");
        toolGbc.gridx = 1;
        toolGbc.weightx = 0.1;
        toolBar.add(searchButton, toolGbc);

        // Status filter
        toolGbc.gridx = 2;
        toolGbc.weightx = 0;
        toolBar.add(new JLabel("Status:"), toolGbc);

        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"All", "Unclaimed", "Claimed"});
        toolGbc.gridx = 3;
        toolGbc.weightx = 0.1;
        toolBar.add(filterCombo, toolGbc);

        // Filter button
        JButton filterButton = new JButton("Filter");
        toolGbc.gridx = 4;
        toolGbc.weightx = 0.1;
        toolBar.add(filterButton, toolGbc);

        // Add toolbar to main panel
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(toolBar, gbc);

        // Tabbed pane
        tabbedPane = new JTabbedPane();

        // Lost Items tab
        JPanel lostItemsPanel = new JPanel(new BorderLayout());
        itemsTable = new JTable(100, 5)  {
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        JScrollPane scrollLostPane = new JScrollPane(itemsTable);
        lostItemsPanel.add(scrollLostPane, BorderLayout.CENTER);
        tabbedPane.addTab("Lost Items", lostItemsPanel);

        // Claims tab
        JPanel claimsPanel = new JPanel(new BorderLayout());
        claimsTable = new JTable(100, 5)  {
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        JScrollPane scrollClaimsPane = new JScrollPane(claimsTable);
        claimsPanel.add(scrollClaimsPane, BorderLayout.CENTER);
        tabbedPane.addTab("Claims", claimsPanel);

        // Add tabbed pane to main panel
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // This makes it take remaining space
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tabbedPane, gbc);

        add(mainPanel);

        // Event listeners
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView ui = new MainView();
            ui.setVisible(true);
        });
    }
}