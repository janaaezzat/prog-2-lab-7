package manager;
import department.*;
import employee.Employee;
import filehandling.DepartmentFileHandler;

import java.util.ArrayList;


public class DepartmentManager{
    private ArrayList<Department> departments;
    private EmployeeManager employeeManager;
    public DepartmentManager(){
        departments=new ArrayList<>();
    }

    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }
    public boolean addDepartment(Department department){
        if(department==null)
            return false;
        if(findDepartmentByID(department.getId())!=null) {
            return false;
        }
        if(findDepartmentByName(department.getName())!=null) {
            return false;
        }
        departments.add(department);
        return true;
    }

    public ArrayList<Department> getAllDepartments() {
        return new ArrayList<>(departments);
    }
    public Department findDepartmentByID(int id){
        for(Department department:departments){
            if(department.getId()==id)
                return department;
        }
        return null;
    }
    public Department findDepartmentByName(String name){
        if(name==null)
            return null;
        for(Department department:departments){
            if (department.getName().equalsIgnoreCase(name.trim()))
                return department;
        }
        return null;
    }
    public boolean isExist(String name){
        return findDepartmentByName(name)!=null;
    }
    public boolean updateDepartment(Department updated){
        if(updated==null)
            return false;
        for(int i=0;i<departments.size();i++){
            Department old=departments.get(i);
            if(old.getId()==updated.getId()){
                Department existName=findDepartmentByName(updated.getName());
                if(existName!=null&&existName.getId()!= updated.getId())
                    return false;
                departments.set(i,updated);
                return true;
            }
        }
        return false;
    }
    
    public boolean isInUse(String departmentName){//check weather a department has employees or not
        if(employeeManager==null||departmentName==null)
            return false;
        for(Employee employee:employeeManager.getAllEmployees()){
            if(employee.getDepartment()!=null&&employee.getDepartment().equalsIgnoreCase(departmentName))
                return true;
        }
        return false;
    }
    
    public boolean deleteDepartment(int ID){
        Department department=findDepartmentByID(ID);
        if(department==null)
            return false;
        if(isInUse(department.getName()))
            return false; //cannot delete a department with employees in it
        departments.remove(department);
        return true;
    }

    void loadDepartments(){ //called by client once the program starts
        DepartmentFileHandler fileHandler = new DepartmentFileHandler();
        ArrayList<Department> loadedDepartments = fileHandler.load(); //load departments from file to list
        if(loadedDepartments!=null)
            departments=loadedDepartments;
    }
    public void saveDepartmentsToFile(){
        DepartmentFileHandler fileHandler=new DepartmentFileHandler();
        fileHandler.save(departments);
    }
}
