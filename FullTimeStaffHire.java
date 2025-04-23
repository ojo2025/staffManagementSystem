/**
 * FullTimeStaffHire class - represents a full-time staff hire
 * Extends the StaffHire class to include full-time specific attributes and methods
 */
public class FullTimeStaffHire extends StaffHire {
    private static final long serialVersionUID = 1L;
    
    private double salary;
    private int weeklyFractionalHours;
    
    /**
     * Constructor for the FullTimeStaffHire class
     * @param vacancyNumber the vacancy number
     * @param designation the job designation
     * @param jobType the job type
     * @param staffName the name of the staff
     * @param joiningDate the joining date
     * @param qualification the qualification
     * @param appointedBy person who appointed
     * @param joined whether the staff has joined
     * @param salary the salary
     * @param weeklyFractionalHours the weekly fractional hours
     */
    public FullTimeStaffHire(int vacancyNumber, String designation, String jobType, 
                            String staffName, String joiningDate, String qualification, 
                            String appointedBy, boolean joined, double salary, 
                            int weeklyFractionalHours) {
        super(vacancyNumber, designation, jobType, staffName, joiningDate, 
              qualification, appointedBy, joined);
        this.salary = salary;
        this.weeklyFractionalHours = weeklyFractionalHours;
    }
    
    // Getter and Setter methods
    public double getSalary() {
        return salary;
    }
    
    /**
     * Method to set the salary
     * Only allows setting if staff has joined, otherwise displays a message
     * @param salary the new salary
     */
    public void setSalary(double salary) {
        if (isJoined()) {
            this.salary = salary;
        } else {
            System.out.println("No staff appointed to set the salary.");
        }
    }
    
    public int getWeeklyFractionalHours() {
        return weeklyFractionalHours;
    }
    
    /**
     * Method to set the weekly fractional hours
     * @param weeklyFractionalHours the new weekly fractional hours
     */
    public void setWeeklyFractionalHours(int weeklyFractionalHours) {
        this.weeklyFractionalHours = weeklyFractionalHours;
    }
    
    /**
     * Method to display the details of the full-time staff
     * Overrides the display method in the StaffHire class
     */
    @Override
    public void display() {
        super.display();
        if (isJoined()) {
            System.out.println("Salary: " + salary);
            System.out.println("Weekly Fractional Hours: " + weeklyFractionalHours);
        }
    }
} 