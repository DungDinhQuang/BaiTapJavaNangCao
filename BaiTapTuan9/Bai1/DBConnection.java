package Bai1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=UserDB;encrypt=true;trustServerCertificate=true";
        String user = "SA";
        String password = "12345";

        return DriverManager.getConnection(url, user, password);
    }
}

