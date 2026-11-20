package gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class AddEmployeePanel extends JPanel {
    // el 7agat deh private 34an el class dah bas el yet7akem feha 
    // Employee information fields
    private JTextField idField; //hena declared as class variables w bnrga3 ta7t ne3mlohom
                                //initialize fields in the constructor or methods, so they can be accessed throughout the class.
                                //34an han7taghom fl methods tanyeen zay el action listeners w el validation and buttons w keda
    private JTextField nameField;
    private JTextField ageField;
    private JTextField positionField;

    // Dropdowns
    private JComboBox<String> departmentBox;
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
    // the constructor 
    public AddEmployeePanel() {
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
        ageField = new JTextField();
        positionField = new JTextField();

        // Create department dropdown
        departmentBox = new JComboBox<>(
                new String[] {
                        "IT",
                        "Human Resources",
                        "Finance",
                        "Marketing",
                        "Sales"
                }
        );

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
        panel.add(ageField);

        panel.add(new JLabel("Department:"));
        panel.add(departmentBox);

        panel.add(new JLabel("Position:"));
        panel.add(positionField);

        panel.add(new JLabel("Employee Type:"));
        panel.add(employeeTypeBox);

        return panel;
    }

    private JPanel createButtonPanel() {
        // flowlayout by7othom from left to right in a row
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        clearButton = new JButton("Clear Form");
        addButton = new JButton("Add Employee");

        //action listener deh bt5aly el button y respond when clicked in response to an event el hwa el user das 3la el button
        // e is event object representing el action of type ActionEvent bas java will infer automatically me4 lazem nektb el tyoe
        // -> lambda operator separates el parameter mn el action
        clearButton.addActionListener(e -> clearForm());

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
    ageField.setText("");
    positionField.setText("");

    departmentBox.setSelectedIndex(0); //deh ll dropdown boxes set it tbos l awel index el default 
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
}