package gui;

import javax.swing.JFrame;
import manager.EmployeeManager;

public class MainFrame extends JFrame {

    public MainFrame(EmployeeManager employeeManager) {

        setTitle("Employee Management System");

        setSize(900, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //lma el user close the window or el frame el app (program) ends

        setLocationRelativeTo(null);// by5aly el frame yzhar fi el center of ur screen

        setContentPane(new AddEmployeePanel(employeeManager));// by7ded el panel ely hyzhr gowa el window frame

    }
}