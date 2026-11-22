package manager;

public class AuthenticationService {
    //fixed credentials,need to be documented in readme zy ma talbeen fl pdf
    //should it be only one user wla aktar???
    private String validUsername="admin";
    private String validPassword="admin123";

    public boolean authenticateLogin(String username, String password) {
        return username.equals(validUsername)&&password.equals(validPassword);
    }
}