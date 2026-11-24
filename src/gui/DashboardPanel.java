package gui;
import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel{
    private MainFrame mainFrame; 
    public DashboardPanel(MainFrame mainFrame){
        this.mainFrame=mainFrame;
        setLayout(new BorderLayout(20,20));
        JLabel welcome=new JLabel("Welcome to Employee Management System", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial",Font.BOLD,26));
        welcome.setBorder(BorderFactory.createEmptyBorder(30,10,20,10));
        add(welcome,BorderLayout.NORTH);
        //Buttons
        JPanel buttonPanel=new JPanel(new GridLayout(2,2,20,20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30,80,30,80));
        JButton addEmployeeButton=new JButton("Add Employee");
        JButton viewEmployeesButton=new JButton("View Employees");
        JButton departmentsButton=new JButton("Manage Departments");
        JButton logoutButton=new JButton("Logout");
        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(viewEmployeesButton);
        buttonPanel.add(departmentsButton);
        buttonPanel.add(logoutButton);
        add(buttonPanel,BorderLayout.CENTER);

        // Event Listeners
        addEmployeeButton.addActionListener(e->mainFrame.navigateToAddEmployee());
        viewEmployeesButton.addActionListener(e->mainFrame.navigateToViewEmployees());
        departmentsButton.addActionListener(e->mainFrame.navigateToDepartments());
        logoutButton.addActionListener(e->mainFrame.navigateToLogin());


    }
    
}
