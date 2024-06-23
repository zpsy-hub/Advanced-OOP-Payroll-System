package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Overtime {
    private int overtimeId;
    private int empId;
    private LocalDate date;
    private String status;
    private LocalTime startTime;
    private LocalTime endTime;
    private double totalHours;
    private String reason;
    private LocalDate dateApproved;
    private int overtimeTypeId;
    private String overtimeTypeName;
    private String employeeName; 

    public Overtime() {
        // Default constructor
    }

    // Constructor with all fields except employeeName
    public Overtime(int overtimeId, int empId, LocalDate date, String status, LocalTime startTime, LocalTime endTime,
                    double totalHours, String reason, LocalDate dateApproved, int overtimeTypeId, String overtimeTypeName) {
        this.overtimeId = overtimeId;
        this.empId = empId;
        this.date = date;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalHours = totalHours;
        this.reason = reason;
        this.dateApproved = dateApproved;
        this.overtimeTypeId = overtimeTypeId;
        this.overtimeTypeName = overtimeTypeName;
    }

    // Constructor with employeeName included
    public Overtime(int overtimeId, int empId, LocalDate date, String status, LocalTime startTime, LocalTime endTime,
                    double totalHours, String reason, LocalDate dateApproved, int overtimeTypeId, String overtimeTypeName,
                    String employeeName) {
        this(overtimeId, empId, date, status, startTime, endTime, totalHours, reason, dateApproved, overtimeTypeId, overtimeTypeName);
        this.employeeName = employeeName;
    }

    // Getters and Setters
    public int getOvertimeId() {
        return overtimeId;
    }

    public void setOvertimeId(int overtimeId) {
        this.overtimeId = overtimeId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(LocalDate dateApproved) {
        this.dateApproved = dateApproved;
    }

    public int getOvertimeTypeId() {
        return overtimeTypeId;
    }

    public void setOvertimeTypeId(int overtimeTypeId) {
        this.overtimeTypeId = overtimeTypeId;
    }

    public String getOvertimeTypeName() {
        return overtimeTypeName;
    }

    public void setOvertimeTypeName(String overtimeTypeName) {
        this.overtimeTypeName = overtimeTypeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "Overtime{" +
                "overtimeId=" + overtimeId +
                ", empId=" + empId +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalHours=" + totalHours +
                ", reason='" + reason + '\'' +
                ", dateApproved=" + dateApproved +
                ", overtimeTypeId=" + overtimeTypeId +
                ", overtimeTypeName='" + overtimeTypeName + '\'' +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }
}
