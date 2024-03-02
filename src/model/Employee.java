package model;

public class Employee {
	private String id;
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
	private double basicSalary;
	private double riceSubsidy;
	private double phoneAllowance;
	private double clothingAllowance;
	private double grossSemiMonthlyRate;
	private double hourlyRate;

    // Constructor method
	public Employee(String id, String lastName, String firstName, String birthday, String address, String phoneNumber,
			String sssNumber, String philhealthNumber, String tinNumber, String pagibigNumber, String status,
            String position, String immediateSupervisor, double basicSalary, double riceSubsidy, double phoneAllowance,
            double clothingAllowance, double grossSemiMonthlyRate, double hourlyRate) {
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
    this.grossSemiMonthlyRate = grossSemiMonthlyRate;
    this.hourlyRate = hourlyRate;
}

	 // Getter methods
	    public String getId() {
	    return id;
	    }

	    public String getLastName() {
	    return lastName;
	    }

	    public String getFirstName() {
	    return firstName;
	    }

	    public String getBirthday() {
	    return birthday;
	    }

	    public String getAddress() {
	    return address;
	    }

	    public String getPhoneNumber() {
	    return phoneNumber;
	    }

	    public String getSssNumber() {
	    return sssNumber;
	    }

	    public String getPhilhealthNumber() {
	    return philhealthNumber;
	    }

	    public String getTinNumber() {
	    return tinNumber;
	    }

	    public String getPagibigNumber() {
	    return pagibigNumber;
	    }

	    public String getStatus() {
	    return status;
	    }

	    public String getPosition() {
	    return position;
	    }

	    public String getImmediateSupervisor() {
	    return immediateSupervisor;
	    }

	    public double getBasicSalary() {
	    return basicSalary;
	    }

	    public double getRiceSubsidy() {
	    return riceSubsidy;
	    }

	    public double getPhoneAllowance() {
	    return phoneAllowance;
	    }

	    public double getClothingAllowance() {
	    return clothingAllowance;
	    }

	    public double getGrossSemiMonthlyRate() {
	    return grossSemiMonthlyRate;
	    }

	    public double getHourlyRate() {
	    return hourlyRate;
	    }
	    
	    //Setter methods
	    public void setId(String id) {
	        this.id = id;
	    }

	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	    }

	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	    }

	    public void setBirthday(String birthday) {
	        this.birthday = birthday;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public void setPhoneNumber(String phoneNumber) {
	        this.phoneNumber = phoneNumber;
	    }

	    public void setSssNumber(String sssNumber) {
	        this.sssNumber = sssNumber;
	    }

	    public void setPhilhealthNumber(String philhealthNumber) {
	        this.philhealthNumber = philhealthNumber;
	    }

	    public void setTinNumber(String tinNumber) {
	        this.tinNumber = tinNumber;
	    }

	    public void setPagibigNumber(String pagibigNumber) {
	        this.pagibigNumber = pagibigNumber;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public void setPosition(String position) {
	        this.position = position;
	    }

	    public void setImmediateSupervisor(String immediateSupervisor) {
	        this.immediateSupervisor = immediateSupervisor;
	    }

	    public void setBasicSalary(double basicSalary) {
	        this.basicSalary = basicSalary;
	    }

	    public void setRiceSubsidy(double riceSubsidy) {
	        this.riceSubsidy = riceSubsidy;
	    }

	    public void setPhoneAllowance(double phoneAllowance) {
	        this.phoneAllowance = phoneAllowance;
	    }

	    public void setClothingAllowance(double clothingAllowance) {
	        this.clothingAllowance = clothingAllowance;
	    }

	    public void setGrossSemiMonthlyRate(double grossSemiMonthlyRate) {
	        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
	    }

	    public void setHourlyRate(double hourlyRate) {
	        this.hourlyRate = hourlyRate;
	    }
	    
	    @Override
	    public String toString() {
	        return "Employee{" +
	                "id='" + id + '\'' +
	                ", lastName='" + lastName + '\'' +
	                ", firstName='" + firstName + '\'' +
	                // Add other fields as needed
	                '}';
	    }
}
