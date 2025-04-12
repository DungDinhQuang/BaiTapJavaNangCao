package BaiTapTuan8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private int customerId = -1; 

    public LoginForm() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(new JLabel());
        panel.add(loginButton);
        add(panel);

        loginButton.addActionListener((ActionEvent e) -> checkLogin());
    }

    private void checkLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (authenticateUser(email, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            new MainForm(customerId).setVisible(true); 
            this.dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean authenticateUser(String email, String password) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=OrderManagement;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String pass = "12345";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT id FROM customers WHERE email = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                customerId = rs.getInt("id");
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
