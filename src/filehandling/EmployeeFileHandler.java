package filehandling;
import java.util.ArrayList;
import java.util.List;
import employee.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class EmployeeFileHandler {
    public void save(ArrayList<Employee> employees){
         Path path = Path.of("employees.txt"); //path is an object implementing "Path" interface,it is used to store file location
         //full-time employee format in file: Type,ID,Name,Age,Department,Position,BasicSalary,Bonus
         //part-time emolyee format in file: Type,ID,Name,Age,Department,Position,HourlyRate,HoursWorked
         StringBuilder employeeData=new StringBuilder();
         for(Employee emp:employees){
            employeeData.append(emp.fileString());
            }
        try{ //it is required to handle file errors without terminating the program so i used exceptions
            Files.writeString(path,employeeData.toString()); //convert stringBuilder dt to String dt
        }
        catch(IOException e){
            System.out.println("Error writing to file: "+e.getMessage());
        }
    }


    public ArrayList<Employee> load(){   
    Path path = Path.of("employees.txt");
    ArrayList<Employee> employees = new ArrayList<>(); //empty initially

    try{
        
    if (!Files.exists(path)||Files.size(path)==0) { 
        return employees; //if file is empty or doesn't exist,an empty array is returned
    }
    List<String> lines = Files.readAllLines(path); //list of all lines in the file
    for(String l:lines){
        if (l.equals("")) {
            continue;
            }
        String[] word=l.split(","); //split the comma-separated line into words and store them in an array of strings
        //reconstruct the objects 7asab their type w their fields that are stored in file
        if (word.length!=8){  //if one of the fields is missing
            continue;
            }
        if(word[0].equals("FullTimeEmployee")){ 
            employees.add(new FullTimeEmployee(Integer.parseInt(word[1]),word[2],Integer.parseInt(word[3]),word[4],word[5],Double.parseDouble(word[6]),Double.parseDouble(word[7])));
            //words are stored as strings so we some fields need to be converted to int or double

        }
        else if(word[0].equals("PartTimeEmployee")){
            employees.add(new PartTimeEmployee(Integer.parseInt(word[1]),word[2],Integer.parseInt(word[3]),word[4],word[5],Double.parseDouble(word[6]),Integer.parseInt(word[7])));

        }

    }}
    catch(IOException e){
        System.out.println("Error reading from file: "+e.getMessage());
    }
   


    return employees;
}    
    
}
