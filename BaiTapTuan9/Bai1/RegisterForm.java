package Bai1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class RegisterForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;

    public RegisterForm() {
        setTitle("Đăng ký tài khoản");
        setSize(350, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblUser = new JLabel("Tên đăng nhập:");
        JLabel lblPass = new JLabel("Mật khẩu:");
        JLabel lblConfirm = new JLabel("Nhập lại mật khẩu:");

        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        txtConfirmPassword = new JPasswordField(15);

        JButton btnRegister = new JButton("Đăng ký");
        JButton btnBack = new JButton("Quay lại");

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(lblUser); panel.add(txtUsername);
        panel.add(lblPass); panel.add(txtPassword);
        panel.add(lblConfirm); panel.add(txtConfirmPassword);
        panel.add(btnRegister); panel.add(btnBack);

        add(panel);

        btnRegister.addActionListener(e -> register());
        btnBack.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }

    private void register() {
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword());
        String confirmPassword = String.valueOf(txtConfirmPassword.getPassword());



        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không khớp. Vui lòng nhập lại.");
            return;
        }

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {

            String checkSql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại.");
                return;
            }


            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            String hashedPassword = HashUtil.hashPassword(password);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            new LoginForm().setVisible(true);
            dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi đăng ký.");
        }
    }
}
