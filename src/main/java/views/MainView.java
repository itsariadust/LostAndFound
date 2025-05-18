package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JTable itemsTable;
    private JTable claimsTable;
    private JTabbedPane tabbedPane;
    private JComboBox<String> filterCombo;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton printButton;

    public MainView() {
        /*
            Window Settings
         */
        setTitle("Lost and Found System");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        /*
            Main Panel
         */
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        /*
            Header
         */
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

        /*
            Search & Filter Toolbar
         */
        JPanel searchToolbar = new JPanel(new GridBagLayout());
        GridBagConstraints searchGbc = new GridBagConstraints();
        searchGbc.insets = new Insets(2, 2, 2, 2);
        searchGbc.fill = GridBagConstraints.HORIZONTAL;

        // Search field
        JTextField searchField = new JTextField();
        searchGbc.gridx = 0;
        searchGbc.gridy = 0;
        searchGbc.weightx = 0.7;
        searchToolbar.add(searchField, searchGbc);

        // Search button
        JButton searchButton = new JButton("Search");
        searchGbc.gridx = 1;
        searchGbc.weightx = 0.1;
        searchToolbar.add(searchButton, searchGbc);

        // Status filter
        searchGbc.gridx = 2;
        searchGbc.weightx = 0;
        searchToolbar.add(new JLabel("Status:"), searchGbc);

        // Filter button
        JButton filterButton = new JButton("Filter");
        searchGbc.gridx = 4;
        searchGbc.weightx = 0.1;
        searchToolbar.add(filterButton, searchGbc);

        // Add toolbar to main panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(searchToolbar, gbc);

        /*
            Data Toolbar
         */
        JPanel dataToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dataToolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Add buttons to data toolbar
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        printButton = new JButton("Print Report");

        dataToolBar.add(addButton);
        dataToolBar.add(editButton);
        dataToolBar.add(deleteButton);
        dataToolBar.add(printButton);

        // Add to main panel (position 2)
        gbc.gridx = 0;
        gbc.gridy = 2;  // Now this is below search toolbar (y=1)
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 5);  // Add some bottom margin
        mainPanel.add(dataToolBar, gbc);

        /*
            Tabbed Pane
         */
        tabbedPane = new JTabbedPane();

        // Lost Items tab
        JPanel lostItemsPanel = new JPanel(new BorderLayout());
        itemsTable = new JTable(100, 5)  {
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        claimsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollClaimsPane = new JScrollPane(claimsTable);
        claimsPanel.add(scrollClaimsPane, BorderLayout.CENTER);
        tabbedPane.addTab("Claims", claimsPanel);

        // Add tabbed pane to main panel
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // This makes it take remaining space
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tabbedPane, gbc);

        /*
            Split Pane
         */
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(600); // Initial divider position
        splitPane.setResizeWeight(0.8); // 70% space for tabbed pane, 30% for details

        // Add your existing tabbed pane to the left
        splitPane.setLeftComponent(tabbedPane);

        // Create the details panel for the right side
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Record Details"));
        detailsPanel.setPreferredSize(new Dimension(300, 0)); // Initial width

        // Add the details panel to the right of the split pane
        splitPane.setRightComponent(detailsPanel);

        // Replace your existing mainPanel.add(tabbedPane) with:
        // Add split pane with tabbed pane and details panel (position 3)
        gbc.gridy = 3;  // Now this is below both toolbars (y=1 and y=2)
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // This makes it take remaining space
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(splitPane, gbc);

        filterCombo = new JComboBox<>();
        updateFilterOptions(tabbedPane.getSelectedIndex()); // Initialize with first tab's options

        // Add tab change listener
        tabbedPane.addChangeListener(e -> {
            updateFilterOptions(tabbedPane.getSelectedIndex());
        });
        searchGbc.gridx = 3;
        searchGbc.weightx = 0.1;
        searchToolbar.add(filterCombo, searchGbc);

        add(mainPanel);

        /*
            Event Listeners
         */

        //
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });

        tabbedPane.addChangeListener(e -> {
            updateButtonActions(tabbedPane.getSelectedIndex());
        });

        // Call this initially to set up first tab's actions
        updateButtonActions(tabbedPane.getSelectedIndex());

        filterButton.addActionListener(e -> {
            String selectedFilter = (String) filterCombo.getSelectedItem();
            int selectedTab = tabbedPane.getSelectedIndex();

            if (selectedTab == 0) { // Lost Items tab
                filterLostItems(selectedFilter);
            } else if (selectedTab == 1) { // Claims tab
                filterClaims(selectedFilter);
            }
        });
    }

    private void updateFilterOptions(int selectedTabIndex) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        if (selectedTabIndex == 0) { // Lost Items tab
            model.addElement("All");
            model.addElement("Unclaimed");
            model.addElement("Claimed");
        } else if (selectedTabIndex == 1) { // Claims tab
            model.addElement("All");
            model.addElement("Pending");
            model.addElement("Approved");
            model.addElement("Denied");
        }

        filterCombo.setModel(model);
        filterCombo.setSelectedIndex(0);
    }

    private void updateButtonActions(int selectedTabIndex) {
        // Remove all existing action listeners
        removeAllActionListeners();

        if (selectedTabIndex == 0) { // Lost Items tab
            addButton.addActionListener(e -> addLostItem());
            editButton.addActionListener(e -> editLostItem());
            deleteButton.addActionListener(e -> deleteLostItem());
            printButton.addActionListener(e -> printLostItemsReport());
        }
        else if (selectedTabIndex == 1) { // Claims tab
            addButton.addActionListener(e -> addClaim());
            editButton.addActionListener(e -> editClaim());
            deleteButton.addActionListener(e -> deleteClaim());
            printButton.addActionListener(e -> printClaimsReport());
        }

        // Update button text if needed
        if (selectedTabIndex == 1) {
            addButton.setText("Add Claim");
            editButton.setText("Edit Claim");
            deleteButton.setText("Delete Claim");
            printButton.setText("Print Claim");
        } else {
            addButton.setText("Add Item");
            editButton.setText("Edit Item");
            deleteButton.setText("Delete Item");
            printButton.setText("Print Item");
        }
    }

    private void removeAllActionListeners() {
        removeActionListenersFrom(addButton, editButton, deleteButton, printButton);
    }

    private void removeActionListenersFrom(JButton... buttons) {
        for (JButton button : buttons) {
            for (ActionListener al : button.getActionListeners()) {
                button.removeActionListener(al);
            }
        }
    }

    // Lost Items tab actions
    private void addLostItem() {
        System.out.println("Adding new lost item");
        // Your implementation here
    }

    private void editLostItem() {
        System.out.println("Editing lost item");
        // Your implementation here
    }

    private void deleteLostItem() {
        System.out.println("Editing lost item");
        // Your implementation here
    }

    private void printLostItemsReport() {
        System.out.println("Editing lost item");
        // Your implementation here
    }

    // Claims tab actions
    private void addClaim() {
        System.out.println("Adding new claim");
        // Your implementation here
    }

    private void editClaim() {
        System.out.println("Editing claim");
        // Your implementation here
    }

    private void deleteClaim() {
        System.out.println("Editing claim");
        // Your implementation here
    }

    private void printClaimsReport() {
        System.out.println("Editing claim");
        // Your implementation here
    }

    private void filterLostItems(String filter) {
        System.out.println("Filtering Lost Items by: " + filter);
        // Todo: filter out lost items
    }

    private void filterClaims(String filter) {
        System.out.println("Filtering Claims by: " + filter);
        // Todo: filter out claims
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView ui = new MainView();
            ui.setVisible(true);
        });
    }
}