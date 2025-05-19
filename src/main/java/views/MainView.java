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

    // Information Panels
    private JPanel lostItemsDetailsPanel;
    private JPanel claimsDetailsPanel;
    private JPanel currentDetailsPanel;

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
        initializeDetailsPanels(); // Initialize panels depending on tab
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(1280); // Initial divider position
        splitPane.setResizeWeight(0.8); // 80% space for tabbed pane, 30% for details

        // Add your existing tabbed pane to the left
        splitPane.setLeftComponent(tabbedPane);

        // Add the details panel to the right of the split pane
        splitPane.setRightComponent(currentDetailsPanel);

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

        // Logout listener
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });

        // Tabbed pane listener
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            updateButtonActions(selectedIndex);

            // Remove current panel
            splitPane.remove(currentDetailsPanel);

            // Switch to appropriate panel
            if (selectedIndex == 0) { // Lost Items
                currentDetailsPanel = lostItemsDetailsPanel;
            } else { // Claims
                currentDetailsPanel = claimsDetailsPanel;
            }

            // Add new panel and refresh
            splitPane.setRightComponent(currentDetailsPanel);
            splitPane.revalidate();
            splitPane.repaint();

            // Update other tab-specific components
            updateFilterOptions(selectedIndex);
            updateButtonActions(selectedIndex);
        });

        // Filter button listener
        filterButton.addActionListener(e -> {
            String selectedFilter = (String) filterCombo.getSelectedItem();
            int selectedTab = tabbedPane.getSelectedIndex();

            if (selectedTab == 0) { // Lost Items tab
                filterLostItems(selectedFilter);
            } else if (selectedTab == 1) { // Claims tab
                filterClaims(selectedFilter);
            }
        });

        // Call this initially to set up first tab's actions
        updateButtonActions(tabbedPane.getSelectedIndex());
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

    private void initializeDetailsPanels() {
        // Panel for Lost Items
        lostItemsDetailsPanel = new JPanel(new GridBagLayout());
        lostItemsDetailsPanel.setBorder(BorderFactory.createTitledBorder("Item Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] lostItemsLabels = {"Item Name", "Description", "Category", "Location", "Date Found", "Status"};
        buildFields(gbc, lostItemsLabels, lostItemsDetailsPanel);

        // Panel for Claims
        claimsDetailsPanel = new JPanel(new GridBagLayout());
        claimsDetailsPanel.setBorder(BorderFactory.createTitledBorder("Claim Details"));

        String[] claimsLabels = {"Claimant Name", "Item Claimed", "Claim Date", "Status"};
        buildFields(gbc, claimsLabels, claimsDetailsPanel);

        // Set initial panel
        currentDetailsPanel = lostItemsDetailsPanel;
    }

    private void buildFields(GridBagConstraints gbc, String[] lostItemsLabels, JPanel lostItemsDetailsPanel) {
        for (String lostItemsLabel : lostItemsLabels) {
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            lostItemsDetailsPanel.add(new JLabel(lostItemsLabel), gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            JTextField tf = new JTextField(15);
            tf.setEditable(false);
            tf.setFocusable(false);
            lostItemsDetailsPanel.add(tf, gbc);
        }
    }

    // Lost Items tab actions
    private void addLostItem() {
        System.out.println("Adding new lost item");
        // Todo: implementation here
    }

    private void editLostItem() {
        System.out.println("Editing lost item");
        // Todo: implementation here
    }

    private void deleteLostItem() {
        System.out.println("Editing lost item");
        // Todo: implementation here
    }

    private void printLostItemsReport() {
        System.out.println("Editing lost item");
        // Todo: implementation here
    }

    // Claims tab actions
    private void addClaim() {
        System.out.println("Adding new claim");
        // Todo: implementation here
    }

    private void editClaim() {
        System.out.println("Editing claim");
        // Todo: implementation here
    }

    private void deleteClaim() {
        System.out.println("Editing claim");
        // Todo: implementation here
    }

    private void printClaimsReport() {
        System.out.println("Editing claim");
        // Todo: implementation here
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