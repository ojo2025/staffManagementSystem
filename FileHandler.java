import java.io.*;
import java.util.ArrayList;

/**
 * FileHandler class to handle saving and loading data from files
 */
public class FileHandler {
    // File paths
    private static final String STAFF_FILE = "staff_data.ser";
    private static final String VACANCY_FILE = "vacancy_data.ser";
    
    /**
     * Saves staff list to file
     * @param staffList the list of staff to save
     * @return true if successful, false otherwise
     */
    public static boolean saveStaffData(ArrayList<StaffHire> staffList) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(STAFF_FILE))) {
            out.writeObject(staffList);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving staff data: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Loads staff list from file
     * @return the loaded list or a new empty list if file does not exist
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<StaffHire> loadStaffData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(STAFF_FILE))) {
            return (ArrayList<StaffHire>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No staff data file found. Creating new data list.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading staff data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Saves vacancy list to file
     * @param vacancyList the list of vacancies to save
     * @return true if successful, false otherwise
     */
    public static boolean saveVacancyData(ArrayList<Vacancy> vacancyList) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(VACANCY_FILE))) {
            out.writeObject(vacancyList);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving vacancy data: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Loads vacancy list from file
     * @return the loaded list or a new empty list if file does not exist
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Vacancy> loadVacancyData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(VACANCY_FILE))) {
            return (ArrayList<Vacancy>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No vacancy data file found. Creating new data list.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading vacancy data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
} 