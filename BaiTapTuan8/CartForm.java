package BaiTapTuan8;

import javax.swing.*;

public class CartForm extends JFrame {
    public CartForm() {
        setTitle("Giỏ hàng");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Đây là giỏ hàng của bạn!", SwingConstants.CENTER);
        add(label);
    }
}
