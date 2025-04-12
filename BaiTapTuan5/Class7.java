package BaiTapTuan5;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class FileChunkReader implements Runnable {
    private final String filePath;
    private final long start;
    private final long size;
    private final byte[] buffer;
    private final int index;
    private final byte[][] results;
    
    public FileChunkReader(String filePath, long start, long size, int index, byte[][] results) {
        this.filePath = filePath;
        this.start = start;
        this.size = size;
        this.index = index;
        this.results = results;
        this.buffer = new byte[(int) size];
    }
    
    @Override
    public void run() {
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            raf.seek(start);
            raf.readFully(buffer);
            results[index] = buffer;
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc phần " + index + " của file: " + e.getMessage());
        }
    }
}

public class Class7 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Nhập đường dẫn file cần đọc: ");
            String inputFile = reader.readLine();
            
            File file = new File(inputFile);
            long fileSize = file.length();
            long chunkSize = 1024 * 1024;
            int numChunks = (int) Math.ceil((double) fileSize / chunkSize);
            
            byte[][] results = new byte[numChunks][];
            ExecutorService executor = Executors.newFixedThreadPool(4);
            
            for (int i = 0; i < numChunks; i++) {
                long start = i * chunkSize;
                long size = Math.min(chunkSize, fileSize - start);
                executor.execute(new FileChunkReader(inputFile, start, size, i, results));
            }
            
            executor.shutdown();
            while (!executor.isTerminated()) {}
            
            System.out.print("Nhập đường dẫn file đích: ");
            String outputFile = reader.readLine();
            
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                for (byte[] chunk : results) {
                    fos.write(chunk);
                }
            }
            
            System.out.println("Ghi file hoàn tất: " + outputFile);
        } catch (IOException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}

