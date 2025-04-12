package BaiTapTuan3;

class PrintNumbers {
    private int num = 1;
    private final int MAX = 9999; 
    private boolean isOddTurn = true; 

    public synchronized void printOdd() {
        while (num < MAX) {
            while (!isOddTurn) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(num);
            num++;
            isOddTurn = false;
            notify();
        }
    }

    public synchronized void printEven() {
        while (num <= MAX) {
            while (isOddTurn) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(num);
            num++;
            isOddTurn = true;
            notify();
        }
    }
}

public class Class1 {
    public static void main(String[] args) {
        PrintNumbers printer = new PrintNumbers();

        Thread oddThread = new Thread(printer::printOdd);
        Thread evenThread = new Thread(printer::printEven);

        oddThread.start();
        evenThread.start();
    }
}
