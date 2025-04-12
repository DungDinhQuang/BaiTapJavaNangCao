package BaiTapTuan5;

import java.io.File;
import java.util.Scanner;

public class Class5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Nhập đường dẫn thư mục: ");
            String directoryPath = scanner.nextLine();
            
            File directory = new File(directoryPath);
            
            if (directory.isDirectory()) {
                String[] files = directory.list();
                if (files != null) {
                    System.out.println("Danh sách tệp trong thư mục " + directoryPath + ":");
                    for (String file : files) {
                        System.out.println(file);
                    }
                } else {
                    System.out.println("Không thể lấy danh sách tệp.");
                }
            } else {
                System.out.println(directoryPath + " không phải là một thư mục hợp lệ.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi xảy ra: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
