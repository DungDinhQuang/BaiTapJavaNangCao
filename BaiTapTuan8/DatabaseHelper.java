package BaiTapTuan8;

import java.sql.*;
import java.util.Optional;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=OrderManagement;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    // Kết nối đến cơ sở dữ liệu
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Tạo đơn hàng mới và trả về ID của đơn hàng
    public static Optional<Integer> createOrder(int customerId) {
        String query = "INSERT INTO orders (customer_id, order_date) OUTPUT INSERTED.id VALUES (?, GETDATE())";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Thêm sản phẩm vào bảng order_items
    public static boolean addOrderItem(int orderId, int productId, int quantity) {
        String query = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm sản phẩm vào giỏ hàng
    public static void addToCart(int customerId, int productId, int quantity) {
        String query = "MERGE INTO cart AS target " +
                       "USING (SELECT ? AS customer_id, ? AS product_id, ? AS quantity) AS source " +
                       "ON target.customer_id = source.customer_id AND target.product_id = source.product_id " +
                       "WHEN MATCHED THEN " +
                       "    UPDATE SET target.quantity = target.quantity + source.quantity " +
                       "WHEN NOT MATCHED THEN " +
                       "    INSERT (customer_id, product_id, quantity) VALUES (source.customer_id, source.product_id, source.quantity);";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


