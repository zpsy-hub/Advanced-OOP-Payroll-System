package model;

public class LoginAttempt {
    private int empId;
    private String username;
    private String timestamp;
    private String loginStatus;

    public LoginAttempt(int empId, String username, String timestamp, String loginStatus) {
        this.empId = empId;
        this.username = username;
        this.timestamp = timestamp;
        this.loginStatus = loginStatus;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }
}
