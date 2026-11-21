
package gui;
import javax.swing.*;
import java.awt.*;
import employee.*;
import manager.EmployeeManager;
// JDialog me4 JPanel because we want a separate dialog window to appear when the user clicks Update Employee
//JPanel is a container inside another window (frame)
//JDialog is a separate dialog window that belongs to the main application
public class UpdateEmployeeDialog extends JDialog {

    private EmployeeManager employeeManager;
    private Employee oldEmployee;
    //onUpdated	Stores an action to execute after a successful update
    //() -> searchEmployees() deh fi el update fi viewEmployeePanel ,When someone executes this action, call searchEmployees() 
   //we are passing an action to UpdateEmployeeDialog, not executing searchEmployees() immediately
    private Runnable onUpdated;//Runnable represents an action that can be executed later

    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField positionField;

    private JComboBox<String> departmentBox;

    private JTextField basicSalaryField;
    private JTextField bonusField;
    private JTextField hourlyRateField;
    private JTextField hoursWorkedField;

    public UpdateEmployeeDialog(
            Window owner, // the main window that owns el dilog which is MainFrame
            Employee employee,// employee selected by user
            EmployeeManager employeeManager,
            Runnable onUpdated) {
         //el parent class constructor (JDialog)
         //APPLICATION_MODAL means the user must close this dialog before interacting with the other application windows
        super(owner, "Update Employee", ModalityType.APPLICATION_MODAL);

        this.oldEmployee = employee;
        this.employeeManager = employeeManager;
        this.onUpdated = onUpdated;

        setLayout(new BorderLayout(10, 10));
      //0: Number of rows is calculated automatically
      //2: Two columns First column contains labels,Second column contains input fields
      //The last two values represent horizontal and vertical gaps
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        //Adds empty space around the form
        //This prevents the form components from touching the edges
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Employee ID m4 byt8yr
        // we use  string.valueof because getId() returns an integer, while JTextField expects a String
        idField = new JTextField(String.valueOf(employee.getId()));
        idField.setEditable(false); // prevent user from editing it 

        // Personal information
        nameField = new JTextField(employee.getName());
        ageField = new JTextField(String.valueOf(employee.getAge()));
        positionField = new JTextField(employee.getPosition());

        departmentBox = new JComboBox<>(new String[]{
                "IT",
                "HR",
                "Human Resources",
                "Finance",
                "Marketing",
                "Sales"
        });

        // Preserve the existing department if it isn't in the options
        String currentDepartment = employee.getDepartment();

        if (currentDepartment != null) {
            departmentBox.setSelectedItem(currentDepartment);//setSelectedItem() selects the existing department if available

            if (!currentDepartment.equals(departmentBox.getSelectedItem())) {
                departmentBox.addItem(currentDepartment); //lw el dep not available add it
                departmentBox.setSelectedItem(currentDepartment);//then select it
            }
        }

        // Add personal information to the form
        addField(formPanel, "Employee ID:", idField);
        addField(formPanel, "Name:", nameField);
        addField(formPanel, "Age:", ageField);
        addField(formPanel, "Department:", departmentBox);
        addField(formPanel, "Position:", positionField);

        // Salary fields depend on employee type
        if (employee instanceof FullTimeEmployee) {

            FullTimeEmployee fullTime =
                    (FullTimeEmployee) employee; //casting 34an 23rf 2a call el functions

            basicSalaryField =
                    new JTextField(String.valueOf(fullTime.getBasicSalary()));

            bonusField =
                    new JTextField(String.valueOf(fullTime.getBonus()));

            addField(formPanel, "Basic Salary:", basicSalaryField);
            addField(formPanel, "Bonus:", bonusField);

        } else if (employee instanceof PartTimeEmployee) {

            PartTimeEmployee partTime =
                    (PartTimeEmployee) employee;

            hourlyRateField =
                    new JTextField(String.valueOf(partTime.getHourlyRate()));

            hoursWorkedField =
                    new JTextField(String.valueOf(partTime.getHoursWorked()));

            addField(formPanel, "Hourly Rate:", hourlyRateField);
            addField(formPanel, "Hours Worked:", hoursWorkedField);
        }

        // Buttons
        JButton saveButton = new JButton("Save Changes");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> saveChanges());//When Save Changes is clicked, saveChanges() executes

        cancelButton.addActionListener(e -> dispose());//When Cancel is clicked, dispose() closes the dialog 

        setSize(450, 420);
        setLocationRelativeTo(owner);
    }

    // Adds a label and its corresponding input field
    //25tsar bas kan momken without method
    private void addField(
            JPanel panel,
            String label,
            JComponent component) {

        panel.add(new JLabel(label));
        panel.add(component);
    }

    // Save updated employee information
    private void saveChanges() {

        try {
           //getText() retrieves the text entered by the user
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String position = positionField.getText().trim();

            String department =
                    (String) departmentBox.getSelectedItem();
               //from string to int
            int age = Integer.parseInt(ageText);

            if (name.isEmpty()
                    || position.isEmpty()
                    || department == null
                    || department.trim().isEmpty()
                    || age <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter valid employee information.");

                return;
            }

            int id = oldEmployee.getId();

            Employee updatedEmployee;

            // Update Full-Time Employee
            if (oldEmployee instanceof FullTimeEmployee) {

                double basicSalary =
                        Double.parseDouble(basicSalaryField.getText().trim());

                double bonus =
                        Double.parseDouble(bonusField.getText().trim());

                if (!Double.isFinite(basicSalary)
                        || !Double.isFinite(bonus)
                        || basicSalary < 0
                        || bonus < 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Salary values must be valid and non-negative.");

                    return;
                }

                updatedEmployee = new FullTimeEmployee(
                        id,
                        name,
                        age,
                        department,
                        position,
                        basicSalary,
                        bonus
                );

            }

            // Update Part-Time Employee
            else if (oldEmployee instanceof PartTimeEmployee) {

                double hourlyRate =
                        Double.parseDouble(hourlyRateField.getText().trim());

                double hoursWorked =
                        Double.parseDouble(hoursWorkedField.getText().trim());

                if (!Double.isFinite(hourlyRate)
                        || !Double.isFinite(hoursWorked)
                        || hourlyRate < 0
                        || hoursWorked < 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Salary values must be valid and non-negative.");

                    return;
                }

                updatedEmployee = new PartTimeEmployee(
                        id,
                        name,
                        age,
                        department,
                        position,
                        hourlyRate,
                        hoursWorked
                );

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Unknown employee type.");
                return;
            }

            // Update the employee in EmployeeManager
            boolean updated =
                    employeeManager.updateEmployee(updatedEmployee);

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Employee updated successfully.");

                dispose();//Closes the update dialog

                // Refresh the ViewEmployeesPanel
                if (onUpdated != null) {
                    onUpdated.run();//execute that action equivalent li calling searchEmployees()
                }

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Employee could not be updated.");
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values for age and salary.");
        }
    }
}
