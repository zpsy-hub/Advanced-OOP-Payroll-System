package model;

public class LeaveBalance {
    private int empId;
    private String employeeLastName;
    private String employeeFirstName;
    private int sickLeave;
    private int emergencyLeave;
    private int vacationLeave;

    // Constructor with parameters
    public LeaveBalance(int empId, String employeeLastName, String employeeFirstName, int sickLeave, int emergencyLeave, int vacationLeave) {
        this.empId = empId;
        this.employeeLastName = employeeLastName;
        this.employeeFirstName = employeeFirstName;
        this.sickLeave = sickLeave;
        this.emergencyLeave = emergencyLeave;
        this.vacationLeave = vacationLeave;
    }

    // Getter and setter methods for empId
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    // Getter and setter methods for employeeLastName
    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    // Getter and setter methods for employeeFirstName
    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    // Getter and setter methods for sickLeave
    public int getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(int sickLeave) {
        this.sickLeave = sickLeave;
    }

    // Getter and setter methods for emergencyLeave
    public int getEmergencyLeave() {
        return emergencyLeave;
    }

    public void setEmergencyLeave(int emergencyLeave) {
        this.emergencyLeave = emergencyLeave;
    }

    // Getter and setter methods for vacationLeave
    public int getVacationLeave() {
        return vacationLeave;
    }

    public void setVacationLeave(int vacationLeave) {
        this.vacationLeave = vacationLeave;
    }
}
