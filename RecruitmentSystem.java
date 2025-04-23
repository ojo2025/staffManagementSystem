import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.PrintStream;

/**
 * RecruitmentSystem class - the main class for the recruitment system GUI
 */
public class RecruitmentSystem extends JFrame implements ActionListener {
    // ArrayList to store staff and vacancies
    private ArrayList<StaffHire> staffList;
    private ArrayList<Vacancy> vacancyList;
    
    // GUI components - main panels
    private JTabbedPane tabbedPane;
    private JPanel fullTimePanel, partTimePanel, vacancyPanel, staffDisplayPanel, terminatePanel;
    
    // Full Time Panel components
    private JTextField ftVacancyNumberField, ftDesignationField, ftJobTypeField, ftStaffNameField, 
                      ftJoiningDateField, ftQualificationField, ftAppointedByField, 
                      ftSalaryField, ftWeeklyHoursField;
    private JCheckBox ftJoinedCheckBox;
    private JButton ftAddButton, ftClearButton;
    
    // Part Time Panel components
    private JTextField ptVacancyNumberField, ptDesignationField, ptJobTypeField, ptStaffNameField, 
                      ptJoiningDateField, ptQualificationField, ptAppointedByField, 
                      ptWorkingHourField, ptWagesPerHourField, ptShiftsField;
    private JCheckBox ptJoinedCheckBox;
    private JButton ptAddButton, ptClearButton;
    
    // Vacancy Panel components
    private JTextField vacancyIdField, vacancyDesignationField, vacancyJobTypeField;
    private JButton addVacancyButton, clearVacancyButton;
    
    // Staff Display Panel components
    private JButton displayAllStaffButton, displayTerminatedStaffButton;
    private JTextArea staffDisplayArea;
    
    // Terminate Panel components
    private JComboBox<String> activeStaffComboBox;
    private JButton terminateButton, refreshButton;
    
    // Vacancy Display components
    private JButton displayAllVacanciesButton;
    private JTextArea vacancyDisplayArea;
    
    // Colors
    private Color backgroundColor = new Color(240, 248, 255); // AliceBlue
    private Color headerColor = new Color(44, 62, 80);      // Dark Blue
    private Color buttonColor = Color.WHITE;                // White background
    private Color textColor = Color.WHITE;
    private Color fieldBgColor = new Color(245, 245, 245);  // Light Gray
    private Color borderColor = new Color(189, 195, 199);   // Light Gray Border
    
    /**
     * Constructor for the RecruitmentSystem class
     */
    public RecruitmentSystem() {
        super("Staff Recruitment System");
        
        // Load data from files
        staffList = FileHandler.loadStaffData();
        vacancyList = FileHandler.loadVacancyData();
        
        // Set up the GUI
        setupGUI();
        
        // Configure the JFrame
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Add window listener to save data on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FileHandler.saveStaffData(staffList);
                FileHandler.saveVacancyData(vacancyList);
            }
        });
        
        setVisible(true);
    }
    
    /**
     * Method to set up the GUI components
     */
    private void setupGUI() {
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(backgroundColor);
        tabbedPane.setForeground(headerColor);
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Create main panels
        fullTimePanel = createFullTimePanel();
        partTimePanel = createPartTimePanel();
        vacancyPanel = createVacancyPanel();
        staffDisplayPanel = createStaffDisplayPanel();
        terminatePanel = createTerminatePanel();
        
        // Add panels to tabbed pane
        tabbedPane.addTab("Full Time Staff", fullTimePanel);
        tabbedPane.addTab("Part Time Staff", partTimePanel);
        tabbedPane.addTab("Vacancy Management", vacancyPanel);
        tabbedPane.addTab("Staff Display", staffDisplayPanel);
        tabbedPane.addTab("Terminate Staff", terminatePanel);
        
        // Add tabbed pane to frame
        add(tabbedPane);
    }
    
    /**
     * Creates a styled text field with placeholder
     */
    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setBackground(fieldBgColor);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Add placeholder functionality
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
        
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
        
        return textField;
    }
    
    /**
     * Creates panel for Full Time Staff
     */
    private JPanel createFullTimePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(headerColor);
        JLabel titleLabel = new JLabel("Full Time Staff Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(textColor);
        titlePanel.add(titleLabel);
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        formPanel.setBackground(backgroundColor);
        
        // Initialize text fields with placeholders
        ftVacancyNumberField = createTextField("Enter vacancy number");
        ftDesignationField = createTextField("Enter designation");
        ftJobTypeField = createTextField("Enter job type");
        ftStaffNameField = createTextField("Enter staff name");
        ftJoiningDateField = createTextField("DD/MM/YYYY");
        ftQualificationField = createTextField("Enter qualification");
        ftAppointedByField = createTextField("Enter appointer");
        ftSalaryField = createTextField("Enter salary");
        ftWeeklyHoursField = createTextField("Enter weekly hours");
        
        // Add vacancy field listener for auto-fill
        ftVacancyNumberField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    String text = ftVacancyNumberField.getText();
                    if (!text.equals("Enter vacancy number")) {
                        int vacancyId = Integer.parseInt(text);
                        for (Vacancy vacancy : vacancyList) {
                            if (vacancy.getVacancyId() == vacancyId && vacancy.isOpen()) {
                                ftDesignationField.setText(vacancy.getDesignation());
                                ftDesignationField.setForeground(Color.BLACK);
                                ftJobTypeField.setText(vacancy.getJobType());
                                ftJobTypeField.setForeground(Color.BLACK);
                                
                                // Show success message to user
                                JOptionPane.showMessageDialog(panel,
                                    "Found vacancy #" + vacancyId + ". Job details auto-filled.",
                                    "Auto-fill Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    // Do nothing if the input is not a valid number
                }
            }
        });
        
        // Initialize checkbox with larger font
        ftJoinedCheckBox = new JCheckBox("Joined");
        ftJoinedCheckBox.setBackground(backgroundColor);
        ftJoinedCheckBox.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add components to form panel
        formPanel.add(createFormLabel("Vacancy Number:"));
        formPanel.add(ftVacancyNumberField);
        formPanel.add(createFormLabel("Designation:"));
        formPanel.add(ftDesignationField);
        formPanel.add(createFormLabel("Job Type:"));
        formPanel.add(ftJobTypeField);
        formPanel.add(createFormLabel("Staff Name:"));
        formPanel.add(ftStaffNameField);
        formPanel.add(createFormLabel("Joining Date:"));
        formPanel.add(ftJoiningDateField);
        formPanel.add(createFormLabel("Qualification:"));
        formPanel.add(ftQualificationField);
        formPanel.add(createFormLabel("Appointed By:"));
        formPanel.add(ftAppointedByField);
        formPanel.add(createFormLabel("Joined:"));
        formPanel.add(ftJoinedCheckBox);
        formPanel.add(createFormLabel("Salary:"));
        formPanel.add(ftSalaryField);
        formPanel.add(createFormLabel("Weekly Hours:"));
        formPanel.add(ftWeeklyHoursField);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(backgroundColor);
        
        ftAddButton = createButton("Add Full Time Staff");
        ftClearButton = createButton("Clear Fields");
        
        buttonPanel.add(ftAddButton);
        buttonPanel.add(ftClearButton);
        
        // Add panels to main panel
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Creates panel for Part Time Staff
     */
    private JPanel createPartTimePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(headerColor);
        JLabel titleLabel = new JLabel("Part Time Staff Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(textColor);
        titlePanel.add(titleLabel);
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(11, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        formPanel.setBackground(backgroundColor);
        
        // Initialize text fields with placeholders
        ptVacancyNumberField = createTextField("Enter vacancy number");
        ptDesignationField = createTextField("Enter designation");
        ptJobTypeField = createTextField("Enter job type");
        ptStaffNameField = createTextField("Enter staff name");
        ptJoiningDateField = createTextField("DD/MM/YYYY");
        ptQualificationField = createTextField("Enter qualification");
        ptAppointedByField = createTextField("Enter appointer");
        ptWorkingHourField = createTextField("Enter working hours");
        ptWagesPerHourField = createTextField("Enter hourly wage");
        ptShiftsField = createTextField("Enter shifts");
        
        // Add vacancy field listener for auto-fill
        ptVacancyNumberField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    String text = ptVacancyNumberField.getText();
                    if (!text.equals("Enter vacancy number")) {
                        int vacancyId = Integer.parseInt(text);
                        for (Vacancy vacancy : vacancyList) {
                            if (vacancy.getVacancyId() == vacancyId && vacancy.isOpen()) {
                                ptDesignationField.setText(vacancy.getDesignation());
                                ptDesignationField.setForeground(Color.BLACK);
                                ptJobTypeField.setText(vacancy.getJobType());
                                ptJobTypeField.setForeground(Color.BLACK);
                                
                                // Show success message to user
                                JOptionPane.showMessageDialog(panel,
                                    "Found vacancy #" + vacancyId + ". Job details auto-filled.",
                                    "Auto-fill Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    // Do nothing if the input is not a valid number
                }
            }
        });
        
        // Initialize checkbox with larger font
        ptJoinedCheckBox = new JCheckBox("Joined");
        ptJoinedCheckBox.setBackground(backgroundColor);
        ptJoinedCheckBox.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add components to form panel
        formPanel.add(createFormLabel("Vacancy Number:"));
        formPanel.add(ptVacancyNumberField);
        formPanel.add(createFormLabel("Designation:"));
        formPanel.add(ptDesignationField);
        formPanel.add(createFormLabel("Job Type:"));
        formPanel.add(ptJobTypeField);
        formPanel.add(createFormLabel("Staff Name:"));
        formPanel.add(ptStaffNameField);
        formPanel.add(createFormLabel("Joining Date:"));
        formPanel.add(ptJoiningDateField);
        formPanel.add(createFormLabel("Qualification:"));
        formPanel.add(ptQualificationField);
        formPanel.add(createFormLabel("Appointed By:"));
        formPanel.add(ptAppointedByField);
        formPanel.add(createFormLabel("Joined:"));
        formPanel.add(ptJoinedCheckBox);
        formPanel.add(createFormLabel("Working Hours:"));
        formPanel.add(ptWorkingHourField);
        formPanel.add(createFormLabel("Wages Per Hour:"));
        formPanel.add(ptWagesPerHourField);
        formPanel.add(createFormLabel("Shifts:"));
        formPanel.add(ptShiftsField);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(backgroundColor);
        
        ptAddButton = createButton("Add Part Time Staff");
        ptClearButton = createButton("Clear Fields");
        
        buttonPanel.add(ptAddButton);
        buttonPanel.add(ptClearButton);
        
        // Add panels to main panel
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Creates panel for Vacancy Management
     */
    private JPanel createVacancyPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(headerColor);
        JLabel titleLabel = new JLabel("Vacancy Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(textColor);
        titlePanel.add(titleLabel);
        
        // Create main content panel
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 30, 30));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        contentPanel.setBackground(backgroundColor);
        
        // Create form panel for adding vacancies
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 15, 20));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(headerColor, 2),
            "Add New Vacancy", 
            TitledBorder.LEFT, 
            TitledBorder.TOP, 
            new Font("Arial", Font.BOLD, 16),
            headerColor
        ));
        formPanel.setBackground(backgroundColor);
        
        // Initialize text fields with placeholders
        vacancyIdField = createTextField("Enter vacancy ID");
        vacancyDesignationField = createTextField("Enter designation");
        vacancyJobTypeField = createTextField("Enter job type");
        
        // Add components to form panel
        formPanel.add(createFormLabel("Vacancy ID:"));
        formPanel.add(vacancyIdField);
        formPanel.add(createFormLabel("Designation:"));
        formPanel.add(vacancyDesignationField);
        formPanel.add(createFormLabel("Job Type:"));
        formPanel.add(vacancyJobTypeField);
        
        // Add buttons
        addVacancyButton = createButton("Add Vacancy");
        clearVacancyButton = createButton("Clear Fields");
        
        formPanel.add(addVacancyButton);
        formPanel.add(clearVacancyButton);
        
        // Create display panel for vacancies
        JPanel displayPanel = new JPanel(new BorderLayout(0, 15));
        displayPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(headerColor, 2),
            "Vacancy List", 
            TitledBorder.LEFT, 
            TitledBorder.TOP, 
            new Font("Arial", Font.BOLD, 16),
            headerColor
        ));
        displayPanel.setBackground(backgroundColor);
        
        vacancyDisplayArea = new JTextArea(15, 30);
        vacancyDisplayArea.setEditable(false);
        vacancyDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        vacancyDisplayArea.setBackground(fieldBgColor);
        vacancyDisplayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(vacancyDisplayArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        
        displayAllVacanciesButton = createButton("Display All Vacancies");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(displayAllVacanciesButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        displayPanel.add(scrollPane, BorderLayout.CENTER);
        displayPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add panels to content panel
        contentPanel.add(formPanel);
        contentPanel.add(displayPanel);
        
        // Add panels to main panel
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Creates panel for Staff Display
     */
    private JPanel createStaffDisplayPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(headerColor);
        JLabel titleLabel = new JLabel("Staff Display");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(textColor);
        titlePanel.add(titleLabel);
        
        // Create display area
        staffDisplayArea = new JTextArea();
        staffDisplayArea.setEditable(false);
        staffDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        staffDisplayArea.setBackground(fieldBgColor);
        staffDisplayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(staffDisplayArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(backgroundColor);
        
        displayAllStaffButton = createButton("Display All Staff");
        displayTerminatedStaffButton = createButton("Display Terminated Staff");
        
        buttonPanel.add(displayAllStaffButton);
        buttonPanel.add(displayTerminatedStaffButton);
        
        // Add panels to main panel
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Creates panel for Terminate Staff
     */
    private JPanel createTerminatePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(headerColor);
        JLabel titleLabel = new JLabel("Terminate Staff");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(textColor);
        titlePanel.add(titleLabel);
        
        // Create content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 20));
        contentPanel.setBackground(backgroundColor);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Create active staff panel
        JPanel activeStaffPanel = new JPanel(new BorderLayout(0, 15));
        activeStaffPanel.setBackground(backgroundColor);
        activeStaffPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(headerColor, 2),
            "Active Staff List", 
            TitledBorder.LEFT, 
            TitledBorder.TOP, 
            new Font("Arial", Font.BOLD, 16),
            headerColor
        ));
        
        // Create staff list area
        JTextArea activeStaffListArea = new JTextArea(12, 40);
        activeStaffListArea.setEditable(false);
        activeStaffListArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        activeStaffListArea.setBackground(fieldBgColor);
        
        JScrollPane staffScrollPane = new JScrollPane(activeStaffListArea);
        staffScrollPane.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        
        // Create label with larger font
        JLabel listLabel = new JLabel("Select from the active staff list below:");
        listLabel.setFont(new Font("Arial", Font.BOLD, 14));
        listLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Add to active staff panel
        activeStaffPanel.add(listLabel, BorderLayout.NORTH);
        activeStaffPanel.add(staffScrollPane, BorderLayout.CENTER);
        
        // Create selection panel
        JPanel selectionPanel = new JPanel(new BorderLayout(10, 15));
        selectionPanel.setBackground(backgroundColor);
        selectionPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(headerColor, 2),
            "Terminate Staff", 
            TitledBorder.LEFT, 
            TitledBorder.TOP, 
            new Font("Arial", Font.BOLD, 16),
            headerColor
        ));
        
        // Create input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        inputPanel.setBackground(backgroundColor);
        
        // Create staff ID field
        JLabel staffIdLabel = createFormLabel("Enter Staff ID to Terminate:");
        JTextField staffIdField = createTextField("Enter Vacancy Number");
        
        inputPanel.add(staffIdLabel);
        inputPanel.add(staffIdField);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(backgroundColor);
        
        terminateButton = createButton("Terminate Staff");
        refreshButton = createButton("Refresh List");
        
        buttonPanel.add(terminateButton);
        buttonPanel.add(refreshButton);
        
        // Add to selection panel
        selectionPanel.add(inputPanel, BorderLayout.NORTH);
        selectionPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Add panels to content panel
        contentPanel.add(activeStaffPanel, BorderLayout.CENTER);
        contentPanel.add(selectionPanel, BorderLayout.SOUTH);
        
        // Add to main panel
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        // Setup action listeners
        refreshButton.addActionListener(e -> updateActiveStaffList(activeStaffListArea));
        terminateButton.addActionListener(e -> {
            try {
                int vacancyNumber = Integer.parseInt(staffIdField.getText());
                terminateStaffById(vacancyNumber);
                updateActiveStaffList(activeStaffListArea);
                staffIdField.setText("Enter Vacancy Number");
                staffIdField.setForeground(Color.GRAY);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, 
                    "Please enter a valid staff ID (vacancy number)", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Initialize the active staff list
        updateActiveStaffList(activeStaffListArea);
        
        return panel;
    }
    
    /**
     * Updates the active staff list in the text area
     */
    private void updateActiveStaffList(JTextArea textArea) {
        textArea.setText("");
        StringBuilder sb = new StringBuilder();
        
        for (StaffHire staff : staffList) {
            if (staff.isActive() && staff instanceof PartTimeStaffHire) {
                sb.append("ID: ").append(staff.getVacancyNumber())
                  .append(" | Name: ").append(staff.getStaffName())
                  .append(" | Designation: ").append(staff.getDesignation())
                  .append("\n");
            }
        }
        
        if (sb.length() == 0) {
            sb.append("No active part-time staff found.");
        }
        
        textArea.setText(sb.toString());
    }
    
    /**
     * Terminates a staff member by their ID (vacancy number)
     */
    private void terminateStaffById(int vacancyNumber) {
        for (StaffHire staff : staffList) {
            if (staff.getVacancyNumber() == vacancyNumber && 
                staff instanceof PartTimeStaffHire && 
                staff.isActive()) {
                
                PartTimeStaffHire partTimeStaff = (PartTimeStaffHire) staff;
                partTimeStaff.terminateStaff();
                
                // Save data
                FileHandler.saveStaffData(staffList);
                
                JOptionPane.showMessageDialog(this, 
                    "Staff with ID " + vacancyNumber + " terminated successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        
        JOptionPane.showMessageDialog(this, 
            "No active part-time staff found with ID " + vacancyNumber, 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Creates a styled label for form fields
     */
    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(51, 51, 51));
        return label;
    }
    
    /**
     * Creates a styled button
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        button.addActionListener(this);
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(240, 240, 240)); // Light gray on hover
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
        
        return button;
    }
    
    /**
     * Method to handle button clicks
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        // Full Time Staff actions
        if (source == ftAddButton) {
            addFullTimeStaff();
        } else if (source == ftClearButton) {
            clearFullTimeFields();
        }
        
        // Part Time Staff actions
        else if (source == ptAddButton) {
            addPartTimeStaff();
        } else if (source == ptClearButton) {
            clearPartTimeFields();
        }
        
        // Vacancy actions
        else if (source == addVacancyButton) {
            addVacancy();
        } else if (source == clearVacancyButton) {
            clearVacancyFields();
        } else if (source == displayAllVacanciesButton) {
            displayAllVacancies();
        }
        
        // Staff Display actions
        else if (source == displayAllStaffButton) {
            displayAllStaff();
        } else if (source == displayTerminatedStaffButton) {
            displayTerminatedStaff();
        }
        
        // Terminate actions - handled by lambda in createTerminatePanel
    }
    
    /**
     * Method to add a full-time staff
     */
    private void addFullTimeStaff() {
        try {
            // Get input values
            String vacancyNumberText = ftVacancyNumberField.getText();
            if (vacancyNumberText.equals("Enter vacancy number")) {
                JOptionPane.showMessageDialog(this, "Please enter a vacancy number!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int vacancyNumber = Integer.parseInt(vacancyNumberText);
            
            String designation = ftDesignationField.getText();
            if (designation.equals("Enter designation")) designation = "";
            
            String jobType = ftJobTypeField.getText();
            if (jobType.equals("Enter job type")) jobType = "";
            
            String staffName = ftStaffNameField.getText();
            if (staffName.equals("Enter staff name")) staffName = "";
            
            String joiningDate = ftJoiningDateField.getText();
            if (joiningDate.equals("DD/MM/YYYY")) joiningDate = "";
            
            String qualification = ftQualificationField.getText();
            if (qualification.equals("Enter qualification")) qualification = "";
            
            String appointedBy = ftAppointedByField.getText();
            if (appointedBy.equals("Enter appointer")) appointedBy = "";
            
            boolean joined = ftJoinedCheckBox.isSelected();
            
            String salaryText = ftSalaryField.getText();
            if (salaryText.equals("Enter salary")) {
                JOptionPane.showMessageDialog(this, "Please enter a salary amount!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double salary = Double.parseDouble(salaryText);
            
            String weeklyHoursText = ftWeeklyHoursField.getText();
            if (weeklyHoursText.equals("Enter weekly hours")) {
                JOptionPane.showMessageDialog(this, "Please enter weekly hours!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int weeklyHours = Integer.parseInt(weeklyHoursText);
            
            // Check for empty fields
            if (designation.isEmpty() || jobType.isEmpty() || staffName.isEmpty() || 
                joiningDate.isEmpty() || qualification.isEmpty() || appointedBy.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if vacancy number exists in vacancy list
            boolean vacancyExists = false;
            for (Vacancy vacancy : vacancyList) {
                if (vacancy.getVacancyId() == vacancyNumber && vacancy.isOpen()) {
                    vacancyExists = true;
                    vacancy.closeVacancy(); // Close the vacancy once staff is hired
                    break;
                }
            }
            
            if (!vacancyExists) {
                JOptionPane.showMessageDialog(this, "No open vacancy found with this number!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if vacancy number already has staff assigned
            for (StaffHire staff : staffList) {
                if (staff.getVacancyNumber() == vacancyNumber && staff.isActive()) {
                    JOptionPane.showMessageDialog(this, "This vacancy already has an active staff member!", 
                                                 "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Create and add full-time staff
            FullTimeStaffHire fullTimeStaff = new FullTimeStaffHire(vacancyNumber, designation, 
                                                                   jobType, staffName, joiningDate, 
                                                                   qualification, appointedBy, joined, 
                                                                   salary, weeklyHours);
            staffList.add(fullTimeStaff);
            
            // Save data
            FileHandler.saveStaffData(staffList);
            FileHandler.saveVacancyData(vacancyList);
            
            JOptionPane.showMessageDialog(this, "Full-time staff added successfully!", 
                                         "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFullTimeFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid number values!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Method to add a part-time staff
     */
    private void addPartTimeStaff() {
        try {
            // Get input values
            String vacancyNumberText = ptVacancyNumberField.getText();
            if (vacancyNumberText.equals("Enter vacancy number")) {
                JOptionPane.showMessageDialog(this, "Please enter a vacancy number!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int vacancyNumber = Integer.parseInt(vacancyNumberText);
            
            String designation = ptDesignationField.getText();
            if (designation.equals("Enter designation")) designation = "";
            
            String jobType = ptJobTypeField.getText();
            if (jobType.equals("Enter job type")) jobType = "";
            
            String staffName = ptStaffNameField.getText();
            if (staffName.equals("Enter staff name")) staffName = "";
            
            String joiningDate = ptJoiningDateField.getText();
            if (joiningDate.equals("DD/MM/YYYY")) joiningDate = "";
            
            String qualification = ptQualificationField.getText();
            if (qualification.equals("Enter qualification")) qualification = "";
            
            String appointedBy = ptAppointedByField.getText();
            if (appointedBy.equals("Enter appointer")) appointedBy = "";
            
            boolean joined = ptJoinedCheckBox.isSelected();
            
            String workingHourText = ptWorkingHourField.getText();
            if (workingHourText.equals("Enter working hours")) {
                JOptionPane.showMessageDialog(this, "Please enter working hours!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int workingHour = Integer.parseInt(workingHourText);
            
            String wagesText = ptWagesPerHourField.getText();
            if (wagesText.equals("Enter hourly wage")) {
                JOptionPane.showMessageDialog(this, "Please enter hourly wages!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double wagesPerHour = Double.parseDouble(wagesText);
            
            String shifts = ptShiftsField.getText();
            if (shifts.equals("Enter shifts")) shifts = "";
            
            // Check for empty fields
            if (designation.isEmpty() || jobType.isEmpty() || staffName.isEmpty() || 
                joiningDate.isEmpty() || qualification.isEmpty() || appointedBy.isEmpty() || 
                shifts.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if vacancy number exists in vacancy list
            boolean vacancyExists = false;
            for (Vacancy vacancy : vacancyList) {
                if (vacancy.getVacancyId() == vacancyNumber && vacancy.isOpen()) {
                    vacancyExists = true;
                    vacancy.closeVacancy(); // Close the vacancy once staff is hired
                    break;
                }
            }
            
            if (!vacancyExists) {
                JOptionPane.showMessageDialog(this, "No open vacancy found with this number!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if vacancy number already has staff assigned
            for (StaffHire staff : staffList) {
                if (staff.getVacancyNumber() == vacancyNumber && staff.isActive()) {
                    JOptionPane.showMessageDialog(this, "This vacancy already has an active staff member!", 
                                                 "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Create and add part-time staff
            PartTimeStaffHire partTimeStaff = new PartTimeStaffHire(vacancyNumber, designation, 
                                                                   jobType, staffName, joiningDate, 
                                                                   qualification, appointedBy, joined, 
                                                                   workingHour, wagesPerHour, shifts);
            staffList.add(partTimeStaff);
            
            // Save data
            FileHandler.saveStaffData(staffList);
            FileHandler.saveVacancyData(vacancyList);
            
            JOptionPane.showMessageDialog(this, "Part-time staff added successfully!", 
                                         "Success", JOptionPane.INFORMATION_MESSAGE);
            clearPartTimeFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid number values!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Method to add a new vacancy
     */
    private void addVacancy() {
        try {
            // Get input values
            String vacancyIdText = vacancyIdField.getText();
            if (vacancyIdText.equals("Enter vacancy ID")) {
                JOptionPane.showMessageDialog(this, "Please enter a vacancy ID!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int vacancyId = Integer.parseInt(vacancyIdText);
            
            String designation = vacancyDesignationField.getText();
            if (designation.equals("Enter designation")) designation = "";
            
            String jobType = vacancyJobTypeField.getText();
            if (jobType.equals("Enter job type")) jobType = "";
            
            // Check for empty fields
            if (designation.isEmpty() || jobType.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if vacancy ID already exists
            for (Vacancy vacancy : vacancyList) {
                if (vacancy.getVacancyId() == vacancyId) {
                    JOptionPane.showMessageDialog(this, "Vacancy ID already exists!", 
                                                 "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Create and add vacancy
            Vacancy vacancy = new Vacancy(vacancyId, designation, jobType);
            vacancyList.add(vacancy);
            
            // Save data
            FileHandler.saveVacancyData(vacancyList);
            
            JOptionPane.showMessageDialog(this, "Vacancy added successfully!", 
                                         "Success", JOptionPane.INFORMATION_MESSAGE);
            clearVacancyFields();
            displayAllVacancies();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid vacancy ID!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Method to display all staff
     */
    private void displayAllStaff() {
        staffDisplayArea.setText("");
        
        if (staffList.isEmpty()) {
            staffDisplayArea.setText("No staff members found.");
            return;
        }
        
        StringBuilder output = new StringBuilder();
        output.append("=== ALL STAFF MEMBERS ===\n\n");
        
        for (int i = 0; i < staffList.size(); i++) {
            StaffHire staff = staffList.get(i);
            
            output.append("Staff #").append(i).append("\n");
            output.append("Vacancy Number: ").append(staff.getVacancyNumber()).append("\n");
            output.append("Name: ").append(staff.getStaffName()).append("\n");
            output.append("Designation: ").append(staff.getDesignation()).append("\n");
            output.append("Job Type: ").append(staff.getJobType()).append("\n");
            output.append("Active: ").append(staff.isActive() ? "Yes" : "No").append("\n");
            
            if (staff instanceof FullTimeStaffHire) {
                FullTimeStaffHire ftStaff = (FullTimeStaffHire) staff;
                output.append("Staff Type: Full Time\n");
                output.append("Salary: ").append(ftStaff.getSalary()).append("\n");
                output.append("Weekly Hours: ").append(ftStaff.getWeeklyFractionalHours()).append("\n");
            } else if (staff instanceof PartTimeStaffHire) {
                PartTimeStaffHire ptStaff = (PartTimeStaffHire) staff;
                output.append("Staff Type: Part Time\n");
                output.append("Working Hours: ").append(ptStaff.getWorkingHour()).append("\n");
                output.append("Wages Per Hour: ").append(ptStaff.getWagesPerHour()).append("\n");
                output.append("Shifts: ").append(ptStaff.getShifts()).append("\n");
                output.append("Terminated: ").append(ptStaff.isTerminated() ? "Yes" : "No").append("\n");
            }
            
            output.append("\n-----------------------------\n\n");
        }
        
        staffDisplayArea.setText(output.toString());
    }
    
    /**
     * Method to display terminated staff
     */
    private void displayTerminatedStaff() {
        staffDisplayArea.setText("");
        
        StringBuilder output = new StringBuilder();
        output.append("=== TERMINATED STAFF MEMBERS ===\n\n");
        
        boolean found = false;
        
        for (int i = 0; i < staffList.size(); i++) {
            StaffHire staff = staffList.get(i);
            
            if (!staff.isActive() && staff instanceof PartTimeStaffHire) {
                found = true;
                PartTimeStaffHire ptStaff = (PartTimeStaffHire) staff;
                
                output.append("Staff #").append(i).append("\n");
                output.append("Vacancy Number: ").append(staff.getVacancyNumber()).append("\n");
                output.append("Designation: ").append(staff.getDesignation()).append("\n");
                output.append("Job Type: ").append(staff.getJobType()).append("\n");
                output.append("Terminated: Yes\n");
                
                output.append("\n-----------------------------\n\n");
            }
        }
        
        if (!found) {
            output.append("No terminated staff members found.");
        }
        
        staffDisplayArea.setText(output.toString());
    }
    
    /**
     * Method to display all vacancies
     */
    private void displayAllVacancies() {
        vacancyDisplayArea.setText("");
        
        if (vacancyList.isEmpty()) {
            vacancyDisplayArea.setText("No vacancies found.");
            return;
        }
        
        StringBuilder output = new StringBuilder();
        output.append("=== ALL VACANCIES ===\n\n");
        
        for (Vacancy vacancy : vacancyList) {
            output.append("Vacancy ID: ").append(vacancy.getVacancyId()).append("\n");
            output.append("Designation: ").append(vacancy.getDesignation()).append("\n");
            output.append("Job Type: ").append(vacancy.getJobType()).append("\n");
            output.append("Status: ").append(vacancy.isOpen() ? "Open" : "Closed").append("\n");
            output.append("\n-----------------------------\n\n");
        }
        
        vacancyDisplayArea.setText(output.toString());
    }
    
    /**
     * Method to clear full-time staff fields
     */
    private void clearFullTimeFields() {
        ftVacancyNumberField.setText("Enter vacancy number");
        ftVacancyNumberField.setForeground(Color.GRAY);
        ftDesignationField.setText("Enter designation");
        ftDesignationField.setForeground(Color.GRAY);
        ftJobTypeField.setText("Enter job type");
        ftJobTypeField.setForeground(Color.GRAY);
        ftStaffNameField.setText("Enter staff name");
        ftStaffNameField.setForeground(Color.GRAY);
        ftJoiningDateField.setText("DD/MM/YYYY");
        ftJoiningDateField.setForeground(Color.GRAY);
        ftQualificationField.setText("Enter qualification");
        ftQualificationField.setForeground(Color.GRAY);
        ftAppointedByField.setText("Enter appointer");
        ftAppointedByField.setForeground(Color.GRAY);
        ftSalaryField.setText("Enter salary");
        ftSalaryField.setForeground(Color.GRAY);
        ftWeeklyHoursField.setText("Enter weekly hours");
        ftWeeklyHoursField.setForeground(Color.GRAY);
        ftJoinedCheckBox.setSelected(false);
    }
    
    /**
     * Method to clear part-time staff fields
     */
    private void clearPartTimeFields() {
        ptVacancyNumberField.setText("Enter vacancy number");
        ptVacancyNumberField.setForeground(Color.GRAY);
        ptDesignationField.setText("Enter designation");
        ptDesignationField.setForeground(Color.GRAY);
        ptJobTypeField.setText("Enter job type");
        ptJobTypeField.setForeground(Color.GRAY);
        ptStaffNameField.setText("Enter staff name");
        ptStaffNameField.setForeground(Color.GRAY);
        ptJoiningDateField.setText("DD/MM/YYYY");
        ptJoiningDateField.setForeground(Color.GRAY);
        ptQualificationField.setText("Enter qualification");
        ptQualificationField.setForeground(Color.GRAY);
        ptAppointedByField.setText("Enter appointer");
        ptAppointedByField.setForeground(Color.GRAY);
        ptWorkingHourField.setText("Enter working hours");
        ptWorkingHourField.setForeground(Color.GRAY);
        ptWagesPerHourField.setText("Enter hourly wage");
        ptWagesPerHourField.setForeground(Color.GRAY);
        ptShiftsField.setText("Enter shifts");
        ptShiftsField.setForeground(Color.GRAY);
        ptJoinedCheckBox.setSelected(false);
    }
    
    /**
     * Method to clear vacancy fields
     */
    private void clearVacancyFields() {
        vacancyIdField.setText("Enter vacancy ID");
        vacancyIdField.setForeground(Color.GRAY);
        vacancyDesignationField.setText("Enter designation");
        vacancyDesignationField.setForeground(Color.GRAY);
        vacancyJobTypeField.setText("Enter job type");
        vacancyJobTypeField.setForeground(Color.GRAY);
    }
    
    /**
     * Inner class to redirect console output to a text area
     */
    private class PrintConsoleToTextArea extends PrintStream {
        private JTextArea textArea;
        private PrintStream originalOut;
        
        public PrintConsoleToTextArea(PrintStream originalOut, JTextArea textArea) {
            super(originalOut);
            this.originalOut = originalOut;
            this.textArea = textArea;
        }
        
        @Override
        public void println(String x) {
            textArea.append(x + "\n");
            originalOut.println(x);
        }
        
        public PrintStream getOriginalOut() {
            return originalOut;
        }
    }
    
    /**
     * Main method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create recruitment system
        SwingUtilities.invokeLater(() -> new RecruitmentSystem());
    }
} 