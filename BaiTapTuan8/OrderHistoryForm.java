package BaiTapTuan8;

import javax.swing.*;

public class OrderHistoryForm extends JFrame {
    public OrderHistoryForm() {
        setTitle("Lịch sử mua hàng");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Lịch sử mua hàng của bạn!", SwingConstants.CENTER);
        add(label);
    }
}
