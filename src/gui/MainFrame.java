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

    private final AddEmployeePanel addEmployeePanel;
    private final ViewEmployeesPanel viewEmployeesPanel;
    private final DepartmentPanel departmentPanel;

    private static final int LOGIN_WIDTH = 480;
    private static final int LOGIN_HEIGHT = 320;

    private static final int MAIN_WIDTH = 1000;
    private static final int MAIN_HEIGHT = 650;

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
        mainPanel = new JPanel(cardLayout);

        // Initialize application screens and keep references for refreshing
        LoginPanel loginPanel = new LoginPanel(new AuthenticationService(), this);
        DashboardPanel dashboardPanel = new DashboardPanel(this);
        addEmployeePanel = new AddEmployeePanel(employeeManager,departmentManager);
        viewEmployeesPanel = new ViewEmployeesPanel(employeeManager,departmentManager);
        departmentPanel = new DepartmentPanel(departmentManager);

        // Add application screens
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(dashboardPanel, "DASHBOARD");
        mainPanel.add(addEmployeePanel, "ADD_EMPLOYEE");
        mainPanel.add(viewEmployeesPanel, "VIEW_EMPLOYEES");
        mainPanel.add(departmentPanel, "DEPARTMENTS");

        add(topBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        navigateToLogin();
    }

    private void applyWindowSize(int width, int height, boolean centerOnScreen) {
        setSize(width, height);
        if (centerOnScreen) {
            setLocationRelativeTo(null);
        }
        revalidate();
        repaint();
    }

    // Login screen (smaller window)
    public void navigateToLogin() {
        topBar.setVisible(false);
        applyWindowSize(LOGIN_WIDTH, LOGIN_HEIGHT, true);
        cardLayout.show(mainPanel, "LOGIN");
    }

    // Dashboard (larger window)
    public void navigateToDashboard() {
        topBar.setVisible(false);
        applyWindowSize(MAIN_WIDTH, MAIN_HEIGHT, true);
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    // Add employee screen (refreshes departments dropdown before showing)
    public void navigateToAddEmployee() {//updated
        addEmployeePanel.reloadDepartments();
        topBar.setVisible(true);
        applyWindowSize(MAIN_WIDTH, MAIN_HEIGHT, false);
        cardLayout.show(mainPanel, "ADD_EMPLOYEE");
    }

    public void navigateToViewEmployees() {//updated
        viewEmployeesPanel.reloadDepartments();
        viewEmployeesPanel.refreshTable();
        topBar.setVisible(true);
        applyWindowSize(MAIN_WIDTH, MAIN_HEIGHT, false);
        cardLayout.show(mainPanel, "VIEW_EMPLOYEES");
    }
    // Department management screen (refreshes departments table before showing)
    public void navigateToDepartments() {
        departmentPanel.refreshTable();
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
            JOptionPane.showMessageDialog(
                    this,
                    "An error occurred while saving application data:\n"
                            + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        System.exit(0);
    }
}
