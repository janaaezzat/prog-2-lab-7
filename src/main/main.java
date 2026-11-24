package main;
import javax.swing.SwingUtilities;
import gui.MainFrame;
import manager.EmployeeManager;
import manager.DepartmentManager;

public class Main {
public static void main(String[] args) {
    EmployeeManager employeeManager = new EmployeeManager(); // ba create el employee manager wa7ed w bn pass it to all gui classes
    DepartmentManager departmentManager = new DepartmentManager(); 
    SwingUtilities.invokeLater(() -> {  //SwingUtilities.invokeLater(...)  This tells Swing to create and show our GUI on
                                        //  the Swing Event Dispatch Thread (EDT), which is the thread Swing uses for GUI work.
                                        // ()->    This is a lambda expression it is a concise way to provide the task that Swing should execute.
        MainFrame frame = new MainFrame(employeeManager, departmentManager);
        employeeManager.loadEmployees(); //load employees from file when the program starts
        departmentManager.loadDepartments(); //load departments from file when the program starts
        frame.setVisible(true);// to make the window visible to the user
        });

    }
}
