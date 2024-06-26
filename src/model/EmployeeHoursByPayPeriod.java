package model;

public class EmployeeHoursByPayPeriod {
    private int empId;
    private double totalHours;
    private double overtimeTotalHours;

    // Constructor
    public EmployeeHoursByPayPeriod(int empId, double totalHours, double overtimeTotalHours) {
        this.empId = empId;
        this.totalHours = totalHours;
        this.overtimeTotalHours = overtimeTotalHours;
    }

    // Getters and setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
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

    // Override toString() method for debugging or logging purposes
    @Override
    public String toString() {
        return "EmployeeHoursByPayPeriod{" +
                "empId=" + empId +
                ", totalHours=" + totalHours +
                ", overtimeTotalHours=" + overtimeTotalHours +
                '}';
    }

	public String getEmployeeName() {
		// TODO Auto-generated method stub
		return null;
	}
}
