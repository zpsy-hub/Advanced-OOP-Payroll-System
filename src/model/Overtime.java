package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Overtime {
    private int overtimeId;
    private int empId;
    private LocalDate date;
    private String status;
    private LocalTime start;
    private LocalTime end;
    private double totalHours;
    private String reason;
    private LocalDate dateApproved;

    // Constructors, getters, and setters
    public Overtime() {
    }

    public Overtime(int overtimeId, int empId, LocalDate date, String status, LocalTime start, LocalTime end, double totalHours, String reason, LocalDate dateApproved) {
        this.overtimeId = overtimeId;
        this.empId = empId;
        this.date = date;
        this.status = status;
        this.start = start;
        this.end = end;
        this.totalHours = totalHours;
        this.reason = reason;
        this.dateApproved = dateApproved;
    }

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

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
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
}
