# MotorPH Payroll System

This is a comprehensive, Java-based desktop payroll system designed to modernize and automate payroll processes. It provides a complete solution for managing employee information, tracking attendance, processing leave and overtime, calculating payroll, and generating reports.

This project was developed for an Advanced Object-Oriented Programming (AOOP) course, with a strong emphasis on clean architecture. It is built using the **Model-View-Controller (MVC)** pattern, with a separate **Data Access Object (DAO)** layer to manage database interactions.

-----

## Core Features

The system is divided into modules based on user roles (Employee, HR, Payroll, and IT):

  * **Authentication:** Secure login for all users, with password reset functionality.
  * **Employee Self-Service:**
      * View personal dashboard and information.
      * Record daily Time In and Time Out.
      * Submit leave and overtime requests.
      * View personal attendance, leave, and overtime history.
      * View and export personal payslips.
  * **HR Management:**
      * Full CRUD (Create, Read, Update, Delete) for employee records.
      * Batch-add new employees via CSV upload.
      * Approve or reject employee leave and overtime requests.
      * Access and view attendance records for all employees.
  * **Payroll Processing:**
      * Batch-calculate payroll for all employees based on attendance and overtime.
      * Ensures accurate calculation of gross pay, allowances, all deductions (SSS, PhilHealth, Pag-IBIG, Tax), and net pay.
      * Generate individual payslips and monthly summary reports.
  * **IT Administration:**
      * Manage system user credentials (add/delete users, change passwords).
      * Manage role-based access control (RBAC) by granting or revoking permissions for specific modules.
      * View system-wide login logs and credential change logs for auditing.

-----

## Architecture & Technology

  * **Core:** Java (JDK 19)
  * **Architecture:** Model-View-Controller (MVC) with a Data Access Object (DAO) layer.
  * **Database:** MySQL
  * **UI:** Java Swing
  * **Key Libraries:**
      * **FlatLaf:** For a modern, flat user interface.
      * **iText:** For generating and exporting PDF payslips and reports.
      * **OpenCSV / Commons CSV:** For batch CSV import/export.
      * **JCalendar:** For date-picker components.
      * **MySQL Connector:** For database connectivity.

-----

## Setup and Installation

To run the project locally, follow these steps.

**For a complete visual guide and walkthrough, visit our complete documentation site:**
**[https://motorph-aoop-group1-2324term3.my.canva.site/](https://motorph-aoop-group1-2324term3.my.canva.site/)**

1.  **Prerequisites:**

      * [Java Development Kit (JDK) 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html) or later.
      * A Java IDE (e.g., IntelliJ IDEA, Eclipse, NetBeans).
      * [suspicious link removed] (and a client like MySQL Workbench or DBeaver).

2.  **Clone the Repository:**

    ```bash
    git clone [your-repository-url]
    ```

3.  **Database Setup:**

      * Ensure your MySQL server is running.
      * Using a MySQL client, create a new database.
      * Import the database schema and data using the provided `.sql` dump file found in the repository.
      * The application reads database credentials from `config.properties` in the project root. Create or update that file with your local database settings, for example:
          ```properties
          db.host=127.0.0.1
          db.port=3306
          db.name=your_database_name
          db.user=root
          db.password=your_password_here
          ```

4.  **Run the Application:**

      * Open the project folder in your preferred Java IDE.
      * The IDE should automatically detect and build the project.
      * Locate the `main` class (e.g., `Main.java` or `Login.java`) in the `src` folder.
      * Right-click and select "Run" to launch the application. You should be directed to the login page.
