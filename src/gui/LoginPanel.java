package gui;
import javax.swing.*;
import java.awt.*;
import manager.AuthenticationService;

public class LoginPanel extends JPanel{
    //reference to the authentication service obj(main frame howa el 7ay instantiate el authenticationService obj then passes it to loginpanel constructor)
    //to acheive srp,we separate gui class(loginpanel) from the authentication logic class(authenticationservice)
    private AuthenticationService authenticationService;
    private MainFrame mainFrame; //reference to the main frame obj so that we can call its "navigateToDashboard" method if login was successfull

    //gui components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;

    public LoginPanel(AuthenticationService authenticationService,MainFrame mainFrame) {
        this.authenticationService = authenticationService;
        this.mainFrame = mainFrame;

        //instantiate gui components that will be added to the panel
        JLabel usernameLabel = new JLabel("Username:"); //local variable bec i won't need it outside the constructor tany
        usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");//local variable
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");

        //grid layout arranges the panel into 3 rows and 2 columns to add the gui components to
        setLayout(new GridLayout(3, 2)); 

        //add the gui components to the panel
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(cancelButton);

        //add action listeners to the 2 buttons(calls the requiredbackend logic when a button is pressed)
        loginButton.addActionListener(e->{

        String username=usernameField.getText(); //read the username from input field
        String password = new String(passwordField.getPassword()); //read password,bas we convert it to string bec getPassword() returns a char array not a string

        if (authenticationService.authenticateLogin(username,password)) {
            mainFrame.navigateToDashboard(); //navigating to dashboard is the main frame's responsibility
        }
        else {
            JOptionPane.showMessageDialog(this,"Invalid username or password,please try again!"); //failed login,remain in login panel and clear the input fields to let user input again
            passwordField.setText("");
            usernameField.setText("");
        }
    });

    cancelButton.addActionListener(e->{mainFrame.exitProgram();}); //cancel button exits the program

    }



    
}
