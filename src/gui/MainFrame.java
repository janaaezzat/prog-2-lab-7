package gui;

import javax.swing.JFrame;
import manager.EmployeeManager;
impoort manager.DepartmentManager;

public class MainFrame extends JFrame {

    public MainFrame(EmployeeManager employeeManager, DepartmentManager departmentManager) {//MainFrame communicates with backend through employeeManager object

        setTitle("Employee Management System");

        setSize(900, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //lma el user close the window or el frame el app (program) ends

        setLocationRelativeTo(null);// by5aly el frame yzhar fi el center of ur screen

        setContentPane(new AddEmployeePanel(employeeManager));// by7ded el panel ely hyzhr gowa el window frame
        }
        void navigateToLogin() { //client should call this awel ma el program ybda2?
            setContentPane(new LoginPanel(new AuthenticationService(), this)); 
        }
        void navigateToDashboard() {
            setContentPane(new DashboardPanel(this)); //msh 3arfa eh el arguments of its consructor bas m7tageen mainFrame reference mabda2eyan 3shn el dashboard ye communicate with mainFrame through it
        }
        void exitProgram(){
            //save employee and department data before exiting the program
            employeeManager.saveEmployees(); 
            departmentManager.saveDepartments();
            System.exit(0); 
        }
}