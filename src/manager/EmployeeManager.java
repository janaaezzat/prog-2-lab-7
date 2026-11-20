package manager;
import employee.*;
import java.util.ArrayList;

public class EmployeeManager {

    private ArrayList<Employee> employees;

    public EmployeeManager() {
        employees = new ArrayList<>();
    }

    //can accept all types of employees
   public boolean addEmployee(Employee employee) {

    if (findEmployeeById(employee.getId()) != null) {//protecting from duplicates
        return false;
    }

    employees.add(employee);
    return true;
}


    public ArrayList<Employee> getAllEmployees() {
        return new ArrayList<>(employees); //bn3ml copy gdeeda 34an man8yar4 fl asl ba3dein
    }

    
    public Employee findEmployeeById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }

        return null;
    }

    public int getEmployeeCount() {
        return employees.size();
    }
}
