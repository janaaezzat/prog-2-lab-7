package main;
import javax.swing.SwingUtilities;
import gui.MainFrame;
import manager.EmployeeManager;
import manager.DepartmentManager;

public class Main {

    public static void main(String[] args) {

        EmployeeManager employeeManager=new EmployeeManager();
        DepartmentManager departmentManager=new DepartmentManager();

        // Load saved data before opening the application
        employeeManager.loadEmployees();
        departmentManager.loadDepartments();

        // Open GUI on Swing Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {

            MainFrame frame = new MainFrame(
                    employeeManager,
                    departmentManager
            );

            frame.setVisible(true);
        });
    }
}
