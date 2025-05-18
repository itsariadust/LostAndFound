package views;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginView() {
        setTitle("Lost and Found System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 280);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel headerLabel = new JLabel("LOST AND FOUND SYSTEM", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel subHeaderLabel = new JLabel("LOGIN", SwingConstants.CENTER);
        subHeaderLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.add(headerLabel);
        headerPanel.add(subHeaderLabel);

        // Form fields
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        // Buttons
        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Event listeners
        exitButton.addActionListener(e -> System.exit(0));
        loginButton.addActionListener(e -> attemptLogin());
    }

    private void attemptLogin() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        // TODO: Add authentication logic
        System.out.println("Attempting login with: " + username);
        usernameField.setText("");
        passwordField.setText("");
        dispose(); // Close login window
        new MainView().setVisible(true); // Open main view
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView ui = new LoginView();
            ui.setVisible(true);
        });
    }
}