package model;

public class User {
    private String username;
    private String password;
    private String id;
    private String lastName;
    private String firstName;
    private String position;
    private UserRole role;

    // Constructor
    public User(String username, String password, String id, String lastName, String firstName, String position, UserRole role) {
        this.username = username;
        this.password = password;
        this.id = id;
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

    public String getid() {
        return id;
    }

    public void setId(String id) {
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
}

