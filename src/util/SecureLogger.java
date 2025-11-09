package util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Secure logging utility that prevents information disclosure through error messages.
 * 
 * Security Features:
 * - Never logs passwords, credentials, or sensitive data
 * - Provides generic error messages to end users
 * - Logs detailed errors to secure file for debugging
 * - Sanitizes exception stack traces
 * - Implements log rotation to prevent file bloat
 * 
 * Usage:
 *   SecureLogger.logError("EmployeeDAO.addEmployee", e);
 *   SecureLogger.logWarning("SessionManager", "Session expired for user");
 *   SecureLogger.logInfo("LoginDAO", "User authenticated successfully");
 */
public class SecureLogger {
    
    private static final String LOG_FILE_PATH = "logs/application.log";
    private static final String ERROR_LOG_PATH = "logs/error.log";
    private static final long MAX_LOG_SIZE = 10 * 1024 * 1024; // 10MB
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    // Generic user-facing error messages (no technical details)
    public static final String GENERIC_ERROR_MESSAGE = "An error occurred while processing your request. Please try again or contact support.";
    public static final String DATABASE_ERROR_MESSAGE = "Unable to connect to the database. Please try again later.";
    public static final String AUTHENTICATION_ERROR_MESSAGE = "Authentication failed. Please check your credentials.";
    public static final String AUTHORIZATION_ERROR_MESSAGE = "You do not have permission to perform this action.";
    public static final String VALIDATION_ERROR_MESSAGE = "Invalid input provided. Please check your data and try again.";
    
    // Sensitive data patterns to sanitize
    private static final String[] SENSITIVE_KEYWORDS = {
        "password", "passwd", "pwd", "secret", "token", "key", "credential",
        "ssn", "social_security", "credit_card", "cvv", "pin"
    };
    
    static {
        // Ensure log directory exists
        try {
            Files.createDirectories(Paths.get("logs"));
        } catch (IOException e) {
            System.err.println("Failed to create logs directory: " + e.getMessage());
        }
    }
    
    /**
     * Log an error with exception details (secure, no sensitive data).
     * 
     * @param context The class/method where error occurred (e.g., "EmployeeDAO.addEmployee")
     * @param exception The exception that was caught
     */
    public static void logError(String context, Exception exception) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String sanitizedMessage = sanitizeMessage(exception.getMessage());
        String logEntry = String.format("[ERROR] %s | %s | %s: %s%n", 
            timestamp, context, exception.getClass().getSimpleName(), sanitizedMessage);
        
        writeToLog(ERROR_LOG_PATH, logEntry);
        
        // Also log stack trace to error log only (never to console or user-facing output)
        if (exception.getStackTrace().length > 0) {
            StringBuilder stackTrace = new StringBuilder();
            stackTrace.append("Stack trace: ");
            for (int i = 0; i < Math.min(5, exception.getStackTrace().length); i++) {
                StackTraceElement element = exception.getStackTrace()[i];
                stackTrace.append("\n  at ").append(element.toString());
            }
            writeToLog(ERROR_LOG_PATH, stackTrace.toString() + "\n");
        }
    }
    
    /**
     * Log an error with custom message (no exception).
     * 
     * @param context The class/method where error occurred
     * @param message Custom error message
     */
    public static void logError(String context, String message) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String sanitizedMessage = sanitizeMessage(message);
        String logEntry = String.format("[ERROR] %s | %s | %s%n", 
            timestamp, context, sanitizedMessage);
        
        writeToLog(ERROR_LOG_PATH, logEntry);
    }
    
    /**
     * Log a warning message.
     * 
     * @param context The class/method issuing warning
     * @param message Warning message
     */
    public static void logWarning(String context, String message) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String sanitizedMessage = sanitizeMessage(message);
        String logEntry = String.format("[WARN] %s | %s | %s%n", 
            timestamp, context, sanitizedMessage);
        
        writeToLog(LOG_FILE_PATH, logEntry);
    }
    
    /**
     * Log an informational message.
     * 
     * @param context The class/method logging info
     * @param message Info message
     */
    public static void logInfo(String context, String message) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String sanitizedMessage = sanitizeMessage(message);
        String logEntry = String.format("[INFO] %s | %s | %s%n", 
            timestamp, context, sanitizedMessage);
        
        writeToLog(LOG_FILE_PATH, logEntry);
    }
    
    /**
     * Log a security event (authentication, authorization, etc.).
     * 
     * @param context The security context
     * @param message Security event message
     */
    public static void logSecurityEvent(String context, String message) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String sanitizedMessage = sanitizeMessage(message);
        String logEntry = String.format("[SECURITY] %s | %s | %s%n", 
            timestamp, context, sanitizedMessage);
        
        writeToLog(LOG_FILE_PATH, logEntry);
    }
    
    /**
     * Sanitize message to remove sensitive data patterns.
     * 
     * @param message Original message
     * @return Sanitized message
     */
    private static String sanitizeMessage(String message) {
        if (message == null) {
            return "null";
        }
        
        String sanitized = message;
        
        // Remove sensitive keywords and their values
        for (String keyword : SENSITIVE_KEYWORDS) {
            // Pattern: keyword=value or keyword: value
            sanitized = sanitized.replaceAll("(?i)" + keyword + "\\s*[=:]\\s*[^\\s,;]+", keyword + "=***REDACTED***");
        }
        
        // Limit message length to prevent log flooding
        if (sanitized.length() > 500) {
            sanitized = sanitized.substring(0, 497) + "...";
        }
        
        return sanitized;
    }
    
    /**
     * Write log entry to file with rotation support.
     * 
     * @param filePath Log file path
     * @param logEntry Log entry to write
     */
    private static void writeToLog(String filePath, String logEntry) {
        try {
            // Check if log rotation needed
            if (Files.exists(Paths.get(filePath))) {
                long fileSize = Files.size(Paths.get(filePath));
                if (fileSize > MAX_LOG_SIZE) {
                    rotateLog(filePath);
                }
            }
            
            // Append to log file
            Files.write(
                Paths.get(filePath),
                logEntry.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
            
        } catch (IOException e) {
            // Fallback to console if file logging fails
            System.err.println("Failed to write to log file: " + e.getMessage());
            System.err.println("Log entry: " + logEntry);
        }
    }
    
    /**
     * Rotate log file when it exceeds maximum size.
     * 
     * @param filePath Log file to rotate
     */
    private static void rotateLog(String filePath) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String archivedPath = filePath.replace(".log", "_" + timestamp + ".log");
            Files.move(Paths.get(filePath), Paths.get(archivedPath));
            
            // Log rotation event
            String rotationMessage = String.format("Log file rotated: %s -> %s%n", filePath, archivedPath);
            Files.write(
                Paths.get(filePath),
                rotationMessage.getBytes(),
                StandardOpenOption.CREATE
            );
            
        } catch (IOException e) {
            System.err.println("Failed to rotate log file: " + e.getMessage());
        }
    }
    
    /**
     * Get generic error message for user display (no technical details).
     * 
     * @param exceptionType Type of exception (for message selection)
     * @return User-friendly error message
     */
    public static String getUserFriendlyMessage(Exception exception) {
        if (exception == null) {
            return GENERIC_ERROR_MESSAGE;
        }
        
        String exceptionName = exception.getClass().getSimpleName().toLowerCase();
        
        if (exceptionName.contains("sql") || exceptionName.contains("database")) {
            return DATABASE_ERROR_MESSAGE;
        } else if (exceptionName.contains("auth") || exceptionName.contains("login")) {
            return AUTHENTICATION_ERROR_MESSAGE;
        } else if (exceptionName.contains("permission") || exceptionName.contains("access")) {
            return AUTHORIZATION_ERROR_MESSAGE;
        } else if (exceptionName.contains("validation") || exceptionName.contains("illegal")) {
            return VALIDATION_ERROR_MESSAGE;
        }
        
        return GENERIC_ERROR_MESSAGE;
    }
}
