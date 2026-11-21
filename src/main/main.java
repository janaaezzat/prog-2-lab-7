package main;
import javax.swing.SwingUtilities;
import gui.MainFrame;
import manager.EmployeeManager;

public class Main {
public static void main(String[] args) {
    EmployeeManager employeeManager = new EmployeeManager(); // ba create el employee manager wa7ed w bn pass it to all gui classes
    SwingUtilities.invokeLater(() -> {  //SwingUtilities.invokeLater(...)  This tells Swing to create and show our GUI on
                                        //  the Swing Event Dispatch Thread (EDT), which is the thread Swing uses for GUI work.
                                        // ()->    This is a lambda expression it is a concise way to provide the task that Swing should execute.
        MainFrame frame = new MainFrame(employeeManager);
        frame.setVisible(true);// to make the window visible to the user
        });

    }
}