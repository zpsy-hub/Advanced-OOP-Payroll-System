package model;

import java.sql.Date;

public class Leave {
    private int empId;
    private int leaveTypeId;
    private int year;
    private Date dateSubmitted;
    private Date startDate;
    private Date endDate;
    private int daysTaken;
    private Date dateApproved;
    private int leaveDaysRemaining;

    // Constructor
    public Leave(int empId, int leaveTypeId, int year, Date dateSubmitted, Date startDate, Date endDate, int daysTaken, Date dateApproved, int leaveDaysRemaining) {
        this.empId = empId;
        this.leaveTypeId = leaveTypeId;
        this.year = year;
        this.dateSubmitted = dateSubmitted;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysTaken = daysTaken;
        this.dateApproved = dateApproved;
        this.leaveDaysRemaining = leaveDaysRemaining;
    }

    // Getters and Setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDaysTaken() {
        return daysTaken;
    }

    public void setDaysTaken(int daysTaken) {
        this.daysTaken = daysTaken;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    public int getLeaveDaysRemaining() {
        return leaveDaysRemaining;
    }

    public void setLeaveDaysRemaining(int leaveDaysRemaining) {
        this.leaveDaysRemaining = leaveDaysRemaining;
    }
}
