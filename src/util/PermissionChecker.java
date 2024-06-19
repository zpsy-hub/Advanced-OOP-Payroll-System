package util;

import model.Permission;
import model.User;
import service.PermissionService;
import service.SQL_client;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PermissionChecker {

    private final List<Permission> userPermissions;

    public PermissionChecker(User loggedInEmployee) {
        Connection connection = SQL_client.getInstance().getConnection();
        PermissionService permissionsService = PermissionService.getInstance();
        this.userPermissions = permissionsService.getPermissionsForEmployee(loggedInEmployee.getId(), connection);
    }

    public List<String> getVisibleButtons() {
        List<String> visibleButtons = new ArrayList<>();
        visibleButtons.add("Dashboard");
        visibleButtons.add("Time In/Out");
        visibleButtons.add("Payslip");
        visibleButtons.add("Leave Request");
        visibleButtons.add("Overtime Request");

        if (hasPermission(1)) {
            visibleButtons.add("Employee Management");
        }
        if (hasPermission(2)) {
            visibleButtons.add("Attendance Management");
        }
        if (hasPermission(3)) {
            visibleButtons.add("Leave Management");
        }
        if (hasPermission(4)) {
            visibleButtons.add("Salary Calculation");
        }
        if (hasPermission(5)) {
            visibleButtons.add("Monthly Summary Reports");
        }
        if (hasPermission(7)) {
            visibleButtons.add("Permissions Management");
        }
        if (hasPermission(8)) {
            visibleButtons.add("Credentials Management");
        }
        if (hasPermission(6)) {
            visibleButtons.add("Authentication Logs");
        }
        if (hasPermission(9)) {
            visibleButtons.add("Overtime Management");
        }

        return visibleButtons;
    }

    private boolean hasPermission(int permissionId) {
        return userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == permissionId);
    }
}
