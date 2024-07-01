package test.DAO;

import static org.testng.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DAO.CredentialsManagementDAO;
import service.SQL_client;

public class CredentialsManagementDAOTest {

    private SQL_client sqlClient;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private CredentialsManagementDAO credentialsManagementDAO;

    @BeforeMethod
    public void setUp() throws SQLException {
        // Manual mocks
        sqlClient = new SQL_client() {
            @Override
            public Connection getConnection() {
                return connection;
            }
        };

        connection = new Connection() {
            @Override
            public PreparedStatement prepareStatement(String sql) throws SQLException {
                return preparedStatement;
            }

            // Implement other abstract methods of Connection as needed
        };

        preparedStatement = new PreparedStatement() {
            @Override
            public ResultSet executeQuery() throws SQLException {
                return resultSet;
            }

            @Override
            public int executeUpdate() throws SQLException {
                return 1; // Assume success
            }

            // Implement other abstract methods of PreparedStatement as needed
        };

        resultSet = new ResultSet() {
            private int index = -1;
            private final List<Employee> employees = Arrays.asList(
                new Employee(1, "Doe", "John"),
                new Employee(2, "Smith", "Jane"),
                new Employee(3, "Johnson", "Jack")
            );

            @Override
            public boolean next() throws SQLException {
                index++;
                return index < employees.size();
            }

            @Override
            public int getInt(String columnLabel) throws SQLException {
                return employees.get(index).getEmpId();
            }

            @Override
            public String getString(String columnLabel) throws SQLException {
                Employee employee = employees.get(index);
                switch (columnLabel) {
                    case "employee_lastname":
                        return employee.getLastName();
                    case "employee_firstname":
                        return employee.getFirstName();
                    default:
                        throw new SQLException("Invalid column label");
                }
            }

            // Implement other abstract methods of ResultSet as needed
        };

        // Initialize the DAO with the manual SQL_client mock
        credentialsManagementDAO = new CredentialsManagementDAO(sqlClient);
    }

    @Test
    public void deleteUserTest() throws SQLException {
        // Arrange
        int employeeId = 123;

        // Act
        boolean result = credentialsManagementDAO.deleteUser(employeeId);

        // Assert
        assertTrue(result);
    }

    @Test
    public void updatePasswordTest() throws SQLException {
        // Arrange
        int employeeId = 123;
        String newPassword = "newPassword123";
        String hashedPassword = credentialsManagementDAO.hashPassword(newPassword);

        // Act
        boolean result = credentialsManagementDAO.updatePassword(employeeId, newPassword);

        // Assert
        assertTrue(result);
    }

    @Test
    public void getAllEmployeeNamesTest() throws SQLException {
        // Act
        List<String> employeeNames = credentialsManagementDAO.getAllEmployeeNames();

        // Assert
        List<String> expectedNames = Arrays.asList(
            "1 - Doe, John",
            "2 - Smith, Jane",
            "3 - Johnson, Jack"
        );
        assertEquals(employeeNames, expectedNames);
    }

    @Test
    public void hashPasswordTest() {
        // Arrange
        String password = "password123";
        String expectedHash = "ef92b778bafe771e89245b89ecbc23bcb0f7a3fa3f1b0f6e4c2e3b6da3a4f46c"; // Example hash

        // Act
        String actualHash = credentialsManagementDAO.hashPassword(password);

        // Assert
        assertEquals(actualHash, expectedHash);
    }
}
