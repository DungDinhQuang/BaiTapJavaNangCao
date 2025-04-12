package BaiTapTuan5;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class KeywordSearchTask implements Callable<Integer> {
    private final String filePath;
    private final String keyword;
    
    public KeywordSearchTask(String filePath, String keyword) {
        this.filePath = filePath;
        this.keyword = keyword;
    }
    
    @Override
    public Integer call() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                count += countOccurrences(line, keyword);
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file " + filePath + ": " + e.getMessage());
        }
        return count;
    }
    
    private int countOccurrences(String line, String keyword) {
        int count = 0, index = 0;
        while ((index = line.indexOf(keyword, index)) != -1) {
            count++;
            index += keyword.length();
        }
        return count;
    }
}

public class Class8 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Nhập số lượng file cần tìm kiếm: ");
            int fileCount = Integer.parseInt(reader.readLine());
            
            List<String> filePaths = new ArrayList<>();
            for (int i = 0; i < fileCount; i++) {
                System.out.print("Nhập đường dẫn file " + (i + 1) + ": ");
                filePaths.add(reader.readLine());
            }
            
            System.out.print("Nhập từ khóa cần tìm: ");
            String keyword = reader.readLine();
            
            ExecutorService executor = Executors.newFixedThreadPool(4);
            List<Future<Integer>> futures = new ArrayList<>();
            
            for (String filePath : filePaths) {
                futures.add(executor.submit(new KeywordSearchTask(filePath, keyword)));
            }
            
            executor.shutdown();
            int totalOccurrences = 0;
            
            for (int i = 0; i < filePaths.size(); i++) {
                try {
                    int occurrences = futures.get(i).get();
                    System.out.println("Từ khóa xuất hiện " + occurrences + " lần trong file: " + filePaths.get(i));
                    totalOccurrences += occurrences;
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Lỗi khi lấy kết quả: " + e.getMessage());
                }
            }
            
            System.out.println("Tổng số lần xuất hiện của từ khóa: " + totalOccurrences);
        } catch (IOException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}

