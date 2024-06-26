package model;

public class EmployeeHoursByPayPeriod {
    private int empId;
    private String employeeName; // Add this field
    private double totalHours;
    private double overtimeTotalHours;

    // Constructor
    public EmployeeHoursByPayPeriod(int empId, String employeeName, double totalHours, double overtimeTotalHours) {
        this.empId = empId;
        this.employeeName = employeeName;
        this.totalHours = totalHours;
        this.overtimeTotalHours = overtimeTotalHours;
    }

    // Getters and Setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public double getOvertimeTotalHours() {
        return overtimeTotalHours;
    }

    public void setOvertimeTotalHours(double overtimeTotalHours) {
        this.overtimeTotalHours = overtimeTotalHours;
    }
}
