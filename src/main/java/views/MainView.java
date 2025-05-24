package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import controllers.LostItemController;
import controllers.ClaimsController;
import models.LostItemsTableModel;

public class MainView extends JFrame {
    // Components
    private JMenuBar menuBar;
    private JMenu fileMenu, helpMenu;
    private JMenu addMenu;
    private JMenuItem logoutMenuItem;
    private JMenuItem exitMenuItem;
    private JTable itemsTable;
    private JTable claimsTable;
    private JTabbedPane tabbedPane;
    private JComboBox<String> filterCombo;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton saveItemButton;
    private JButton saveClaimButton;
    private JButton cancelItemEditButton;
    private JButton cancelClaimEditButton;
    private JButton addImageItemButton;
    private JLabel imageLabel;
    private JComboBox<String> statusComboBox;

    // Information fields
    private ArrayList<JComponent> lostItemsFields = new ArrayList<>();
    private ArrayList<JComponent> claimsFields = new ArrayList<>();

    // And inputs
    private Map<String, JComponent> lostItemsInputs = new LinkedHashMap<>();
    private Map<String, JComponent> claimsInputs = new LinkedHashMap<>();
    String itemImage;

    // Information Panels
    private JPanel lostItemsDetailsPanel;
    private JPanel claimsDetailsPanel;
    private JPanel currentDetailsPanel;

    // Flag
    private enum OperationMode { ADD, EDIT, VIEW }
    private OperationMode currentMode = OperationMode.VIEW;

    // Table model
    private LostItemsTableModel lostItemsTableModel;

    public MainView(String userName) {
        /*
            ----
            Window Settings
            ----
         */
        setTitle("Lost and Found System");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        /*
            ----
            Menu Bar
            ----
         */
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        addMenu = new JMenu("Add");
        fileMenu.add(addMenu);
        fileMenu.addSeparator();
        logoutMenuItem = new JMenuItem("Logout");
        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(logoutMenuItem);
        fileMenu.add(exitMenuItem);
        setJMenuBar(menuBar);

        /*
            ----
            Main Panel
            ----
         */
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        /*
            ----
            Header
            ----
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
        userPanel.add(new JLabel("Welcome, " + userName));
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
            ----
            Search & Filter Toolbar
            ----
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
            ----
            Data Toolbar
            ----
         */
        JPanel dataToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dataToolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Add buttons to data toolbar
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        dataToolBar.add(addButton);
        dataToolBar.add(editButton);
        dataToolBar.add(deleteButton);

        // Add to main panel (position 2)
        gbc.gridx = 0;
        gbc.gridy = 2;  // Now this is below search toolbar (y=1)
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 5);  // Add some bottom margin
        mainPanel.add(dataToolBar, gbc);

        /*
            ----
            Tabbed Pane
            ----
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
            ----
            Split Pane
            ----
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

        // Filter Combobox
        filterCombo = new JComboBox<>();
        updateFilterOptions(tabbedPane.getSelectedIndex()); // Initialize with first tab's options
        searchGbc.gridx = 3;
        searchGbc.weightx = 0.1;
        searchToolbar.add(filterCombo, searchGbc);

        add(mainPanel);

        /*
            ----
            Event Listeners
            ----
         */

        // Menu Listeners
        logoutMenuItem.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });

        exitMenuItem.addActionListener(e -> {
            System.exit(0);
        });

        // Logout listener
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });

        // Tabbed pane listener
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();

            updateFilterOptions(selectedIndex);
            updateStatusOptions(selectedIndex);

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

        // Cancel edit buttons
        cancelItemEditButton.addActionListener(e -> {
            // Clear all lost items fields
            clearFields(lostItemsFields);
            imageLabel.setIcon(null);

            // Hide buttons
            addImageItemButton.setVisible(false);
            saveItemButton.setVisible(false);
            cancelItemEditButton.setVisible(false);

            // Disable fields
            setFieldsEnabled(lostItemsFields, false);
        });

        cancelClaimEditButton.addActionListener(e -> {
            // Clear all claims fields
            clearFields(claimsFields);

            // Hide buttons
            saveClaimButton.setVisible(false);
            cancelClaimEditButton.setVisible(false);

            // Disable fields
            setFieldsEnabled(claimsFields, false);
        });

        // Filter button listener
        filterButton.addActionListener(e -> {
            String selectedFilter = (String) filterCombo.getSelectedItem();
            int selectedTab = tabbedPane.getSelectedIndex();

            if (selectedTab == 0) { // Lost Items tab
                LostItemController.filterLostItems(selectedFilter);
            } else if (selectedTab == 1) { // Claims tab
                ClaimsController.filterClaims(selectedFilter);
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
            model.addElement("Disposed");
        } else if (selectedTabIndex == 1) { // Claims tab
            model.addElement("All");
            model.addElement("Pending");
            model.addElement("Approved");
            model.addElement("Denied");
        }

        filterCombo.setModel(model);
        filterCombo.setSelectedIndex(0);
    }

    private void updateStatusOptions (int selectedTabIndex) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        if (selectedTabIndex == 0) { // Lost Items tab
            model.addElement("Unclaimed");
            model.addElement("Claimed");
            model.addElement("Disposed");
        } else if (selectedTabIndex == 1) { // Claims tab
            model.addElement("Pending");
            model.addElement("Approved");
            model.addElement("Denied");
        }

        statusComboBox.setModel(model);
        statusComboBox.setSelectedIndex(0);
    }

    private void updateButtonActions(int selectedTabIndex) {
        // Remove all existing action listeners
        removeAllActionListeners();

        if (selectedTabIndex == 0) { // Lost Items tab
            addButton.addActionListener(e -> {
                currentMode = OperationMode.ADD;
                clearFields(lostItemsFields);
                setFieldsEnabled(lostItemsFields, true);
                addImageItemButton.setVisible(true);
                saveItemButton.setVisible(true);
                cancelItemEditButton.setVisible(true);
            });
            editButton.addActionListener(e -> {
                currentMode = OperationMode.EDIT;
                setFieldsEnabled(lostItemsFields, true);
                saveItemButton.setVisible(true);
                cancelItemEditButton.setVisible(true);
            });
            deleteButton.addActionListener(e -> LostItemController.deleteLostItem());
            saveItemButton.addActionListener(e -> {
                Map<String, String> itemData = getFieldValues(lostItemsInputs);
                // Process the data (save to database, etc.)
                if (currentMode == OperationMode.ADD) {
                    new LostItemController();
                    if (!LostItemController.addLostItem(itemData)) {
                        return;
                    }
                    JOptionPane.showMessageDialog(this.rootPane, "Item added successfully.",
                            "Add Item Record Success", JOptionPane.INFORMATION_MESSAGE);
                } else if (currentMode == OperationMode.EDIT) {
                    LostItemController.editLostItem(itemData); // editLostItem(itemData);
                }

                // Reset UI
                setFieldsEnabled(lostItemsFields, false);
                saveItemButton.setVisible(false);
                cancelItemEditButton.setVisible(false);
            });
        }
        else if (selectedTabIndex == 1) { // Claims tab
            addButton.addActionListener(e -> {
                currentMode = OperationMode.ADD;
                clearFields(claimsFields);
                setFieldsEnabled(claimsFields, true);
                saveClaimButton.setVisible(true);
                cancelClaimEditButton.setVisible(true);
            });
            editButton.addActionListener(e -> {
                currentMode = OperationMode.EDIT;
                setFieldsEnabled(claimsFields, true);
                saveClaimButton.setVisible(true);
                cancelClaimEditButton.setVisible(true);
            });
            deleteButton.addActionListener(e -> ClaimsController.deleteClaim());
            saveClaimButton.addActionListener(e -> {
                Map<String, String> claimData = getFieldValues(claimsInputs);
                // Process the data
                if (currentMode == OperationMode.ADD) {
                    ClaimsController.addClaim(claimData);
                } else if (currentMode == OperationMode.EDIT) {
                    ClaimsController.editClaim(claimData); // editClaim(itemData);
                }

                // Reset UI
                setFieldsEnabled(claimsFields, false);
                saveClaimButton.setVisible(false);
                cancelClaimEditButton.setVisible(false);
            });
        }

        // Update button text if needed
        if (selectedTabIndex == 1) {
            addButton.setText("Add Claim");
            editButton.setText("Edit Claim");
            deleteButton.setText("Delete Claim");
        } else {
            addButton.setText("Add Item");
            editButton.setText("Edit Item");
            deleteButton.setText("Delete Item");
        }
    }

    private void removeAllActionListeners() {
        removeActionListenersFrom(addButton, editButton, deleteButton);
    }

    private void removeActionListenersFrom(JButton... buttons) {
        for (JButton button : buttons) {
            for (ActionListener al : button.getActionListeners()) {
                button.removeActionListener(al);
            }
        }
    }

    private void initializeDetailsPanels() {
        /*
            ----
            Panel for Lost Items
            ----
         */
        lostItemsDetailsPanel = new JPanel(new GridBagLayout());
        lostItemsDetailsPanel.setBorder(BorderFactory.createTitledBorder("Item Details"));
        GridBagConstraints itemsGbc = new GridBagConstraints();
        itemsGbc.insets = new Insets(5, 5, 5, 5);
        itemsGbc.fill = GridBagConstraints.HORIZONTAL;
        itemsGbc.anchor = GridBagConstraints.NORTHWEST; // Align content to top-left

        // Create content panel for fields
        JPanel lostItemsContent = new JPanel(new GridBagLayout());
        String[] lostItemsLabels = {"Item Name", "Description", "Category",
                "Location", "Date Found", "Found By", "Status"};
        addContentToPanels(itemsGbc, lostItemsLabels, lostItemsContent, lostItemsDetailsPanel, true);

        /*
            ----
            Panel for Claims
            ----
         */
        claimsDetailsPanel = new JPanel(new GridBagLayout());
        claimsDetailsPanel.setBorder(BorderFactory.createTitledBorder("Claim Details"));
        GridBagConstraints claimsGbc = new GridBagConstraints();
        claimsGbc.insets = new Insets(5, 5, 5, 5);
        claimsGbc.fill = GridBagConstraints.HORIZONTAL;
        claimsGbc.anchor = GridBagConstraints.NORTHWEST; // Align content to top-left

        // Create content panel for fields
        JPanel claimsContent = new JPanel(new GridBagLayout());
        String[] claimsLabels = {"Claimant Name", "Item Claimed", "Claim Date",
                "Ownership Proof", "Status", "Approved By", "Approval Date"};
        addContentToPanels(claimsGbc, claimsLabels, claimsContent, claimsDetailsPanel, false);

        // Set initial panel
        currentDetailsPanel = lostItemsDetailsPanel;
    }

    private void addContentToPanels(GridBagConstraints gbc, String[] labels, JPanel content,
                                    JPanel panel, boolean isLostItemsPanel) {
        // Build fields
        buildFields(gbc, labels, content, isLostItemsPanel);

        // Reset constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Only add image components for lost items panel
        if (isLostItemsPanel) {
            // Add image button (row 0)
            addImageItemButton = new JButton("Add Image");
            addImageItemButton.setVisible(false);
            panel.add(addImageItemButton, gbc);

            // Add image label (row 1)
            gbc.gridy++;
            imageLabel = new JLabel();
            imageLabel.setPreferredSize(new Dimension(400, 400));
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);
            panel.add(imageLabel, gbc);
        }

        // Add the content panel (row 2 or row 0 for claims)
        gbc.gridy++;
        gbc.weighty = 1.0; // Take remaining space
        panel.add(content, gbc);

        // Add save button (row 3 or row 1 for claims)
        gbc.gridy++;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton saveButton = new JButton("Save");
        saveButton.setVisible(false);
        panel.add(saveButton, gbc);

        // Add cancel button (row 4 or row 2 for claims)
        gbc.gridy++;
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setVisible(false);
        panel.add(cancelButton, gbc);

        // Set button references
        if (isLostItemsPanel) {
            this.saveItemButton = saveButton;
            this.cancelItemEditButton = cancelButton;

            // Add action listener for image button
            addImageItemButton.addActionListener(e -> {
                this.itemImage = ImagePicker.selectImageFile(this, imageLabel);
            });
        } else {
            this.saveClaimButton = saveButton;
            this.cancelClaimEditButton = cancelButton;
        }
    }

    private void buildFields(GridBagConstraints gbc, String[] fieldLabels, JPanel panel,
                             boolean isLostItemsPanel) {
        gbc.gridy = 0;

        for (String fieldLabel : fieldLabels) {
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            panel.add(new JLabel(fieldLabel), gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;

            if (fieldLabel.equals("Description") || fieldLabel.equals("Ownership Proof")) {
                // TextArea configuration
                JTextArea textArea = new JTextArea(3, 15);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);
                textArea.setFocusable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);

                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 1.0; // Allow vertical expansion
                panel.add(scrollPane, gbc);

                if (isLostItemsPanel) {
                    lostItemsFields.add(scrollPane);
                    lostItemsInputs.put(fieldLabel, textArea);
                } else {
                    claimsFields.add(scrollPane);
                    claimsInputs.put(fieldLabel, textArea);
                }
            } else if (fieldLabel.equals("Status")) {
                statusComboBox = new JComboBox<>();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 0.0; // No vertical expansion
                statusComboBox.setEnabled(false);
                updateStatusOptions(tabbedPane.getSelectedIndex());
                panel.add(statusComboBox, gbc);
                if (isLostItemsPanel) {
                    lostItemsFields.add(statusComboBox);
                    lostItemsInputs.put(fieldLabel, statusComboBox);
                } else {
                    claimsFields.add(statusComboBox);
                    claimsInputs.put(fieldLabel, statusComboBox);
                }
            } else {
                // TextField configuration
                JTextField textField = new JTextField(15);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 0.0; // No vertical expansion
                textField.setEditable(false);
                textField.setFocusable(false);
                panel.add(textField, gbc);

                if (isLostItemsPanel) {
                    lostItemsFields.add(textField);
                    lostItemsInputs.put(fieldLabel, textField);
                } else {
                    claimsFields.add(textField);
                    claimsInputs.put(fieldLabel, textField);
                }
            }

            // Reset constraints for next row
            gbc.gridy++;
            gbc.weighty = 0.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
        }
    }

    private void clearFields(ArrayList<JComponent> fields) {
        for (JComponent field : fields) {
            if (field instanceof JScrollPane) {
                JTextArea textArea = (JTextArea)((JScrollPane)field).getViewport().getView();
                textArea.setText("");
            } else if (field instanceof JTextField) {
                ((JTextField)field).setText("");
            }
        }
    }

    private void setFieldsEnabled(ArrayList<JComponent> fields, boolean enabled) {
        for (JComponent field : fields) {
            if (field instanceof JScrollPane) {
                JTextArea textArea = (JTextArea)((JScrollPane)field).getViewport().getView();
                textArea.setFocusable(enabled);
                textArea.setEditable(enabled);
            } else if (field instanceof JTextField) {
                ((JTextField)field).setEditable(enabled);
                field.setFocusable(enabled);
            }
            field.setEnabled(enabled);
        }
    }

    private Map<String, String> getFieldValues(Map<String, JComponent> fieldInputs) {
        Map<String, String> values = new LinkedHashMap<>();
        fieldInputs.forEach((label, component) -> {
            if (component instanceof JTextArea) {
                values.put(label, ((JTextArea) component).getText());
            } else if (component instanceof JTextField) {
                values.put(label, ((JTextField) component).getText());
            } else if (component instanceof JComboBox<?>) {
                values.put(label, ((JComboBox<?>) component).getSelectedItem().toString());
            }
        });
        if (fieldInputs == this.lostItemsInputs) {
            values.put("ImagePath", itemImage); // Add image path if exists
        }
        return values;
    }
}