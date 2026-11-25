package gui;
import department.Department;

import manager.DepartmentManager;
import javax.swing.*;//create GUI components
import javax.swing.table.DefaultTableModel;//manage data displayed
import java.awt.*;//for layouts, font, and alignment


public class DepartmentPanel extends JPanel {
    private DepartmentManager departmentManager;

    // text fields where user enter Department ID and Name
    private JTextField idField;
    private JTextField nameField;
    private JTable departmentTable;//displays department in a table
    private DefaultTableModel tableModel;

    public DepartmentPanel(DepartmentManager departmentManager) {
        this.departmentManager = departmentManager;

        setLayout(new BorderLayout(15, 15));


        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add new Department"));
        //create empty text fields for department ID and name
        idField = new JTextField();
        nameField = new JTextField();

        //Add the ID and name labels and their text fields
        formPanel.add(new JLabel("Department ID"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Department Name"));
        formPanel.add(nameField);

        //Buttons creation
        JButton addButton = new JButton("Add Department");
        JButton deleteButton = new JButton("Delete Department");
        JButton clearButton = new JButton("Clear");
        JButton refreshButton = new JButton("Refresh");

        //putting the buttons add and clear in a panel to the right
        JPanel formButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        formButtonPanel.add(clearButton);
        formButtonPanel.add(addButton);

        JPanel formContainer = new JPanel(new BorderLayout());
        formContainer.add(formPanel, BorderLayout.CENTER);
        formContainer.add(formButtonPanel, BorderLayout.SOUTH);

        //Department table
        String[] columns = {"Department ID", "Department Name"};
        tableModel = new DefaultTableModel(columns, 0){//table intially has zero rows
            public boolean isCellEditable ( int row, int column){
                return false;
            }
        };
        departmentTable=new JTable(tableModel);
        departmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane=new JScrollPane(departmentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("All Departments"));

        //Table buttons
        JPanel tableButtonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );
        tableButtonPanel.add(refreshButton);
        tableButtonPanel.add(deleteButton);
        JPanel tableContainer=new JPanel(new BorderLayout(5,5));
        tableContainer.add(scrollPane,BorderLayout.CENTER);
        tableContainer.add(tableButtonPanel,BorderLayout.SOUTH);
        //Add components to the main panel
        centerPanel.add(formContainer,BorderLayout.NORTH);
        centerPanel.add(tableContainer,BorderLayout.CENTER);
        add(centerPanel,BorderLayout.CENTER);

        //Event Listeners

        addButton.addActionListener(e-> addDepartment());
        deleteButton.addActionListener(e->deleteDepartment());
        clearButton.addActionListener(e->clearForm());
        refreshButton.addActionListener(e->refreshTable());
        refreshTable();
    }

    private void addDepartment(){
        try{
            String idText=idField.getText().trim();
            String name=nameField.getText().trim();
            if(idText.isEmpty()||name.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int id=Integer.parseInt(idText);
            if(id<=0){
                JOptionPane.showMessageDialog(this, "Department ID must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE
                );
                return;

            }
            Department department=new Department(id,name);
            if(!departmentManager.addDepartment(department)){
                JOptionPane.showMessageDialog(this, "Department ID or name already exists.", "Duplicate Department", JOptionPane.ERROR_MESSAGE
                );
                return;

            }
            JOptionPane.showMessageDialog(this, "Department added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE
            );
            clearForm();
            refreshTable();
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Department ID must be a valid integer.", "Input Error", JOptionPane.ERROR_MESSAGE
            );

        }
        catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void deleteDepartment(){
        int selectedRow=departmentTable.getSelectedRow();
        if(selectedRow==-1) {//-1 means the user didn't select any rows
            JOptionPane.showMessageDialog(this,"please select a department first");
            return;
        }

        int modelRow=departmentTable.convertRowIndexToModel(selectedRow);
        int id=(int) tableModel.getValueAt(modelRow,0);//column 0 contains department ID
        int confirmation=JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this department?", "Confirm Delete",JOptionPane.YES_NO_OPTION);
        if(confirmation!=JOptionPane.YES_OPTION)
            return;
        boolean deleted=departmentManager.deleteDepartment(id);
        if(deleted){
            JOptionPane.showMessageDialog(this,"Department deleted successfully");
            refreshTable();
        }
        else{
            JOptionPane.showMessageDialog(this, "This department cannot be deleted because it does not exist " + "or employees are still assigned to it.", "Delete Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }


    public void refreshTable(){
        tableModel.setRowCount(0);
        for (Department department:departmentManager.getAllDepartments()){
            Object[] row={department.getId(),department.getName()};
            tableModel.addRow(row);
        }
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
    }
}
