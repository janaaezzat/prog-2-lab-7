package gui;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Employee Management System");

        setSize(900, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setContentPane(new AddEmployeePanel());

    }
}