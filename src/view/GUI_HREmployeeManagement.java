package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import model.Employee;
import model.User;
import DAO.EmployeeDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import util.SessionManager;
import util.SignOutButton;
import java.awt.Toolkit;

public class GUI_HREmployeeManagement extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private User loggedInEmployee;
    private JLabel employeeNameLabel;
    private JTable table;
    private JButton updatedataButton;
    private JButton deletedataButton;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    User loggedInEmployee = SessionManager.getLoggedInUser();
                    GUI_HREmployeeManagement employeeManagement = new GUI_HREmployeeManagement(loggedInEmployee);
                    employeeManagement.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     *
     * @throws IOException
     */
    public GUI_HREmployeeManagement(User loggedInEmployee) throws IOException {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_HREmployeeManagement.class.getResource("/img/logo.png")));
    	setTitle("MotorPH Payroll System");
        this.loggedInEmployee = loggedInEmployee;
        initialize();
        populateEmployeeTable();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 800);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/emp mngmnt.png");
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1280, 800);
        contentPane.add(mainPanel);
        mainPanel.setLayout(null);

        // Use the Sidebar class
        Sidebar sidebar = new Sidebar(loggedInEmployee);
        sidebar.setBounds(0, 92, 321, 680);
        mainPanel.add(sidebar);

        // Sign Out button initialization
        SignOutButton signOutButton = new SignOutButton(SignOutButton.getSignOutActionListener(this));
        signOutButton.setBounds(1125, 24, 111, 40);
        mainPanel.add(signOutButton);

        employeeNameLabel = new JLabel();
        employeeNameLabel.setBounds(707, 30, 400, 33);
        employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        employeeNameLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        mainPanel.add(employeeNameLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(390, 160, 818, 468);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane);

        table = new JTable();
        table.setRowMargin(12);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.setViewportView(table);

        // CRUD buttons
        JButton addemployeeButton = new JButton("Add Employee");
        addemployeeButton.setBounds(390, 663, 154, 51);
        addemployeeButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        addemployeeButton.setBackground(Color.WHITE);
        mainPanel.add(addemployeeButton);
        // Action listener for the "Add Employee" button
        addemployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EmployeeDialog dialog = new EmployeeDialog(GUI_HREmployeeManagement.this, true, null);
                dialog.setVisible(true);
                populateEmployeeTable(); // Refresh table after adding
            }
        });

        updatedataButton = new JButton("Update Data");
        updatedataButton.setBounds(578, 663, 154, 51);
        updatedataButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        updatedataButton.setBackground(Color.WHITE);
        updatedataButton.setEnabled(false);
        mainPanel.add(updatedataButton);
        // Action listener for the "Update Data" button
        updatedataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = table.convertRowIndexToModel(selectedRow); // Convert view row to model row
                    Integer id = (Integer) model.getValueAt(modelRow, 0);
                    String lastName = (String) model.getValueAt(modelRow, 1);
                    String firstName = (String) model.getValueAt(modelRow, 2);
                    String birthday = (String) model.getValueAt(modelRow, 3);
                    String address = (String) model.getValueAt(modelRow, 4);
                    String phoneNumber = (String) model.getValueAt(modelRow, 5);
                    String sssNumber = (String) model.getValueAt(modelRow, 6);
                    String philhealthNumber = (String) model.getValueAt(modelRow, 7);
                    String tinNumber = (String) model.getValueAt(modelRow, 8);
                    String pagibigNumber = (String) model.getValueAt(modelRow, 9);
                    String department = (String) model.getValueAt(modelRow, 10);
                    String status = (String) model.getValueAt(modelRow, 11);
                    String position = (String) model.getValueAt(modelRow, 12);
                    String immediateSupervisor = (String) model.getValueAt(modelRow, 13);
                    double basicSalary = Double.parseDouble(model.getValueAt(modelRow, 14).toString());
                    double grossSemiMonthlyRate = Double.parseDouble(model.getValueAt(modelRow, 15).toString());
                    double hourlyRate = Double.parseDouble(model.getValueAt(modelRow, 16).toString());

                    Employee employee = new Employee(id, lastName, firstName, birthday, address, phoneNumber,
                            sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, department, position, 
                            immediateSupervisor, basicSalary, grossSemiMonthlyRate, hourlyRate);

                    EmployeeDialog dialog = new EmployeeDialog(GUI_HREmployeeManagement.this, false, employee);
                    dialog.setVisible(true);
                    populateEmployeeTable(); // Refresh table after updating
                } else {
                    JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deletedataButton = new JButton("Delete Data");
        deletedataButton.setBounds(953, 663, 154, 51);
        deletedataButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        deletedataButton.setBackground(Color.WHITE);
        deletedataButton.setEnabled(false);
        mainPanel.add(deletedataButton);
        deletedataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int option = JOptionPane.showConfirmDialog(GUI_HREmployeeManagement.this, "Are you sure you want to delete this employee? Deletion is permanent.", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        int modelRow = table.convertRowIndexToModel(selectedRow); // Convert view row to model row
                        int idToDelete = (int) model.getValueAt(modelRow, 0);
                        boolean success = EmployeeDAO.deleteEmployee(idToDelete);
                        if (success) {
                            model.removeRow(modelRow);
                            JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Employee deleted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Error deleting employee.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Enable/disable buttons based on table selection
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0 && selectedRow < table.getRowCount()) {
                        // Enable the "Update Data" and "Delete Data" buttons
                        updatedataButton.setEnabled(true);
                        deletedataButton.setEnabled(true);
                    } else {
                        // Disable the "Update Data" and "Delete Data" buttons
                        updatedataButton.setEnabled(false);
                        deletedataButton.setEnabled(false);
                    }
                }
            }
        });

        // Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }

        // Initialize table model and row sorter
        model = new DefaultTableModel(
                new Object[]{"Employee #", "Last Name", "First Name", "Birthday", "Address", "Phone Number",
                        "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", "Department", "Status", "Position",
                        "Immediate Supervisor", "Basic Salary", "Gross Semi-monthly Rate", "Hourly Rate"},
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class; // Ensure Employee # is treated as an Integer
                }
                return super.getColumnClass(columnIndex);
            }
        };

        table.setModel(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Default sorting by Employee ID in ascending order
        sorter.setComparator(0, (id1, id2) -> ((Integer) id1).compareTo((Integer) id2));
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        sorter.sort(); // Ensure sorting is applied immediately
    }

    private void populateEmployeeTable() {
        // Clear the existing rows
        model.setRowCount(0);

        // Get all employees from the database
        List<Employee> employees = EmployeeDAO.getAllEmployees();

        // Add each employee to the table model
        for (Employee employee : employees) {
            model.addRow(new Object[]{
                    employee.getEmpId(),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getBirthday(),
                    employee.getAddress(),
                    employee.getPhoneNumber(),
                    employee.getSssNumber(),
                    employee.getPhilhealthNumber(),
                    employee.getTinNumber(),
                    employee.getPagibigNumber(),
                    employee.getDepartment(),
                    employee.getStatus(),
                    employee.getPosition(),
                    employee.getImmediateSupervisor(),
                    employee.getBasicSalary(),
                    employee.getGrossSemiMonthlyRate(),
                    employee.getHourlyRate()
            });
        }

        // Apply sorting
        sorter.sort();
    }
}
