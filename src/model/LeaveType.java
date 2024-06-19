package model;

public class LeaveType {
    private int leaveTypeId;
    private String leaveTypeName;
    private int daysAllotted;

    // Constructor
    public LeaveType(int leaveTypeId, String leaveTypeName, int daysAllotted) {
        this.leaveTypeId = leaveTypeId;
        this.leaveTypeName = leaveTypeName;
        this.daysAllotted = daysAllotted;
    }

    // Getters and Setters
    public int getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    public int getDaysAllotted() {
        return daysAllotted;
    }

    public void setDaysAllotted(int daysAllotted) {
        this.daysAllotted = daysAllotted;
    }
}
