package BaiTapTuan8;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainForm extends JFrame {
    private JPanel productPanel;
    private List<ProductItem> productItems = new ArrayList<>();
    private JButton viewCartButton, viewHistoryButton;
    private int customerId; // Nhận từ LoginForm

    public MainForm(int customerId) {
        this.customerId = customerId; // Lưu ID của khách hàng
        setTitle("Order Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Khu vực danh sách sản phẩm
        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(productPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Thanh điều hướng dưới cùng
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        viewCartButton = new JButton("🛒 Xem giỏ hàng");
        viewHistoryButton = new JButton("📜 Xem lịch sử giao dịch");
        bottomPanel.add(viewCartButton);
        bottomPanel.add(viewHistoryButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Tải sản phẩm từ DB
        loadProducts();

        // Sự kiện nút
        viewCartButton.addActionListener(e -> viewCart());
        viewHistoryButton.addActionListener(e -> viewHistory());
    }

    private void loadProducts() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=OrderManagement;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String pass = "12345";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT id, name, price FROM products";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                ProductItem item = new ProductItem(id, name, price);
                productItems.add(item);
                productPanel.add(item);
            }
            productPanel.revalidate();
            productPanel.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewCart() {
        JOptionPane.showMessageDialog(this, "🔍 Hiển thị giỏ hàng (Chưa code giao diện)", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewHistory() {
        JOptionPane.showMessageDialog(this, "📜 Hiển thị lịch sử giao dịch (Chưa code giao diện)", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainForm(1).setVisible(true)); // Giả sử user ID = 1
    }

    // Lớp hiển thị từng sản phẩm
    class ProductItem extends JPanel {
        private int productId;
        private String productName;
        private double productPrice;
        private JSpinner quantitySpinner;
        private JLabel priceLabel;

        public ProductItem(int id, String name, double price) {
            this.productId = id;
            this.productName = name;
            this.productPrice = price;

            setLayout(new BorderLayout());
            setBorder(BorderFactory.createTitledBorder(name));

            // Khu vực thông tin sản phẩm bên trái
            priceLabel = new JLabel(formatPrice(price));

            // Khu vực số lượng và nút bấm bên phải
            JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
            JButton addToCartButton = new JButton("🛒 Thêm vào giỏ");
            JButton buyNowButton = new JButton("🛍️ Mua ngay");

            controlPanel.add(new JLabel("Số lượng:"));
            controlPanel.add(quantitySpinner);
            controlPanel.add(addToCartButton);
            controlPanel.add(buyNowButton);

            // Thêm hai phần vào panel chính
            add(priceLabel, BorderLayout.WEST);
            add(controlPanel, BorderLayout.EAST);

            // Sự kiện
            quantitySpinner.addChangeListener(e -> updatePrice());
            addToCartButton.addActionListener(e -> addToCart());
            buyNowButton.addActionListener(e -> buyNow());
        }

        private void updatePrice() {
            int quantity = (int) quantitySpinner.getValue();
            double totalPrice = productPrice * quantity;
            priceLabel.setText(formatPrice(totalPrice));
        }
        
        private void addToCart() {
            int quantity = (int) quantitySpinner.getValue();
            DatabaseHelper.addToCart(customerId, productId, quantity);
            JOptionPane.showMessageDialog(this, "Đã thêm " + quantity + " x " + productName + " vào giỏ hàng!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        }


        private void buyNow() {
            int quantity = (int) quantitySpinner.getValue();
            if (DatabaseHelper.addOrderItem(customerId, productId, quantity)) {
                JOptionPane.showMessageDialog(this, "Mua thành công " + quantity + " x " + productName + "!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi mua hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

        private String formatPrice(double price) {
            return String.format("%.0f VND", price);
        }
    }
}
