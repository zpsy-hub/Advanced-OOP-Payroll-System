package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Duration;
import model.User;

/**
 * Secure session management with timeout, validation, and proper logout.
 * 
 * Security Features:
 * - Session timeout after 30 minutes of inactivity
 * - Last activity timestamp tracking
 * - Proper session invalidation on logout
 * - Session validation before sensitive operations
 * - Login attempt logging
 * 
 * Usage:
 *   SessionManager.getInstance().login(username, password, conn);
 *   if (SessionManager.getInstance().hasActiveSession()) { ... }
 *   SessionManager.getInstance().logout();
 */
public class SessionManager {
    
    private static SessionManager instance;
    private static User loggedInUser;
    private static LocalDateTime lastActivityTime;
    private static final int SESSION_TIMEOUT_MINUTES = 30;
    
    private DAO.LoginDAO userRepository;

    private SessionManager(DAO.LoginDAO userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Get singleton instance of SessionManager.
     * 
     * @param userRepository LoginDAO instance for authentication
     * @return SessionManager instance
     */
    public static synchronized SessionManager getInstance(DAO.LoginDAO userRepository) {
        if (instance == null) {
            instance = new SessionManager(userRepository);
        }
        return instance;
    }
    
    /**
     * Get singleton instance (requires previous initialization).
     * 
     * @return SessionManager instance or null if not initialized
     */
    public static SessionManager getInstance() {
        return instance;
    }

    /**
     * Authenticate user and create session.
     * 
     * @param username User's username
     * @param password User's password
     * @param conn Database connection for logging
     * @return true if authentication successful, false otherwise
     */
    public boolean login(String username, String password, Connection conn) {
        try {
            // Authenticate user
            loggedInUser = userRepository.authenticateUser(username, password);
            
            if (loggedInUser != null) {
                // Initialize session
                lastActivityTime = LocalDateTime.now();
                
                // Get the employee ID of the logged-in user
                int employeeId = userRepository.getEmployeeIdByUsername(username);
                Integer loggedInUserEmployeeId = (employeeId > 0) ? employeeId : null;
                
                // Log successful login attempt
                logLoginAttempt(conn, loggedInUserEmployeeId, username, true);
                SecureLogger.logSecurityEvent("SessionManager.login", 
                    "User '" + username + "' logged in successfully (empId: " + employeeId + ")");
                
                return true;
            } else {
                // Log failed login attempt
                logLoginAttempt(conn, null, username, false);
                SecureLogger.logSecurityEvent("SessionManager.login", 
                    "Failed login attempt for username: " + username);
                
                return false;
            }
        } catch (Exception e) {
            SecureLogger.logError("SessionManager.login", e);
            return false;
        }
    }

    /**
     * Logout user and invalidate session.
     */
    public void logout() {
        if (loggedInUser != null) {
            String username = loggedInUser.getUsername();
            SecureLogger.logSecurityEvent("SessionManager.logout", 
                "User '" + username + "' logged out");
        }
        
        // Clear session data
        loggedInUser = null;
        lastActivityTime = null;
        
        SecureLogger.logInfo("SessionManager.logout", "Session invalidated");
    }

    /**
     * Check if user has an active session.
     * Automatically invalidates session if timeout exceeded.
     * 
     * @return true if session is active and valid, false otherwise
     */
    public boolean hasActiveSession() {
        if (loggedInUser == null || lastActivityTime == null) {
            return false;
        }
        
        // Check session timeout
        Duration inactivityDuration = Duration.between(lastActivityTime, LocalDateTime.now());
        long inactiveMinutes = inactivityDuration.toMinutes();
        
        if (inactiveMinutes >= SESSION_TIMEOUT_MINUTES) {
            // Session expired
            SecureLogger.logSecurityEvent("SessionManager.hasActiveSession", 
                "Session expired for user '" + loggedInUser.getUsername() + 
                "' after " + inactiveMinutes + " minutes of inactivity");
            logout();
            return false;
        }
        
        // Update last activity time
        updateActivity();
        return true;
    }

    /**
     * Require active session or throw exception.
     * Use this before sensitive operations.
     * 
     * @throws SecurityException if session is invalid or expired
     */
    public void requireActiveSession() throws SecurityException {
        if (!hasActiveSession()) {
            SecureLogger.logSecurityEvent("SessionManager.requireActiveSession", 
                "Access denied: No active session");
            throw new SecurityException("No active session. Please login first.");
        }
    }

    /**
     * Update last activity timestamp (call on user actions).
     */
    public void updateActivity() {
        if (loggedInUser != null) {
            lastActivityTime = LocalDateTime.now();
        }
    }

    /**
     * Get remaining session time in minutes.
     * 
     * @return minutes until session expires, or 0 if no active session
     */
    public long getRemainingSessionMinutes() {
        if (!hasActiveSession()) {
            return 0;
        }
        
        Duration inactivityDuration = Duration.between(lastActivityTime, LocalDateTime.now());
        long inactiveMinutes = inactivityDuration.toMinutes();
        return Math.max(0, SESSION_TIMEOUT_MINUTES - inactiveMinutes);
    }

    /**
     * Log login attempt in the database.
     * 
     * @param conn Database connection
     * @param empId Employee ID (null if unknown)
     * @param username Username attempted
     * @param success Whether login was successful
     */
    private void logLoginAttempt(Connection conn, Integer empId, String username, boolean success) {
        try {
            String query = "INSERT INTO payrollsystem_db.login_attempts (emp_id, username, timestamp, success) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
            
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, empId != null ? empId : 0); 
                ps.setString(2, username);
                ps.setBoolean(3, success); 
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            SecureLogger.logError("SessionManager.logLoginAttempt", e);
        }
    }

    /**
     * Get logged-in user (if session is active).
     * 
     * @return User object or null if no active session
     */
    public static User getLoggedInUser() {
        // Validate session before returning user
        if (instance != null && instance.hasActiveSession()) {
            return loggedInUser;
        }
        return null;
    }

    /**
     * Get logged-in user without session validation (legacy support).
     * WARNING: Use getLoggedInUser() instead for security.
     * 
     * @param sessionManager SessionManager instance
     * @return User object or null
     * @deprecated Use getLoggedInUser() instead
     */
    @Deprecated
    public static User getLoggedInUser(SessionManager sessionManager) {
        return getLoggedInUser();
    }

    /**
     * Get session timeout in minutes (for display purposes).
     * 
     * @return session timeout in minutes
     */
    public static int getSessionTimeoutMinutes() {
        return SESSION_TIMEOUT_MINUTES;
    }
}

