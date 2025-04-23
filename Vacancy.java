import java.io.Serializable;

/**
 * Vacancy class - represents a job vacancy in the recruitment system
 */
public class Vacancy implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int vacancyId;
    private String designation;
    private String jobType;
    private boolean isOpen;
    
    /**
     * Constructor for the Vacancy class
     * @param vacancyId the unique vacancy identifier
     * @param designation the job designation
     * @param jobType the job type (permanent, contract, temporary)
     */
    public Vacancy(int vacancyId, String designation, String jobType) {
        this.vacancyId = vacancyId;
        this.designation = designation;
        this.jobType = jobType;
        this.isOpen = true;
    }
    
    // Getter and Setter methods
    public int getVacancyId() {
        return vacancyId;
    }
    
    public void setVacancyId(int vacancyId) {
        this.vacancyId = vacancyId;
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
    
    public boolean isOpen() {
        return isOpen;
    }
    
    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    
    /**
     * Closes the vacancy
     */
    public void closeVacancy() {
        this.isOpen = false;
    }
    
    /**
     * Opens the vacancy
     */
    public void openVacancy() {
        this.isOpen = true;
    }
    
    /**
     * Method to display the details of the vacancy
     */
    public void display() {
        System.out.println("Vacancy ID: " + vacancyId);
        System.out.println("Designation: " + designation);
        System.out.println("Job Type: " + jobType);
        System.out.println("Status: " + (isOpen ? "Open" : "Closed"));
    }
    
    /**
     * Returns a string representation of the vacancy
     */
    @Override
    public String toString() {
        return "Vacancy #" + vacancyId + " - " + designation + " (" + jobType + ") - " + (isOpen ? "Open" : "Closed");
    }
} 