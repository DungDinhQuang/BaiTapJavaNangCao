package BaiTapTuan3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class TicketBookingSystem {
    private final List<String> availableSeats;

    public TicketBookingSystem() {

        availableSeats = new ArrayList<>();
        char[] coaches = {'A', 'B', 'C', 'D'};  
        for (char coach : coaches) {
            for (int i = 1; i <= 12; i++) {
                availableSeats.add(coach + String.valueOf(i));
            }
        }

        Collections.shuffle(availableSeats);
    }


    public synchronized String bookSeat(String agentName) {
        if (availableSeats.isEmpty()) {
            return "Hết vé!";
        }
        String seat = availableSeats.remove(0);
        return "✅ " + agentName + " đã đặt thành công chỗ: " + seat;
    }

    public boolean hasSeatsAvailable() {
        return !availableSeats.isEmpty();
    }
}


class TicketAgent extends Thread {
    private final TicketBookingSystem system;
    private final String agentName;
    private final Random random = new Random();

    public TicketAgent(TicketBookingSystem system, String agentName) {
        this.system = system;
        this.agentName = agentName;
    }

    @Override
    public void run() {
        while (system.hasSeatsAvailable()) {
            String result = system.bookSeat(agentName);
            System.out.println(result);
            try {
                Thread.sleep(random.nextInt(500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Lớp chính để chạy chương trình
public class Class2 {
    public static void main(String[] args) {
        TicketBookingSystem system = new TicketBookingSystem();

        // Tạo 5 đại lý bán vé (Thread)
        Thread agent1 = new TicketAgent(system, "Đại lý Hoa sen");
        Thread agent2 = new TicketAgent(system, "Đại lý Tàu giá rẻ");
        Thread agent3 = new TicketAgent(system, "Đại lý La bàn");
        Thread agent4 = new TicketAgent(system, "Đại lý Xuyên Việt");
        Thread agent5 = new TicketAgent(system, "Đại lý Thuận Tiến");

        // Bắt đầu đặt vé
        agent1.start();
        agent2.start();
        agent3.start();
        agent4.start();
        agent5.start();

        // Chờ tất cả các đại lý hoàn thành
        try {
            agent1.join();
            agent2.join();
            agent3.join();
            agent4.join();
            agent5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("🎉 Tất cả vé đã được đặt!");
    }
}
