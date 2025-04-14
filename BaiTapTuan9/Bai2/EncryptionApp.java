package Bai2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EncryptionApp {
    private JFrame frame;
    private JTextField inputField;
    private JTextArea resultArea;
    private JComboBox<String> algorithmComboBox;
    private Encryptable encrptable;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                EncryptionApp window = new EncryptionApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public EncryptionApp() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblInput = new JLabel("Nhập văn bản :");
        lblInput.setBounds(20, 20, 150, 25);
        frame.getContentPane().add(lblInput);

        inputField = new JTextField();
        inputField.setBounds(20, 50, 400, 25);
        frame.getContentPane().add(inputField);
        inputField.setColumns(10);

        JLabel lblAlgorithm = new JLabel("Chọn thuật toán mã hóa:");
        lblAlgorithm.setBounds(20, 80, 150, 25);
        frame.getContentPane().add(lblAlgorithm);

        algorithmComboBox = new JComboBox<>(new String[] {"AES", "RSA"});
        algorithmComboBox.setBounds(20, 110, 100, 25);
        frame.getContentPane().add(algorithmComboBox);

        JButton btnEncrypt = new JButton("Mã hóa");
        btnEncrypt.setBounds(20, 150, 100, 30);
        frame.getContentPane().add(btnEncrypt);

        JButton btnDecrypt = new JButton("Giải mã");
        btnDecrypt.setBounds(130, 150, 100, 30);
        frame.getContentPane().add(btnDecrypt);

        JLabel lblResult = new JLabel("Kết quả:");
        lblResult.setBounds(20, 200, 150, 25);
        frame.getContentPane().add(lblResult);

        resultArea = new JTextArea();
        resultArea.setBounds(20, 230, 400, 100);
        resultArea.setEditable(false);
        frame.getContentPane().add(resultArea);

        // Event for encryption
        btnEncrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();

                if (selectedAlgorithm.equals("AES")) {
                    encrptable = new AESUtil();
                } else if (selectedAlgorithm.equals("RSA")) {
                    encrptable = new RSAUtil();
                }

                String encryptedText = encrptable.encrypt(inputText);
                resultArea.setText("Văn bản sau khi được mã hóa: \n" + encryptedText);
            }
        });

        btnDecrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();

                if (selectedAlgorithm.equals("AES")) {
                    encrptable = new AESUtil();
                } else if (selectedAlgorithm.equals("RSA")) {
                    encrptable = new RSAUtil();
                }

                String decryptedText = encrptable.decrypt(inputText);
                resultArea.setText("Văn bản sau khi được giải mã: \n" + decryptedText);
            }
        });
    }
}
