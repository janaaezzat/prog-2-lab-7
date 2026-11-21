package employee;

public class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private double hoursWorked;

    public PartTimeEmployee(int id, String name, int age,String department, String position,double hourlyRate, double hoursWorked) {
        super(id, name, age, department, position);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    @Override 
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public String fileString(){
        return "PartTimeEmployee"+","+getId()+","+getName()+","+getAge()+","+getDepartment()+","+getPosition()+","+hourlyRate+","+hoursWorked+"\n";
        //id,name,age,department,position are private so their getters are used
    }
}
