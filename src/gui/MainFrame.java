package gui;

import javax.swing.*;
import java.awt.*;

import manager.EmployeeManager;
import manager.DepartmentManager;
import manager.AuthenticationService;

public class MainFrame extends JFrame {

    private final EmployeeManager employeeManager;
    private final DepartmentManager departmentManager;

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final JPanel topBar;
    private static final int LOGIN_WIDTH = 500;
    private static final int LOGIN_HEIGHT = 100;

    private static final int MAIN_WIDTH = 1000;
    private static final int MAIN_HEIGHT = 650;

    private void applyWindowSize(int width, int height, boolean centerOnScreen) {
        setSize(width, height);
        if (centerOnScreen) {
            setLocationRelativeTo(null);
        }
        revalidate();
        repaint();
    }

    public MainFrame(EmployeeManager employeeManager, DepartmentManager departmentManager) {

        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager;

        // Connect the two managers
        departmentManager.setEmployeeManager(employeeManager);
        setTitle("Employee Management System");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Shared top bar with Back button for sub-screens
        topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setBorder(BorderFactory.createEmptyBorder(8, 12, 0, 12));

        JButton backButton = new JButton("← Back to Dashboard");
        backButton.addActionListener(e -> navigateToDashboard());
        topBar.add(backButton);
        topBar.setVisible(false);

        // CardLayout handles screen navigation
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);//mainPanel is the area inside the mainFrame where the different screens are displayed and switched between using the cardLayout
        
        // Add application screens(we define the screens we're going to naviagte between)
        //Each screen/panel is registered inside mainPanel with a name->show method takes main panel and the registered name of the panel to be shown as arguments  
        mainPanel.add(new LoginPanel(new AuthenticationService(), this), "LOGIN");
        mainPanel.add(new DashboardPanel(this), "DASHBOARD");
        mainPanel.add(new AddEmployeePanel(employeeManager), "ADD_EMPLOYEE");
        mainPanel.add(new ViewEmployeesPanel(employeeManager), "VIEW_EMPLOYEES");
        mainPanel.add(new DepartmentPanel(departmentManager), "DEPARTMENTS");

        add(topBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        navigateToLogin(); //login is the fisrt screen shown when the program starts
    }

    // Login screen
    // Login screen (smaller window)
    public void navigateToLogin() {
        topBar.setVisible(false);
        applyWindowSize(LOGIN_WIDTH, LOGIN_HEIGHT, true);
        cardLayout.show(mainPanel, "LOGIN");
        //LOGIN is the name assigned to the login panel when it was added to the mainPanel
        //main panel heya el passed to "show" method NOT loginPanel nafasaha
        //because CardLayout manages the entire collection of panels inside mainPanel
    }

    // Dashboard (larger window)
    public void navigateToDashboard() {
        topBar.setVisible(false);
        applyWindowSize(MAIN_WIDTH, MAIN_HEIGHT, true);
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    // Add employee screen
    public void navigateToAddEmployee() {
        topBar.setVisible(true);
        applyWindowSize(MAIN_WIDTH, MAIN_HEIGHT, false);
        cardLayout.show(mainPanel, "ADD_EMPLOYEE");
    }

    // View employees screen
    public void navigateToViewEmployees() {
        viewEmployeesPanel.refreshTable();
        topBar.setVisible(true);
        applyWindowSize(MAIN_WIDTH, MAIN_HEIGHT, false);
        cardLayout.show(mainPanel, "VIEW_EMPLOYEES");
    }
    
    // Department management screen
    public void navigateToDepartments() {
        topBar.setVisible(true);
        applyWindowSize(MAIN_WIDTH, MAIN_HEIGHT, false);
        cardLayout.show(mainPanel, "DEPARTMENTS");
    }
    // Save data and exit
    public void exitProgram() {

        try {
            employeeManager.saveEmployees();
            departmentManager.saveDepartments();

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving application data:\n" + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }

        System.exit(0);
    }
}
