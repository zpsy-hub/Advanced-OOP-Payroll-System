package model;

import java.sql.Date;

public class LeaveDetails {
    private Leave leave;
    private String lastName;
    private String firstName;
    private String leaveTypeName;

    public LeaveDetails(Leave leave, String lastName, String firstName, String leaveTypeName) {
        this.leave = leave;
        this.lastName = lastName;
        this.firstName = firstName;
        this.leaveTypeName = leaveTypeName;
    }

    // Getters and Setters
    public Leave getLeave() {
        return leave;
    }

    public void setLeave(Leave leave) {
        this.leave = leave;
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

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

  
}
