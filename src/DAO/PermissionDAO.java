package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Permission;
import service.SQL_client;


public class PermissionDAO {
	private static PermissionDAO instance;
    private static Connection connection;

    private PermissionDAO() {
        this.connection = SQL_client.getInstance().getConnection();
    }

    public static synchronized PermissionDAO getInstance() {
        if (instance == null) {
            instance = new PermissionDAO();
        }
        return instance;
    }

    public List<Permission> getAllPermissions() {
        List<Permission> permissions = new ArrayList<>();
        String query = "SELECT * FROM payroll_system.permissions";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int permissionId = resultSet.getInt("permissions_id");
                String permissionName = resultSet.getString("permissions_name");
                permissions.add(new Permission(permissionId, permissionName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permissions;
    }

    public List<Permission> getPermissionsByEmployeeId(int empId, Connection connection) {
        List<Permission> permissions = new ArrayList<>();
        String query = "SELECT p.* FROM payroll_system.permissions p " +
                       "INNER JOIN payroll_system.employee_permissions ep ON p.permissions_id = ep.permission_id " +
                       "WHERE ep.emp_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int permissionId = resultSet.getInt("permissions_id");
                String permissionName = resultSet.getString("permissions_name");
                permissions.add(new Permission(permissionId, permissionName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permissions;
    }
    
    public void grantAccess(int empId, int permissionId) {
        String query = "INSERT INTO payroll_system.employee_permissions (emp_id, permission_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, empId);
            statement.setInt(2, permissionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void revokeAccess(int empId, int permissionId) {
        String query = "DELETE FROM payroll_system.employee_permissions WHERE emp_id = ? AND permission_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, empId);
            statement.setInt(2, permissionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // Fetch permissions for employee with ID 6
        int empId = 6;
        PermissionDAO permissionDAO = PermissionDAO.getInstance();
        try (Connection connection = SQL_client.getInstance().getConnection()) {
            List<Permission> permissions = permissionDAO.getPermissionsByEmployeeId(empId, connection);

            // Print the permissions
            System.out.println("Permissions for employee with ID " + empId + ":");
            for (Permission permission : permissions) {
                System.out.println(permission.getPermissionName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}