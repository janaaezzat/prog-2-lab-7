package employee;

public abstract class Employee {

    private int id;
    private String name;
    private int age;
    private String department;
    private String position;

    // Constructor
    public Employee(int id, String name, int age,String department, String position) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.position = position;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }
    public abstract double calculateSalary();
    public abstract String fileString();
    //each employee can return its string file format 7asab noo3o w so that the class can be open for extension
    //instead of handling different types of employees inside save and load methods
}