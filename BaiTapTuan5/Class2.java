package BaiTapTuan5;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Class2 {
    public static void main(String[] args) {
        String destinationFile = "C:\\Java save files\\output.txt"; 
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             FileWriter writer = new FileWriter(destinationFile)) {
            
            System.out.println("Nhập nội dung (nhập 'exit' để kết thúc):");
            String line;
            
            while (!(line = reader.readLine()).equalsIgnoreCase("exit")) {
                writer.write(line + "\n");
            }
            
            System.out.println("Dữ liệu đã được lưu vào " + destinationFile);
        } catch (IOException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
