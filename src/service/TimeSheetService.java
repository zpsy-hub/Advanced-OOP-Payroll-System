package service;

import java.util.List;

import DAO.TimesheetDAO;
import model.User;

public class TimeSheetService {
    private final TimesheetDAO timesheetDAO;
    private final User loggedInUser;

    public TimeSheetService(User loggedInUser) {
        this.timesheetDAO = new TimesheetDAO();
        this.loggedInUser = loggedInUser;
    }
    
    /**
     * Retrieves the details of the logged-in employee.
     *
     * @return The User object of the logged-in user if found, null otherwise.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Retrieves timesheet records for the logged-in employee.
     *
     * @return A list of String arrays representing timesheet records.
     */
    public List<String[]> getLoggedInEmployeeTimesheetRecords() {
        User loggedInUser = getLoggedInUser();
        if (loggedInUser != null) {
            return timesheetDAO.getTimesheetRecords(loggedInUser.getId());
        } else {
            return null;
        }
    }

    /**
     * Retrieves timesheet records for a given employee ID.
     *
     * @param empId The ID of the employee.
     * @return A list of String arrays representing timesheet records.
     */
    public List<String[]> getEmployeeTimesheetRecords(int empId) {
        return timesheetDAO.getTimesheetRecords(empId);
    }
}
