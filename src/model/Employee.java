package model;

public class Employee {
    private int id;
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
    private String position;
    private String immediateSupervisor;
    private float basicSalary;
    private float riceSubsidy;
    private float phoneAllowance;
    private float clothingAllowance;
    private float grossSemimonthlyRate;
    private double hourlyRate;

    // Constructor
    public Employee(int id, String lastName, String firstName, String birthday, String address, String phoneNumber,
            String sssNumber, String philhealthNumber, String tinNumber, String pagibigNumber, String status,
            String position, String immediateSupervisor, float basicSalary, float riceSubsidy, float phoneAllowance,
            float clothingAllowance, float grossSemimonthlyRate, double hourlyRate) {
        this.id = id;
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
        this.position = position;
        this.immediateSupervisor = immediateSupervisor;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemimonthlyRate = grossSemimonthlyRate;
        this.hourlyRate = hourlyRate;
    }

    // Getters and Setters
    public int getEmpId() {
        return id;
    }

    public void setid(int id) {
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

    public float getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(float basicSalary) {
        this.basicSalary = basicSalary;
    }

    public float getRiceSubsidy() {
        return riceSubsidy;
    }

    public void setRiceSubsidy(float riceSubsidy) {
        this.riceSubsidy = riceSubsidy;
    }

    public float getPhoneAllowance() {
        return phoneAllowance;
    }

    public void setPhoneAllowance(float phoneAllowance) {
        this.phoneAllowance = phoneAllowance;
    }

    public float getClothingAllowance() {
        return clothingAllowance;
    }

    public void setClothingAllowance(float clothingAllowance) {
        this.clothingAllowance = clothingAllowance;
    }

    public float getGrossSemimonthlyRate() {
        return grossSemimonthlyRate;
    }

    public void setGrossSemimonthlyRate(float grossSemimonthlyRate) {
        this.grossSemimonthlyRate = grossSemimonthlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

}
