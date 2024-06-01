package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "INSERT INTO payroll_system.employees (employee_lastname, employee_firstname, birthday, address, phone_number, sss_number, philhealth_number, tin_number, pagibig_number, status, position, immediate_supervisor, basic_salary, rice_subsidy, phone_allowance, clothing_allowance, gross_semimonthly_rate, hourly_rate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            pstmt.setString(10, employee.getStatus());
            pstmt.setString(11, employee.getPosition());
            pstmt.setString(12, employee.getImmediateSupervisor());
            pstmt.setFloat(13, employee.getBasicSalary());
            pstmt.setFloat(14, employee.getRiceSubsidy());
            pstmt.setFloat(15, employee.getPhoneAllowance());
            pstmt.setFloat(16, employee.getClothingAllowance());
            pstmt.setFloat(17, employee.getGrossSemimonthlyRate());
            pstmt.setDouble(18, employee.getHourlyRate());

            // Execute the INSERT statement
            int rowsInserted = pstmt.executeUpdate();

            // Retrieve the generated auto-increment ID, if any
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    employee.setid(generatedId);
                }
            }

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static Employee getEmployeeById(int empId) {
        String sql = "SELECT * FROM payroll_system.employees WHERE emp_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = SQL_client.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, empId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractEmployeeFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close ResultSet, PreparedStatement, and Connection in finally block
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private static Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("emp_id");
        String lastName = rs.getString("employee_lastname");
        String firstName = rs.getString("employee_firstname");
        String birthday = rs.getDate("birthday").toLocalDate().toString();
        String address = rs.getString("address");
        String phoneNumber = rs.getString("phone_number");
        String sssNumber = rs.getString("sss_number");
        String philhealthNumber = rs.getString("philhealth_number");
        String tinNumber = rs.getString("tin_number");
        String pagibigNumber = rs.getString("pagibig_number");
        String status = rs.getString("status");
        String position = rs.getString("position");
        String immediateSupervisor = rs.getString("immediate_supervisor");
        float basicSalary = rs.getFloat("basic_salary");
        float riceSubsidy = rs.getFloat("rice_subsidy");
        float phoneAllowance = rs.getFloat("phone_allowance");
        float clothingAllowance = rs.getFloat("clothing_allowance");
        float grossSemimonthlyRate = rs.getFloat("gross_semimonthly_rate");
        double hourlyRate = rs.getDouble("hourly_rate");

        // Use the parameterized constructor to create an Employee object
        return new Employee(id, lastName, firstName, birthday, address, phoneNumber, sssNumber, philhealthNumber,
                tinNumber, pagibigNumber, status, position, immediateSupervisor, basicSalary, riceSubsidy,
                phoneAllowance, clothingAllowance, grossSemimonthlyRate, hourlyRate);
    }



    public static boolean updateEmployee(Employee employee) {
        String sql = "UPDATE payroll_system.employees SET employee_lastname=?, employee_firstname=?, birthday=?, address=?, phone_number=?, sss_number=?, philhealth_number=?, tin_number=?, pagibig_number=?, status=?, position=?, immediate_supervisor=?, basic_salary=?, rice_subsidy=?, phone_allowance=?, clothing_allowance=?, gross_semimonthly_rate=?, hourly_rate=? WHERE emp_id=?";
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
            pstmt.setString(10, employee.getStatus());
            pstmt.setString(11, employee.getPosition());
            pstmt.setString(12, employee.getImmediateSupervisor());
            pstmt.setFloat(13, employee.getBasicSalary());
            pstmt.setFloat(14, employee.getRiceSubsidy());
            pstmt.setFloat(15, employee.getPhoneAllowance());
            pstmt.setFloat(16, employee.getClothingAllowance());
            pstmt.setFloat(17, employee.getGrossSemimonthlyRate());
            pstmt.setDouble(18, employee.getHourlyRate());
            pstmt.setInt(19, employee.getEmpId());
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteEmployee(int empId) {
        String sql = "DELETE FROM payroll_system.employees WHERE emp_id=?";
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
    
    //gets all employees records
    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM payroll_system.employees";
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
          
    // Method to get the hourly rate by employee ID
    public static double getHourlyRateById(int empId) {
        String sql = "SELECT hourly_rate FROM payroll_system.employees WHERE emp_id=?";
        try (Connection conn = SQL_client.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("hourly_rate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return a default value if no hourly rate is found
        return 0.0;
    }
    
}
