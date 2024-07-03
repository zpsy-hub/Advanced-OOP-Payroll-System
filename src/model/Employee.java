package model;

public class Employee {
    private int empId;
    private String lastName;
    private String firstName;
    private String birthday;
    private String address;
    private String phoneNumber;
    private String sssNumber;
    private String philhealthNumber;
    private String tinNumber;
    private String pagibigNumber;
    private String status;
    private String department;
    private String position;
    private String immediateSupervisor;
    private double basicSalary;
    private double grossSemiMonthlyRate;
    private double hourlyRate;

    // Constructor using all fields
    public Employee(int empId, String lastName, String firstName, String birthday, String address, String phoneNumber,
                    String sssNumber, String philhealthNumber, String tinNumber, String pagibigNumber, String status,
                    String department, String position, String immediateSupervisor, double basicSalary,
                    double grossSemiMonthlyRate, double hourlyRate) {
        this.empId = empId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sssNumber = sssNumber;
        this.philhealthNumber = philhealthNumber;
        this.tinNumber = tinNumber;
        this.pagibigNumber = pagibigNumber;
        this.status = status;
        this.department = department;
        this.position = position;
        this.immediateSupervisor = immediateSupervisor;
        this.basicSalary = basicSalary;
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
        this.hourlyRate = hourlyRate;
    }

    // Constructor for convenience (parameters adjusted based on use case)
    public Employee(int empId, String lastName, String firstName, String birthday, String address, String phoneNumber,
                    String sssNumber, String philhealthNumber, String tinNumber, String pagibigNumber, String status,
                    String department, String position, String immediateSupervisor, float basicSalary) {
        this.empId = empId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sssNumber = sssNumber;
        this.philhealthNumber = philhealthNumber;
        this.tinNumber = tinNumber;
        this.pagibigNumber = pagibigNumber;
        this.status = status;
        this.department = department;
        this.position = position;
        this.immediateSupervisor = immediateSupervisor;
        this.basicSalary = basicSalary;       
    }
    
    /// Constructor for logged-in user info (with full name)
    public Employee(int empId, String employeeName, String department, String position) {
        this.empId = empId;
        if (employeeName != null && employeeName.contains(" ")) {
            String[] nameParts = employeeName.split(" ");
            this.firstName = nameParts[0];
            this.lastName = nameParts[1];
        } else {
            this.firstName = employeeName;
            this.lastName = "";
        }
        this.department = department;
        this.position = position;
    }
    
    // Constructor with necessary parameters
    public Employee(int empId, String firstName, String lastName, String position, String department) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.department = department;
    }

    
    // Getters and setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSssNumber() {
        return sssNumber;
    }

    public void setSssNumber(String sssNumber) {
        this.sssNumber = sssNumber;
    }

    public String getPhilhealthNumber() {
        return philhealthNumber;
    }

    public void setPhilhealthNumber(String philhealthNumber) {
        this.philhealthNumber = philhealthNumber;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getPagibigNumber() {
        return pagibigNumber;
    }

    public void setPagibigNumber(String pagibigNumber) {
        this.pagibigNumber = pagibigNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImmediateSupervisor() {
        return immediateSupervisor;
    }

    public void setImmediateSupervisor(String immediateSupervisor) {
        this.immediateSupervisor = immediateSupervisor;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getGrossSemiMonthlyRate() {
        return grossSemiMonthlyRate;
    }

    public void setGrossSemiMonthlyRate(double grossSemiMonthlyRate) {
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", sssNumber='" + sssNumber + '\'' +
                ", philhealthNumber='" + philhealthNumber + '\'' +
                ", tinNumber='" + tinNumber + '\'' +
                ", pagibigNumber='" + pagibigNumber + '\'' +
                ", status='" + status + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", immediateSupervisor='" + immediateSupervisor + '\'' +
                ", basicSalary=" + basicSalary +
                ", grossSemiMonthlyRate=" + grossSemiMonthlyRate +
                ", hourlyRate=" + hourlyRate +
                '}';
    }
}
