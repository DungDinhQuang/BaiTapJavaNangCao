package BaiTapTuan5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Class3 {
    public static void main(String[] args) {
        String sourceFile = "C:\\Java save files\\input.txt";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            int lineCount = 0;
            while (reader.readLine() != null) {
                lineCount++;
            }
            
            System.out.println("Số dòng trong tệp: " + lineCount);
        } catch (IOException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
