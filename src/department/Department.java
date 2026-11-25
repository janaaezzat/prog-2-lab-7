package department;

public class Department {
    private int id;
    private String name;

    public Department(int id,String name){
        if(id<=0) {
            throw new IllegalArgumentException("Department ID must be positive"); // will stop the project if the ID is not positive
        }
        if(name==null||name.trim().isEmpty())
            throw new IllegalArgumentException("Department name cannot be empty");
        this.id=id;//m7tageen validations hena????
        this.name=name.trim();//3shan law eluser da5el esm eldepartment feh spaces yeshelha
        // bas law esm eldepartment fe space maben kelmeten zay "Human Resources" mesh hayshel elspace de
    }
    //getters
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String toString() {
        return name;
    }// 3shan yezhar 3la el system k string
    public String fileString() {
        return id+","+name+"\n"; //string to be written in text file
    }



    
}
