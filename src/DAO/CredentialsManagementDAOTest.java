package DAO;

import org.junit.Test;

import service.SQL_client;

import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CredentialsManagementDAOTest {
    private CredentialsManagementDAO dao;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        dao = new CredentialsManagementDAO();
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(SQL_client.getInstance().getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    public void testDeleteUser_Success() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean result = dao.deleteUser(123); // Assuming employeeId 123 exists
        assertTrue(result);
    }

    @Test
    public void testDeleteUser_Failure() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // Simulate failure
        boolean result = dao.deleteUser(123);
        assertFalse(result);
    }

    @Test
    public void testUpdatePassword_Success() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean result = dao.updatePassword(123, "newPassword123");
        assertTrue(result);
    }

    @Test
    public void testUpdatePassword_Failure() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // Simulate failure
        boolean result = dao.updatePassword(123, "newPassword123");
        assertFalse(result);
    }

    @Test
    public void testGetAllEmployeeNames() throws SQLException {
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("123 - Doe, John");
        expectedNames.add("456 - Smith, Jane");

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false); // Two rows, then end
        when(mockResultSet.getInt("emp_id")).thenReturn(123, 456);
        when(mockResultSet.getString("employee_lastname")).thenReturn("Doe", "Smith");
        when(mockResultSet.getString("employee_firstname")).thenReturn("John", "Jane");

        List<String> result = dao.getAllEmployeeNames();
        assertEquals(expectedNames, result);
    }
}