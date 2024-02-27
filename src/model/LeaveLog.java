package model;

public class LeaveLog {
    private String date;
    private String id;
    private String lastName;
    private String firstName;
    private String leaveType;
    private String startDate;
    private String endDate;
    private int totalDays;
    private int remainingBalance;
    private String status;

    // Constructor
    public LeaveLog(String date, String id, String lastName, String firstName, String leaveType,
                    String startDate, String endDate, int totalDays, int remainingBalance, String status) {
        this.date = date;
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalDays = totalDays;
        this.remainingBalance = remainingBalance;
        this.status = status;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(int remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   
}
