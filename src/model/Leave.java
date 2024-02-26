package model;

public class Leave {
	private String id;
	private String lastName;
	private String firstName;
	private int sickLeaveDays;
	private int emergencyLeaveDays;
	private int vacationLeaveDays;
	
	// Constructor with parameters
	public Leave(String id, String lastName, String firstName, int sickLeaveDays, int emergencyLeaveDays, int vacationLeaveDays) {
		this.id = id;
	    this.lastName = lastName;
	    this.firstName = firstName;
	    this.sickLeaveDays = sickLeaveDays;
	    this.emergencyLeaveDays = emergencyLeaveDays;
	    this.vacationLeaveDays = vacationLeaveDays;
	    }
	
	// Getter and setter methods for employeeId
	public String getId() {
	    return id;
	    }
	
	public void setId(String id) {
        this.id = id;
        }
	    
	
	// Getter and setter methods for lastName
	public String getLastName() {
		return lastName;
		}
	
	public void setLastName(String lastName) {
	    this.lastName = lastName;
	    }
	    
	// Getter and setter methods for firstName
	public String getFirstName() {
	    return firstName;
	    }
	
	public void setFirstName(String firstName) {
	    this.firstName = firstName;
	    }
	    

    // Getter and setter methods for sickLeaveDays
    public int getSickLeaveDays() {
        return sickLeaveDays;
    }

    public void setSickLeaveDays(int sickLeaveDays) {
        this.sickLeaveDays = sickLeaveDays;
    }

    // Getter and setter methods for vacationLeaveDays
    public int getVacationLeaveDays() {
        return vacationLeaveDays;
    }

    public void setVacationLeaveDays(int vacationLeaveDays) {
        this.vacationLeaveDays = vacationLeaveDays;
    }

    // Getter and setter methods for emergencyLeaveDays
    public int getEmergencyLeaveDays() {
        return emergencyLeaveDays;
    }

    public void setEmergencyLeaveDays(int emergencyLeaveDays) {
        this.emergencyLeaveDays = emergencyLeaveDays;
    }
}
