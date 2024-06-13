package model;

public class User {
    private String username;
    private String password;
    private int id;
    private String lastName;
    private String firstName;
    private String position;
    private Employee employee;

    // Full Constructor
    public User(String username, String password, int employeeNumber, String lastName, String firstName, String position) {
        this.username = username;
        this.password = password;
        this.id = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.position = position;
    }

    // Constructor for testing with only id, last name, and first name
    public User(int id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
