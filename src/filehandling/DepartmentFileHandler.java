package filehandling;
import java.util.ArrayList;
import java.util.List;
import department.Department;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class DepartmentFileHandler{ 
    public void save(ArrayList<Department> departments){
        Path path = Path.of("src","filehandling", "departments.txt"); //path is an object implementing "Path" interface,it is used to store file location
         //department format in text file: id,name
         StringBuilder departmentData=new StringBuilder();
         for(Department dep:departments){
            departmentData.append(dep.fileString());
            }
        try{ //it is required to handle file errors without terminating the program so i used exceptions
            Files.writeString(path,departmentData.toString()); //convert stringBuilder dt to String dt
        }
        catch(IOException e){
            System.out.println("Error writing to file: "+e.getMessage());
        }
        }

    public ArrayList<Department> load(){
        Path path = Path.of("src","filehandling", "departments.txt");
        ArrayList<Department> departments = new ArrayList<>(); //empty initially

        try{
            if (!Files.exists(path)||Files.size(path)==0) { 
            return departments; //if file is empty or doesn't exist,an empty array is returned
            }
        List<String> lines = Files.readAllLines(path); //list of all lines in the file
        for(String l:lines){
            if (l.equals("")){ //skip empty lines
                continue;
            }
        String[] word=l.split(","); //split the comma-separated line into words and store them in an array of strings
        //reconstruct the objects 7asab their type w their fields that are stored in file
        if (word.length!=2){  //if one of the fields is missing
            continue;
            }
        departments.add(new Department(Integer.parseInt(word[0]),word[1])); //words are stored as strings so id should be converted to int first
        }
        }
        catch(IOException e){
            System.out.println("Error reading from file: "+e.getMessage());
        }
   
    return departments;
} 
}

    

