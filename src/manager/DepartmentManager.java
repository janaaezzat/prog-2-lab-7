package manager;
import department.*;
import filehandling.DepartmentFileHandler;
import java.util.ArrayList;

public class DepartmentManager{
    private ArrayList<Department> departments;











    
    
    void loadDepartments(){ //called by client once the program starts
        DepartmentFileHandler fileHandler = new DepartmentFileHandler();
        departments = fileHandler.load(); //load departments from file to list
    }
    
}
