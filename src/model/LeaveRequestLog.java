package model;

import java.sql.Date;
import java.sql.Timestamp;

public class LeaveRequestLog {
    private Timestamp timestamp;
    private int id; 
    private String employeeLastName;
    private String employeeFirstName;
    private String leaveType;
    private Date dateStart;
    private Date dateEnd;
    private int daysTotal;
    private int leaveBalance;
    private String status;

    // Constructor
    public LeaveRequestLog(Timestamp timestamp, int id, String employeeLastName, String employeeFirstName, String leaveType,
                    Date dateStart, Date dateEnd, int daysTotal, int leaveBalance, String status) {
        this.timestamp = timestamp;
        this.id = id;
        this.employeeLastName = employeeLastName;
        this.employeeFirstName = employeeFirstName;
        this.leaveType = leaveType;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.daysTotal = daysTotal;
        this.leaveBalance = leaveBalance;
        this.status = status;
    }

    // Getters and Setters
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getDaysTotal() {
        return daysTotal;
    }

    public void setDaysTotal(int daysTotal) {
        this.daysTotal = daysTotal;
    }

    public int getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(int leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "LeaveRequestLog{" +
                "timestamp=" + timestamp +
                ", id=" + id +
                ", employeeLastName='" + employeeLastName + '\'' +
                ", employeeFirstName='" + employeeFirstName + '\'' +
                ", leaveType='" + leaveType + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", daysTotal=" + daysTotal +
                ", leaveBalance=" + leaveBalance +
                ", status='" + status + '\'' +
                '}';
    }
}
