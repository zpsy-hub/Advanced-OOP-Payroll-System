package model;
import util.UserRole;

public class User {
    private String username;
    private String password;
    private int id;
    private String lastName;
    private String firstName;
    private String position;
    private UserRole role;
    private Employee employee;

    // Constructor
    public User(String username, String password, int employeeNumber, String lastName, String firstName, String position, UserRole role) {
        this.username = username;
        this.password = password;
        this.id = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.position = position;
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}