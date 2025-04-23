/**
 * PartTimeStaffHire class - represents a part-time staff hire
 * Extends the StaffHire class to include part-time specific attributes and methods
 */
public class PartTimeStaffHire extends StaffHire {
    private static final long serialVersionUID = 1L;
    
    private int workingHour;
    private double wagesPerHour;
    private String shifts;
    private boolean terminated;
    
    /**
     * Constructor for the PartTimeStaffHire class
     * @param vacancyNumber the vacancy number
     * @param designation the job designation
     * @param jobType the job type
     * @param staffName the name of the staff
     * @param joiningDate the joining date
     * @param qualification the qualification
     * @param appointedBy person who appointed
     * @param joined whether the staff has joined
     * @param workingHour the working hours per day
     * @param wagesPerHour the wages per hour
     * @param shifts the working shifts (morning, day, evening)
     */
    public PartTimeStaffHire(int vacancyNumber, String designation, String jobType, 
                            String staffName, String joiningDate, String qualification, 
                            String appointedBy, boolean joined, int workingHour, 
                            double wagesPerHour, String shifts) {
        super(vacancyNumber, designation, jobType, staffName, joiningDate, 
              qualification, appointedBy, joined);
        this.workingHour = workingHour;
        this.wagesPerHour = wagesPerHour;
        this.shifts = shifts;
        this.terminated = false;
    }
    
    // Getter methods
    public int getWorkingHour() {
        return workingHour;
    }
    
    public double getWagesPerHour() {
        return wagesPerHour;
    }
    
    public String getShifts() {
        return shifts;
    }
    
    public boolean isTerminated() {
        return terminated;
    }
    
    /**
     * Method to set the shifts
     * Only allows setting if staff has joined and not terminated
     * @param shifts the new shifts
     */
    public void setShifts(String shifts) {
        if (isJoined() && !terminated) {
            this.shifts = shifts;
        } else {
            System.out.println("Staff has not joined or has been terminated. Cannot change shifts.");
        }
    }
    
    /**
     * Method to terminate the staff
     * Only works if staff is not already terminated
     */
    public void terminateStaff() {
        if (terminated) {
            System.out.println("Staff is already terminated.");
        } else {
            setStaffName("");
            setJoiningDate("");
            setQualification("");
            setAppointedBy("");
            setJoined(false);
            setActive(false);
            this.terminated = true;
            System.out.println("Staff has been terminated.");
        }
    }
    
    /**
     * Method to display the details of the part-time staff
     * Overrides the display method in the StaffHire class
     */
    @Override
    public void display() {
        super.display();
        if (isJoined() && !terminated) {
            System.out.println("Working Hours: " + workingHour);
            System.out.println("Wages Per Hour: " + wagesPerHour);
            System.out.println("Shifts: " + shifts);
            System.out.println("Income per Day: " + (workingHour * wagesPerHour));
        }
        System.out.println("Terminated: " + (terminated ? "Yes" : "No"));
    }
} 