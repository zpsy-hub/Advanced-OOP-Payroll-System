package service;

import java.sql.*;

public class SQL_client {
    private static SQL_client instance = null;
    public static Connection conn = null;

    private SQL_client() {
    }

    public static SQL_client getInstance() {
        if (instance == null) {
            instance = new SQL_client();
        }
        return instance;
    }

    public static Connection getConnection() {
        String connectionUrl = "jdbc:mysql://127.0.0.1:3306/?user=root";
        try {
            conn = DriverManager.getConnection(connectionUrl, "root", "DF.w}=;$CLn+84?m]r(M%Q"); 
            return conn;
        } catch (SQLException e) {
            // Handle any errors
            e.printStackTrace();
            return null;
        }
    }
}
