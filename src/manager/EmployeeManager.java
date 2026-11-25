package manager;
import employee.*;
import filehandling.EmployeeFileHandler;
import java.util.ArrayList;

public class EmployeeManager {

    private ArrayList<Employee> employees; //polymorphism

    public EmployeeManager() {
        employees = new ArrayList<>();
        //creates an empty list when creating an employeeManager object
    }
    // add employee
    //can accept all types of employees
   public boolean addEmployee(Employee employee) {
      
    if (employee == null) {
            return false;
        }

    if (findEmployeeById(employee.getId()) != null) {//protecting from duplicates (Checks whether another employee already has the same ID)
        return false; 
    }

    employees.add(employee);
    return true;
}

// get all employees
//i will use in jtable
    public ArrayList<Employee> getAllEmployees() {
        return new ArrayList<>(employees); //bn3ml copy gdeeda 34an man8yar4 fl asl ba3dein (Returns a copy of the list instead of exposing the original list)
    }

 // find employee by id   
    public Employee findEmployeeById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }

        return null;
    }
// get employee count
    public int getEmployeeCount() {
        return employees.size();//Returns the number of employees currently stored
    }

// Update Employee (update employee 7a7ot other employee makano in same id)
    public boolean updateEmployee(Employee updatedEmployee) {

        if (updatedEmployee == null) {
            return false;
        }

        for (int i = 0; i < employees.size(); i++) {

            if (employees.get(i).getId() == updatedEmployee.getId()) {

                employees.set(i, updatedEmployee);//set(index, object) replaces the element at a particular position
                return true;
            }
        }

        return false;
    }

    // Delete Employee By ID
    public boolean deleteEmployeeById(int id) {

        for (int i = 0; i < employees.size(); i++) {

            if (employees.get(i).getId() == id) {

                employees.remove(i);//Remove the employee at that index
                return true;
            }
        }

        return false;//If no employee is found, the method returns false
    }

    // Filter Employees By Department
    //this method returns employees belonging to a particular department
    public ArrayList<Employee> filterEmployeesByDepartment(String department) {

        ArrayList<Employee> filteredEmployees = new ArrayList<>();//new empty list to store the matching employees
        
        if (department == null) {
       return filteredEmployees; //htrg3 list 
         }


        for (Employee employee : employees) {

            if (employee.getDepartment().equalsIgnoreCase(department)) {//Ignorecase so that IT, it, and It are treated as equal

                filteredEmployees.add(employee);
            }
        }

        return filteredEmployees;
    }

    public Employee findEmployeeByName(String name) {

    for (Employee employee : employees) {

        if (employee.getName().equalsIgnoreCase(name)) {
            return employee;
        }
    }

    return null;
    }

    public void saveEmployees() { //save the list of existing employees to text file throgh the method in EmployeeFileHandler class
        EmployeeFileHandler fileHandler = new EmployeeFileHandler();
        fileHandler.save(employees);
    }

    // Load employees (called by client once the program starts)
    public void loadEmployees() {
        EmployeeFileHandler fileHandler = new EmployeeFileHandler();
        employees = fileHandler.load();
        }



}



