package model;

public class EmployeePermission {
    private int empId;
    private int permissionId;

    public EmployeePermission(int empId, int permissionId) {
        this.empId = empId;
        this.permissionId = permissionId;
    }

    // Getters and setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }
}
