package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Employee;
import service.SQL_client;

public class EmployeeDAO {
    private static EmployeeDAO instance = null;

    // Singleton pattern: private constructor
    public EmployeeDAO() {}

    // Singleton pattern: getInstance method
    public static EmployeeDAO getInstance() {
        if (instance == null) {
            instance = new EmployeeDAO();
        }
        return instance;
    }

    public static boolean createEmployee(Employee employee) {
        String sql = "INSERT INTO payrollsystem_db.employee (last_name, first_name, birthdate, address, phone_no, sss_no, philhealth_no, bir_no, pagibig_no, status_id, dept_id, position_id, emp_supervisor_id, basic_salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = SQL_client.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, employee.getLastName());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setDate(3, java.sql.Date.valueOf(employee.getBirthday()));
            pstmt.setString(4, employee.getAddress());
            pstmt.setString(5, employee.getPhoneNumber());
            pstmt.setString(6, employee.getSssNumber());
            pstmt.setString(7, employee.getPhilhealthNumber());
            pstmt.setString(8, employee.getTinNumber());
            pstmt.setString(9, employee.getPagibigNumber());
            pstmt.setInt(10, getStatusIdByName(employee.getStatus()));
            pstmt.setInt(11, getDepartmentIdByName(employee.getDepartment()));
            pstmt.setInt(12, getPositionIdByName(employee.getPosition()));
            pstmt.setInt(13, getEmployeeIdByName(employee.getImmediateSupervisor()));
            pstmt.setDouble(14, employee.getBasicSalary());

            // Execute the INSERT statement
            int rowsInserted = pstmt.executeUpdate();

            // Retrieve the generated auto-increment ID, if any
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    employee.setEmpId(generatedId);
                }
            }

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Employee getEmployeeById(int empId) {
        String sql = "SELECT e.*, s.status_name, d.dept_name, p.position_name, CONCAT(sup.first_name, ' ', sup.last_name) as supervisor_name, ed.gross_semi_monthly_rate, ed.hourly_rate " +
                     "FROM payrollsystem_db.employee e " +
                     "JOIN payrollsystem_db.status s ON e.status_id = s.status_id " +
                     "JOIN payrollsystem_db.department d ON e.dept_id = d.dept_id " +
                     "JOIN payrollsystem_db.position p ON e.position_id = p.position_id " +
                     "LEFT JOIN payrollsystem_db.employee sup ON e.emp_supervisor_id = sup.emp_id " +
                     "JOIN payrollsystem_db.employee_details ed ON e.emp_id = ed.emp_id " +
                     "WHERE e.emp_id = ?";
        try (Connection conn = SQL_client.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractEmployeeFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT e.*, s.status_name, d.dept_name, p.position_name, CONCAT(sup.first_name, ' ', sup.last_name) as supervisor_name, ed.gross_semi_monthly_rate, ed.hourly_rate " +
                     "FROM payrollsystem_db.employee e " +
                     "JOIN payrollsystem_db.status s ON e.status_id = s.status_id " +
                     "JOIN payrollsystem_db.department d ON e.dept_id = d.dept_id " +
                     "JOIN payrollsystem_db.position p ON e.position_id = p.position_id " +
                     "LEFT JOIN payrollsystem_db.employee sup ON e.emp_supervisor_id = sup.emp_id " +
                     "JOIN payrollsystem_db.employee_details ed ON e.emp_id = ed.emp_id";
        try (Connection conn = SQL_client.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Employee employee = extractEmployeeFromResultSet(rs);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static boolean updateEmployee(Employee employee) {
        String sql = "UPDATE payrollsystem_db.employee SET last_name = ?, first_name = ?, birthdate = ?, address = ?, phone_no = ?, sss_no = ?, philhealth_no = ?, bir_no = ?, pagibig_no = ?, status_id = ?, dept_id = ?, position_id = ?, emp_supervisor_id = ?, basic_salary = ? WHERE emp_id = ?";
        try (Connection conn = SQL_client.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getLastName());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setDate(3, java.sql.Date.valueOf(employee.getBirthday()));
            pstmt.setString(4, employee.getAddress());
            pstmt.setString(5, employee.getPhoneNumber());
            pstmt.setString(6, employee.getSssNumber());
            pstmt.setString(7, employee.getPhilhealthNumber());
            pstmt.setString(8, employee.getTinNumber());
            pstmt.setString(9, employee.getPagibigNumber());
            pstmt.setInt(10, getStatusIdByName(employee.getStatus()));
            pstmt.setInt(11, getDepartmentIdByName(employee.getDepartment()));
            pstmt.setInt(12, getPositionIdByName(employee.getPosition()));
            pstmt.setInt(13, getEmployeeIdByName(employee.getImmediateSupervisor()));
            pstmt.setDouble(14, employee.getBasicSalary());
            pstmt.setInt(15, employee.getEmpId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteEmployee(int empId) {
        String sql = "DELETE FROM payrollsystem_db.employee WHERE emp_id = ?";
        try (Connection conn = SQL_client.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("emp_id");
        String lastName = rs.getString("last_name");
        String firstName = rs.getString("first_name");
        String birthday = rs.getDate("birthdate").toLocalDate().toString();
        String address = rs.getString("address");
        String phoneNumber = rs.getString("phone_no");
        String sssNumber = rs.getString("sss_no");
        String philhealthNumber = rs.getString("philhealth_no");
        String tinNumber = rs.getString("bir_no");
        String pagibigNumber = rs.getString("pagibig_no");
        String status = rs.getString("status_name");
        String department = rs.getString("dept_name");
        String position = rs.getString("position_name");
        String immediateSupervisor = rs.getString("supervisor_name");
        double basicSalary = rs.getDouble("basic_salary");
        double grossSemiMonthlyRate = rs.getDouble("gross_semi_monthly_rate");
        double hourlyRate = rs.getDouble("hourly_rate");

        return new Employee(id, lastName, firstName, birthday, address, phoneNumber, sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, department, position, immediateSupervisor, basicSalary, grossSemiMonthlyRate, hourlyRate);
    }

    private static int getStatusIdByName(String statusName) {
        String sql = "SELECT status_id FROM payrollsystem_db.status WHERE status_name = ?";
        try (Connection conn = SQL_client.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, statusName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("status_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return a default value if no ID is found
    }

    private static int getDepartmentIdByName(String departmentName) {
        String sql = "SELECT dept_id FROM payrollsystem_db.department WHERE dept_name = ?";
        try (Connection conn = SQL_client.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, departmentName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("dept_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return a default value if no ID is found
    }

    private static int getPositionIdByName(String positionName) {
        String sql = "SELECT position_id FROM payrollsystem_db.position WHERE position_name = ?";
        try (Connection conn = SQL_client.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, positionName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("position_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return a default value if no ID is found
    }

    private static int getEmployeeIdByName(String supervisorName) {
        String sql = "SELECT emp_id FROM payrollsystem_db.employee WHERE CONCAT(first_name, ' ', last_name) = ?";
        try (Connection conn = SQL_client.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, supervisorName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("emp_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return a default value if no ID is found
    }
    
 // Method to retrieve all statuses from the database
    public Map<Integer, String> getAllStatuses() {
        Map<Integer, String> statuses = new HashMap<>();
        String sql = "SELECT status_id, status_name FROM payrollsystem_db.status";
        try (Connection conn = SQL_client.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("status_id");
                String name = rs.getString("status_name");
                statuses.put(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statuses;
    }

    // Method to retrieve all positions from the database
    public Map<Integer, String> getAllPositions() {
        Map<Integer, String> positions = new HashMap<>();
        String sql = "SELECT position_id, position_name FROM payrollsystem_db.position";
        try (Connection conn = SQL_client.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("position_id");
                String name = rs.getString("position_name");
                positions.put(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    // Method to retrieve all departments from the database
    public Map<Integer, String> getAllDepartments() {
        Map<Integer, String> departments = new HashMap<>();
        String sql = "SELECT dept_id, dept_name FROM payrollsystem_db.department";
        try (Connection conn = SQL_client.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("dept_id");
                String name = rs.getString("dept_name");
                departments.put(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    // Method to retrieve all supervisors from the database
    public Map<Integer, String> getAllSupervisors() {
        Map<Integer, String> supervisors = new HashMap<>();
        String sql = "SELECT emp_id, CONCAT(first_name, ' ', last_name) AS supervisor_name FROM payrollsystem_db.employee";
        try (Connection conn = SQL_client.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("emp_id");
                String name = rs.getString("supervisor_name");
                supervisors.put(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supervisors;
    }
    
 // Method to retrieve the auto-generated Employee ID after insertion
    public static int getGeneratedEmployeeId() {
        String sql = "SELECT LAST_INSERT_ID()"; // MySQL function to get last inserted ID
        try (Connection conn = SQL_client.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Retrieve the first column of the result set
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no ID is found or an error occurs
    }


}
