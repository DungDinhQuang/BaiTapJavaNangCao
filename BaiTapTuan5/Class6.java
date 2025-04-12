package BaiTapTuan5;

import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class FileReaderThread extends Thread {
    private final String filePath;
    private final FileWriter writer;
    
    public FileReaderThread(String filePath, FileWriter writer) {
        this.filePath = filePath;
        this.writer = writer;
    }
    
    @Override
    public void run() {
        synchronized (writer) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                writer.write("\n--- Nội dung từ file: " + filePath + " ---\n");
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line + "\n");
                }
                writer.write("\n--- Kết thúc file: " + filePath + " ---\n");
            } catch (IOException e) {
                System.out.println("Lỗi khi đọc file " + filePath + ": " + e.getMessage());
            }
        }
    }
}

public class Class6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Nhập số lượng file cần đọc: ");
            int fileCount = scanner.nextInt();
            scanner.nextLine();
            
            List<String> filePaths = new ArrayList<>();
            for (int i = 0; i < fileCount; i++) {
                System.out.print("Nhập đường dẫn file " + (i + 1) + ": ");
                filePaths.add(scanner.nextLine());
            }
            
            String outputFile = "C:\\Java save files\\output.txt";
            FileWriter writer = new FileWriter(outputFile);
            
            List<FileReaderThread> threads = new ArrayList<>();
            for (String filePath : filePaths) {
                FileReaderThread thread = new FileReaderThread(filePath, writer);
                threads.add(thread);
                thread.start();
            }
            
            for (FileReaderThread thread : threads) {
                thread.join();
            }
            
            writer.close();
            System.out.println("Tất cả nội dung đã được ghi vào " + outputFile);
        } catch (Exception e) {
            System.out.println("Lỗi xảy ra: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
