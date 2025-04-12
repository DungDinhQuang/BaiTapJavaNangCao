package BaiTapTuan8;

//Lớp OrderItem ánh xạ bảng order_items
class OrderItem {
 private int id;
 private int orderId;
 private int productId;
 private int quantity;
 
 public OrderItem(int id, int orderId, int productId, int quantity) {
     this.id = id;
     this.orderId = orderId;
     this.productId = productId;
     this.quantity = quantity;
 }
 
 // Getter và Setter
 public int getId() { return id; }
 public int getOrderId() { return orderId; }
 public int getProductId() { return productId; }
 public int getQuantity() { return quantity; }
}
