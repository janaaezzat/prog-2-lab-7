package gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import department.Department;
import manager.DepartmentManager; // added this
import manager.EmployeeManager;
import employee.*;

public class AddEmployeePanel extends JPanel {
    // el 7agat deh private 34an el class dah bas el yet7akem feha
    // Employee information fields
    private JTextField idField; //hena declared as class variables w bnrga3 ta7t ne3mlohom
    //initialize fields in the constructor or methods, so they can be accessed throughout the class.
    //34an han7taghom fl methods tanyeen zay el action listeners w el validation and buttons w keda
    private JTextField nameField;
    private JSpinner ageSpinner;
    private JTextField positionField;

    // Dropdowns
    private JComboBox<Department> departmentBox;
    private JComboBox<String> employeeTypeBox;

    // Buttons
    private JButton addButton;
    private JButton clearButton;

    // Salary section
    private JPanel salaryPanel;

    private JTextField basicSalaryField;
    private JTextField bonusField;

    private JTextField hourlyRateField;
    private JTextField hoursWorkedField;
    private EmployeeManager employeeManager;// keda el main creates EmployeeManager w passes same object ll main frame then to addemppanel fa te2dar add tezawed mn 5elal manager
    private DepartmentManager departmentManager; // added this
    // the constructor
    public AddEmployeePanel(EmployeeManager employeeManager, DepartmentManager departmentManager) { // added this
        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager; // added this
        // border layout leh 5 positions north, south, east, west, center
        // Main panel layout
        setLayout(new BorderLayout(15, 15));// 15, 15 is the horizontal and vertical gap between components

        // Add spacing around the entire panel
        setBorder(new EmptyBorder(20, 20, 20, 20));// mesa7a fadya ll gamaleyat

        // Create the three main sections
        JPanel headerPanel = createHeaderPanel(); // dol methods han3rfhom
        JPanel formPanel = createFormPanel();
        salaryPanel = createSalaryPanel();
        JPanel buttonPanel = createButtonPanel();

        // Combine employee information and salary information
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(salaryPanel, BorderLayout.CENTER);

        // Place the sections inside the main panel
        //add hena inherited method form main class Jpanel bt7ot a component in another component w el position elly 3ayzen n7ot feha
        //me4 far2a this.add or add bas
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        reloadDepartments(); // added this
        // Display the correct salary fields initially
        updateSalaryFields();

    }
    // el concept createHeaderPanel creates a Jpanel w adds title lable returns panel constructor puts it in north in this case w han3ml keda fl ba2y
    private JPanel createHeaderPanel() {
// el header panel dah 3shan el title btrga3 Jpanel w private brdo 34an el class dah bas el yet7akem feha
        JPanel panel = new JPanel(new BorderLayout()); //hn3ml panel w nrgh3a

        JLabel titleLabel = new JLabel("Add New Employee");

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(titleLabel, BorderLayout.WEST);

        return panel;
    }

    private JPanel createFormPanel() {
// new GridLayout(rows, columns, horizontalGap, verticalGap)
        JPanel panel = new JPanel(new GridLayout(0, 2, 12, 12));
        // ccreates border 7awalein el form b title for appearance bas
        panel.setBorder(
                new TitledBorder("Employee Information")
        );

        // Create input fields
        idField = new JTextField();
        nameField = new JTextField();
        ageSpinner = new JSpinner(new SpinnerNumberModel(25, 18, 100, 1)); //spinner dah bya5od starting defualt wl min and max and step fa ye5alena ne5tar mn arkam dol bas
        positionField = new JTextField();

        // Create department dropdown
        departmentBox = new JComboBox<>(); // added this

        // Create employee type dropdown
        employeeTypeBox = new JComboBox<>(
                new String[] {
                        "Full-Time",
                        "Part-Time"
                }
        );
        //
        employeeTypeBox.addActionListener(e -> updateSalaryFields());

        //The order in which we call add() is important because GridLayout fills cells in order.
        // Add labels and components to the form
        panel.add(new JLabel("Employee ID:"));
        panel.add(idField);

        panel.add(new JLabel("Full Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Age:"));
        panel.add(ageSpinner);

        panel.add(new JLabel("Department:"));
        panel.add(departmentBox);

        panel.add(new JLabel("Position:"));
        panel.add(positionField);

        panel.add(new JLabel("Employee Type:"));
        panel.add(employeeTypeBox);

        return panel;
    }

    public void reloadDepartments() { // added this
        departmentBox.removeAllItems(); // added this
        for (Department d : departmentManager.getAllDepartments()) { // added this
            departmentBox.addItem(d); // added this
        } // added this
    } // added this

    private JPanel createButtonPanel() {
        // flowlayout by7othom from left to right in a row
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        clearButton = new JButton("Clear Form");
        addButton = new JButton("Add Employee");

        //action listener deh bt5aly el button y respond when clicked in response to an event el hwa el user das 3la el button
        // e is event object representing el action of type ActionEvent bas java will infer automatically me4 lazem nektb el tyoe
        // -> lambda operator separates el parameter mn el action
        clearButton.addActionListener(e -> clearForm());
        addButton.addActionListener(e -> addEmployee());

        panel.add(clearButton);
        panel.add(addButton);

        return panel;
    }
    private JPanel createSalaryPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 12, 12));
        panel.setBorder(new TitledBorder("Salary Information"));

        return panel;
    }

    private void clearForm() {

        idField.setText("");
        nameField.setText("");
        ageSpinner.setValue(25);
        positionField.setText("");

        if (departmentBox.getItemCount() > 0) { // added this
            departmentBox.setSelectedIndex(0); //deh ll dropdown boxes set it tbos l awel index el default
        } // added this
        employeeTypeBox.setSelectedIndex(0);

        updateSalaryFields(); // Reset salary fields to default state

    }

    private void updateSalaryFields() {
        // Remove the previous salary components
        //34an lw knt katba 7agat 3la asas full time then changes mn el dropdown l part time
        salaryPanel.removeAll();

        // Check which employee type is selected
        if (employeeTypeBox.getSelectedItem().equals("Full-Time")) {

            basicSalaryField = new JTextField();
            bonusField = new JTextField();

            salaryPanel.add(new JLabel("Basic Salary:"));
            salaryPanel.add(basicSalaryField);

            salaryPanel.add(new JLabel("Bonus:"));
            salaryPanel.add(bonusField);

        } else {

            hourlyRateField = new JTextField();
            hoursWorkedField = new JTextField();

            salaryPanel.add(new JLabel("Hourly Rate:"));
            salaryPanel.add(hourlyRateField);

            salaryPanel.add(new JLabel("Hours Worked:"));
            salaryPanel.add(hoursWorkedField);
        }

        // Refresh the panel after changing its components
        salaryPanel.revalidate(); //bt2ool l swing en feh 7agat et8ayart fa recalculate el layout
        salaryPanel.repaint();// bt2ool l swing redraw el panel so the visual changes appear
    }
    private void addEmployee() {

        try {
            // Read common employee information
            //parse int hena deh bta5od el mn JTextField el hwa byb2a string w t7awelo int
            //w lw el 7aga me4 int ya3ny da5lt hello masalan hat3ml NumberFormatException hence why we are using el try and catch
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int age = (int) ageSpinner.getValue(); //getvalue returns object fa hn3mlha casting
            Object selectedDept = departmentBox.getSelectedItem(); // added this
            if (selectedDept == null) { // added this
                JOptionPane.showMessageDialog(this, "No department available.", "Input Error", JOptionPane.ERROR_MESSAGE); // added this
                return; // added this
            } // added this
            String department = selectedDept.toString(); // added this
            String position = positionField.getText();

            // Validate common information
            if (name.isEmpty() || position.isEmpty()) {
                // el method bta5ot parent and message el awal this refers ll current addemp obj wl message el 3ayzeen nezhrha and can be overriden tab3an
                //ennna ne3ml message title w fourth arg are constants (info msg aw error msg aw warning msg question msg or plain msg) 7asab ana 3izah azher eh  w bet7otlha icon
                // joptionpane deh class , showmessage deh static method wl info wl kalam dah predefined coonstants
                JOptionPane.showMessageDialog(this,"Name and position cannot be empty.","Input Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create the correct employee type
            Employee employee;

            if (employeeTypeBox.getSelectedItem().equals("Full-Time")) {

                double basicSalary = Double.parseDouble(
                        basicSalaryField.getText()
                );

                double bonus = Double.parseDouble(
                        bonusField.getText()
                );

                if (basicSalary < 0 || bonus < 0) {
                    JOptionPane.showMessageDialog(this,"Salary and bonus cannot be negative.","Input Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                employee = new FullTimeEmployee(id, name, age, department, position,basicSalary, bonus);

            } else {

                double hourlyRate = Double.parseDouble(
                        hourlyRateField.getText()
                );

                double hoursWorked = Double.parseDouble(
                        hoursWorkedField.getText()
                );

                if (hourlyRate < 0 || hoursWorked < 0) {
                    JOptionPane.showMessageDialog(this,"Hourly rate and hours worked cannot be negative.","Input Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                employee = new PartTimeEmployee(id, name, age, department, position,hourlyRate, hoursWorked);
            }

            if (!employeeManager.addEmployee(employee)) {
                JOptionPane.showMessageDialog(this,"An employee with this ID already exists.","Duplicate ID",JOptionPane.ERROR_MESSAGE);
                return;
            }
            //oveerriding it 34an ne5aly el display awda7
            JOptionPane.showMessageDialog(this,"Employee added successfully!\n" +"Employee Name: " + employee.getName() + "\n" +"Employee ID: " + employee.getId() + "\n" +"Total Salary: " + employee.calculateSalary(),"Success",JOptionPane.INFORMATION_MESSAGE);
            clearForm();//y clear ba3d ma added successfully

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,"Please enter valid numeric values in the ID and salary fields.","Input Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
