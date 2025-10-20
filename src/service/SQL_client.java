package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQL_client {
    private static SQL_client instance = null;
    private static Connection conn = null;

    private SQL_client() {
    }

    public static synchronized SQL_client getInstance() {
        if (instance == null) {
            instance = new SQL_client();
        }
        return instance;
    }

    public static synchronized Connection getConnection() {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    return conn;
                }
            } catch (SQLException e) {
                // fall through to re-create connection
            }
        }

        Properties props = new Properties();
        String configPath = "config.properties";
        // load properties if file exists
        if (Files.exists(Paths.get(configPath))) {
            try (FileInputStream fis = new FileInputStream(configPath)) {
                props.load(fis);
            } catch (IOException e) {
                System.err.println("Warning: could not load config.properties: " + e.getMessage());
            }
        }

        String host = props.getProperty("db.host", "127.0.0.1");
        String port = props.getProperty("db.port", "3306");
        String dbName = props.getProperty("db.name", "").trim();
        String user = props.getProperty("db.user", "root");
        String password = props.getProperty("db.password", "");

        String connectionUrl = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC", host, port, dbName);
        // if dbName is empty, remove the trailing slash
        if (dbName.isEmpty()) {
            connectionUrl = String.format("jdbc:mysql://%s:%s/?serverTimezone=UTC", host, port);
        }

        try {
            conn = DriverManager.getConnection(connectionUrl, user, password);
            return conn;
        } catch (SQLException e) {
            // Avoid printing raw stack trace with credentials
            System.err.println("Failed to connect to database: " + e.getMessage());
            return null;
        }
    }
}
