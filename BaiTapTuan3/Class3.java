package BaiTapTuan3;

import java.util.Random;

class BakeryStore {
    private int breadCount = 35; // Số bánh mì hiện có trong cửa hàng
    private final int MAX_BREAD = 50; // Giới hạn bánh mì tối đa trong cửa hàng

    // Hàm thêm bánh mì (Producer)
    public synchronized void restockBread() {
        while (breadCount >= MAX_BREAD) {
            try {
                System.out.println("🔴 Cửa hàng đã đầy bánh mì. Lò bánh mì tạm dừng sản xuất...");
                wait(); // Đợi đến khi có khách mua
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Xác định số lượng bánh cần giao
        int needed = MAX_BREAD - breadCount;
        int deliveryAmount = Math.min(needed, 35); // Giao tối đa 35 bánh nhưng không vượt quá 50 cái

        breadCount += deliveryAmount;
        System.out.println("🥖 Lò bánh mì đã giao " + deliveryAmount + " bánh. Tổng trong cửa hàng: " + breadCount);
        
        notifyAll(); // Thông báo cho khách hàng có thể mua bánh
    }

    // Hàm mua bánh mì (Consumer)
    public synchronized void buyBread(int amount) {
        while (breadCount < amount) {
            System.out.println("⚠️ Hết bánh mì! Khách hàng phải đợi...");
            try {
                wait(); // Chờ đến khi có bánh
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        breadCount -= amount;
        System.out.println("🛒 Khách hàng mua " + amount + " bánh. Còn lại: " + breadCount);
        
        notifyAll(); // Thông báo lò bánh mì có thể sản xuất nếu cần
    }
}

// Lò bánh mì (Producer)
class Bakery extends Thread {
    private final BakeryStore store;

    public Bakery(BakeryStore store) {
        this.store = store;
    }

    @Override
    public void run() {
        while (true) {
            store.restockBread();
            try {
                Thread.sleep(10000); // Giả lập thời gian sản xuất và giao hàng (7 giây)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Khách hàng (Consumer)
class Customer extends Thread {
    private final BakeryStore store;
    private final Random random = new Random();

    public Customer(BakeryStore store) {
        this.store = store;
    }

    @Override
    public void run() {
        while (true) {
            int amount = random.nextInt(10) + 1; // Khách mua ngẫu nhiên từ 1-10 bánh
            store.buyBread(amount);
            try {
                Thread.sleep(random.nextInt(10000) + 1000); // Mỗi khách đến sau 1-10 giây
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Chương trình chính
public class Class3 {
    public static void main(String[] args) {
        BakeryStore store = new BakeryStore();

        // Tạo 1 lò bánh mì
        Bakery bakery = new Bakery(store);
        bakery.start();

        // Tạo 5 khách hàng
        for (int i = 1; i <= 5; i++) {
            new Customer(store).start();
        }
    }
}

