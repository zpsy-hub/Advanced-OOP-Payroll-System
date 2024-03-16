package service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.User;
import model.UserRole;

public class LoginService {
    private LoginDAO userRepository;
    private String loginLogFilePath = "src/data/Login Log.csv";

    public LoginService(LoginDAO userRepository) {
        this.userRepository = userRepository;
    }

    // Modified login method to return a boolean indicating login success
    public boolean login(String username, String password) {
        User user = userRepository.authenticateUser(username, password);
        // Logging login attempt
        logLoginAttempt(username, user != null);
        return user != null;
    }

    // Method to log login attempt in CSV file
    private void logLoginAttempt(String username, boolean success) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = now.format(formatter);
        try (FileWriter writer = new FileWriter(loginLogFilePath, true)) {
            writer.append(dateTime);
            writer.append(",");
            writer.append(username);
            writer.append(",");
            writer.append(success ? "Success" : "Failed");
            writer.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
