package BaiTapTuan5;

import java.io.*;

public class Class1 {
    public static void main(String[] args) {
        String sourceFile = "C:\\Java save files\\input.txt"; 
        String destinationFile = "C:\\Java save files\\output.txt"; 
        
        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(destinationFile)) {
            
            byte[] buffer = new byte[1024]; 
            int bytesRead;
            
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            
            System.out.println("Sao chép tệp thành công!");
        } catch (IOException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
