package Bai1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginForm() {
        setTitle("Đăng nhập");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblUser = new JLabel("Tên đăng nhập:");
        JLabel lblPass = new JLabel("Mật khẩu:");
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        JButton btnLogin = new JButton("Đăng nhập");
        JButton btnRegister = new JButton("Đăng ký");

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(lblUser); panel.add(txtUsername);
        panel.add(lblPass); panel.add(txtPassword);
        panel.add(btnLogin); panel.add(btnRegister);

        add(panel);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> {
            new RegisterForm().setVisible(true);
            dispose();
        });
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword());
        String hashedPassword = HashUtil.hashPassword(password);


        try (Connection conn = DBConnection.getConnection()) {
        	String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	stmt.setString(1, username);
        	stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}

