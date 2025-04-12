package BaiTapTuan8;

import java.sql.Date;

class Order {
    private int id;
    private int customerId;
    private Date orderDate;
    
    public Order(int id, int customerId, Date orderDate) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }
    
    // Getter và Setter
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public Date getOrderDate() { return orderDate; }
}