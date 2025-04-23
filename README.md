# Staff Recruitment System

This is a Java-based Staff Recruitment System that allows for managing staff hiring, including both full-time and part-time staff. The system uses a Graphical User Interface (GUI) to interact with users and stores data in local files.

## Project Structure

The project consists of the following classes:

1. **StaffHire.java** - Base class that contains common attributes and methods for all staff hiring
2. **FullTimeStaffHire.java** - Subclass of StaffHire for managing full-time staff
3. **PartTimeStaffHire.java** - Subclass of StaffHire for managing part-time staff
4. **Vacancy.java** - Class for managing job vacancies
5. **FileHandler.java** - Utility class for handling file operations
6. **RecruitmentSystem.java** - Main GUI class that handles user interactions and manages staff and vacancy records

## Features

- Add full-time staff with details like vacancy number, designation, job type, staff name, etc.
- Add part-time staff with similar details plus working hours, wages, and shifts
- Add and manage vacancies with open/closed status
- Terminate staff by ID (shows list of active staff with IDs)
- Display terminated staff
- Display all staff members
- Display all vacancies with open/closed status
- Data persistence using local file system
- Attractive color interface with tabs for organization
- Clear button for input fields
- Input fields with placeholders for better user guidance
- Auto-fill job information when entering a valid vacancy ID
- Enhanced exception handling for invalid inputs
- Improved visual appearance with consistent styling

## How to Run

To compile and run the application:

```bash
javac StaffHire.java FullTimeStaffHire.java PartTimeStaffHire.java Vacancy.java FileHandler.java RecruitmentSystem.java
java RecruitmentSystem
```

## System Requirements

- Java Development Kit (JDK) 8 or higher

## Class Hierarchy

```
StaffHire
   |
   |--- FullTimeStaffHire
   |
   |--- PartTimeStaffHire
```

## GUI Overview

The GUI provides a tabbed interface with the following sections:

1. **Full Time Staff** - Add new full-time staff members with auto-fill capability
2. **Part Time Staff** - Add new part-time staff members with auto-fill capability
3. **Vacancy Management** - Add and view vacancies
4. **Staff Display** - View all staff or terminated staff
5. **Terminate Staff** - View active staff and terminate by entering staff ID

## Data Persistence

Data is stored in local files:
- staff_data.ser - Stores staff information
- vacancy_data.ser - Stores vacancy information

## User Experience Improvements

- Placeholder text in input fields guides users on what to enter
- When entering a valid vacancy ID, designation and job type fields automatically populate
- Improved visual styling with consistent colors and fonts
- Better error messages and input validation
- List of active staff shown when terminating staff

## Author

Created for a programming assignment in CS4001. 
