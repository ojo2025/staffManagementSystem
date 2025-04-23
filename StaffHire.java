import java.io.Serializable;

/**
 * StaffHire class - base class for the recruitment system
 * Contains common attributes and methods for all staff hiring
 */
public class StaffHire implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int vacancyNumber;
    private String designation;
    private String jobType;
    private String staffName;
    private String joiningDate;
    private String qualification;
    private String appointedBy;
    private boolean joined;
    private boolean active;
    
    /**
     * Constructor for the StaffHire class
     * @param vacancyNumber the vacancy number
     * @param designation the job designation
     * @param jobType the job type (permanent, contract, temporary)
     * @param staffName the name of the staff
     * @param joiningDate the joining date
     * @param qualification the qualification
     * @param appointedBy person who appointed
     * @param joined whether the staff has joined
     */
    public StaffHire(int vacancyNumber, String designation, String jobType, 
                    String staffName, String joiningDate, String qualification, 
                    String appointedBy, boolean joined) {
        this.vacancyNumber = vacancyNumber;
        this.designation = designation;
        this.jobType = jobType;
        this.staffName = staffName;
        this.joiningDate = joiningDate;
        this.qualification = qualification;
        this.appointedBy = appointedBy;
        this.joined = joined;
        this.active = true;
    }
    
    // Getter and Setter methods
    public int getVacancyNumber() {
        return vacancyNumber;
    }
    
    public void setVacancyNumber(int vacancyNumber) {
        this.vacancyNumber = vacancyNumber;
    }
    
    public String getDesignation() {
        return designation;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
    public String getJobType() {
        return jobType;
    }
    
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    
    public String getStaffName() {
        return staffName;
    }
    
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
    
    public String getJoiningDate() {
        return joiningDate;
    }
    
    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }
    
    public String getQualification() {
        return qualification;
    }
    
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
    public String getAppointedBy() {
        return appointedBy;
    }
    
    public void setAppointedBy(String appointedBy) {
        this.appointedBy = appointedBy;
    }
    
    public boolean isJoined() {
        return joined;
    }
    
    public void setJoined(boolean joined) {
        this.joined = joined;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
     * Method to display the details of the staff
     */
    public void display() {
        System.out.println("Vacancy Number: " + vacancyNumber);
        System.out.println("Designation: " + designation);
        System.out.println("Job Type: " + jobType);
        System.out.println("Staff Name: " + staffName);
        System.out.println("Joining Date: " + joiningDate);
        System.out.println("Qualification: " + qualification);
        System.out.println("Appointed By: " + appointedBy);
        System.out.println("Joined: " + (joined ? "Yes" : "No"));
        System.out.println("Active: " + (active ? "Yes" : "No"));
    }
} 