package gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;//el layout manager
import java.util.ArrayList;
import department.Department; // added this
import manager.DepartmentManager; // added this
import manager.EmployeeManager;
import employee.*;

// creating JPanel class me4 jFrame la2n already e7na 3amleen MainFrame fa e7na 3yzeen n7ot screen display el employees gowha m4 nft7 other window independent 3nha no
public class ViewEmployeesPanel extends JPanel {

    private EmployeeManager employeeManager;
    private DepartmentManager departmentManager; // added this

    private JTable employeeTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;
    private JComboBox<String> departmentBox;

    private JButton searchButton;
    private JButton refreshButton;
    private JButton deleteButton;
    private JButton updateButton;

    public ViewEmployeesPanel(EmployeeManager employeeManager, DepartmentManager departmentManager) { // added this
        // same as in AddEmployeePanel bnst2bel nafs el employeeManager mn el MainFrame
        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager; // added this

        setLayout(new BorderLayout(10, 10));

        // 1. Create table
        // array bi names of kol column
        String[] columns = {
                "ID", "Name", "Age", "Department",
                "Position", "Type", "Salary"
        };
        //start bi zero rows w names of columns
        tableModel = new DefaultTableModel(columns, 0) {
            // 34an el user may2dr4 y3del info of an employee direct from table cells no lazem from update feature
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //JTable 4kl el table w el display
        //DefaultTableModel mas2ool 3n el data ely gowa el table w tnzmha

        employeeTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(employeeTable);// table bi scroller

        // 2. Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout());

        searchField = new JTextField(15);// place where user will write
        //15 character initially bas momken 2ktar
        // user choose from these dep badal ma ykteb
        departmentBox = new JComboBox<>(); // added this
        // buttons
        searchButton = new JButton("Search");
        refreshButton = new JButton("Refresh");//display el employees tany mn el awal

        // adding components to the searchPanel
        searchPanel.add(new JLabel("Search ID / Name:"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Department:"));
        searchPanel.add(departmentBox);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        // 3. Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        updateButton = new JButton("Update Employee");
        deleteButton = new JButton("Delete Employee");

        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        // 4. Add panels to main panel ely heya viewemployeepannel
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 5. Connect buttons to methods
        //In Java Swing, creating a button is not enough. We also need to tell the program what should happen when the user clicks it
        //addActionListener(): Registers an action that happens when the user clicks the button
        //e: Represents the action event ely el user 3mloo which is pressing the button

        //searchEmployees(): The method that will execute when the button is clicked
        searchButton.addActionListener(e -> searchEmployees());

        //when refresh the search is cleared, the department filter is reset, and all employees are displayed again
        refreshButton.addActionListener(e -> {
            searchField.setText("");//ba5leeha empty clears the searchfield
            departmentBox.setSelectedIndex(0);//this resets the department filter to All Departments la2nha kanet at index 0
            loadEmployees(employeeManager.getAllEmployees());//returns a copy of employeelist to display
        });

        deleteButton.addActionListener(e -> deleteEmployee());
        updateButton.addActionListener(e -> updateEmployee());

        reloadDepartments(); // added this
        // 6. Display employees when screen opens
        //we want the JTable to display the existing employees immediately when ViewEmployeesPanel is created
        //Without this statement, the table might initially appear empty until the user clicks Refresh
        loadEmployees(employeeManager.getAllEmployees());
    }

    public void reloadDepartments() { // added this
        departmentBox.removeAllItems(); // added this
        departmentBox.addItem("All Departments"); // added this
        for (Department d : departmentManager.getAllDepartments()) { // added this
            departmentBox.addItem(d.getName()); // added this
        } // added this
    } // added this

    //display the employees stored in EmployeeManager inside the JTable
    private void loadEmployees(ArrayList<Employee> employees) {

        tableModel.setRowCount(0);//it removes all currently displayed rows ,this removes rows from the JTable model only. It does NOT delete employees from the original EmployeeManager ArrayList

        for (Employee employee : employees) {

            String type;
            // employee type
            if (employee instanceof FullTimeEmployee) {
                type = "Full-Time";
            } else {
                type = "Part-Time";
            }
            //Create an array for one table row (kol row represent employee)
            Object[] row = {
                    employee.getId(),
                    employee.getName(),
                    employee.getAge(),
                    employee.getDepartment(),
                    employee.getPosition(),
                    type,
                    employee.calculateSalary()
            };

            tableModel.addRow(row);//This inserts the employee information into the JTable
        }
    }

    // Search by ID or Name and filter by Department
    private void searchEmployees() {

        //getText()	Gets the text entered by the user
        //trim()	Removes spaces from the beginning and end
        //ely el user katbo in search
        String query = searchField.getText().trim().toLowerCase();

        //getSelectedItem(): Returns the item selected by the user mn el combobox
        Object selectedItem = departmentBox.getSelectedItem(); // added this
        String selectedDepartment = selectedItem != null ? selectedItem.toString() : "All Departments"; // added this


        //we create a new empty ArrayList,Its purpose is to store only employees who match the search conditions
        ArrayList<Employee> results = new ArrayList<>();

        for (Employee employee : employeeManager.getAllEmployees()) {

            boolean matchesSearch =
                    String.valueOf(employee.getId()).contains(query)//Converts the ID from an integer into a String ,Because contains() is a String method, and we want to search inside the ID
                            || (employee.getName() != null && employee.getName().toLowerCase().contains(query));

            boolean matchesDepartment =
                    selectedDepartment.equals("All Departments")
                            || (employee.getDepartment() != null
                            && employee.getDepartment().equalsIgnoreCase(selectedDepartment));

            if (matchesSearch && matchesDepartment) {
                results.add(employee);
            }
        }

        loadEmployees(results);
        //Handle the case where no employees are found
        if (results.isEmpty()) {
            // displays a message dialogue to the user
            JOptionPane.showMessageDialog(
                    this, // the current pannel that owns tha dialog
                    "No employees found.",//the message displayyed to the user
                    "Search Result", // dialog title
                    JOptionPane.INFORMATION_MESSAGE //Message icon/type
            );
        }
    }

    // Delete selected employee
    private void deleteEmployee() {

        int selectedRow = employeeTable.getSelectedRow();//getSelectedRow() returns the index of the row selected by the user
        //If no row is selected, it returns -1
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee first."
            );
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);// 0 col bta3 el id

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this employee?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION //displays Yes and No,The result is stored in confirm
        );

        if (confirm == JOptionPane.YES_OPTION) {

            boolean deleted = employeeManager.deleteEmployeeById(id);

            if (deleted) {
                JOptionPane.showMessageDialog(
                        this,
                        "Employee deleted successfully."
                );

                searchEmployees();
            }
        }
    }

// update feature

    private void updateEmployee() {

        int selectedRow = employeeTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee to update.");

            return;
        }

        // Convert selected row to model row
        int modelRow =
                employeeTable.convertRowIndexToModel(selectedRow);

        // Get employee ID
        int id = (int) tableModel.getValueAt(modelRow, 0);

        // Find employee in EmployeeManager
        Employee oldEmployee =
                employeeManager.findEmployeeById(id);

        if (oldEmployee == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Employee not found.");

            return;
        }

        // Open the update form
        UpdateEmployeeDialog dialog =
                new UpdateEmployeeDialog(
                        SwingUtilities.getWindowAncestor(this),
                        oldEmployee,
                        employeeManager,
                        () -> searchEmployees()
                );

        dialog.setVisible(true);
    }
    public void refreshTable() {
        searchField.setText("");
        if (departmentBox.getItemCount() > 0) { // added this
            departmentBox.setSelectedIndex(0); // added this
        } // added this
        loadEmployees(employeeManager.getAllEmployees());
    }
}
