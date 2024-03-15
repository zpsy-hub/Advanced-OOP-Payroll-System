package service;

import java.sql.Connection;
import java.util.List;

import model.Permission;

public class PermissionService {
    private static PermissionService instance;

    public static synchronized PermissionService getInstance() {
        if (instance == null) {
            instance = new PermissionService();
        }
        return instance;
    }

    // Method to retrieve permissions for a specific employee
    public List<Permission> getPermissionsForEmployee(int employeeId, Connection connection) {
        return PermissionDAO.getInstance().getPermissionsByEmployeeId(employeeId, connection);
    }

}
