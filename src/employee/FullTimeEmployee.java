package employee;

public class FullTimeEmployee extends Employee {
    private double basicSalary;
    private double bonus;

    public FullTimeEmployee(int id, String name, int age,String department, String position,double basicSalary, double bonus) {
        super(id, name, age, department, position);
        this.basicSalary = basicSalary;
        this.bonus = bonus;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getBonus() {
        return bonus;
    }

    @Override 
    public double calculateSalary() {
        return basicSalary + bonus;
    }

    @Override
    public String fileString(){
        return "FullTimeEmployee"+","+getId()+","+getName()+","+getAge()+","+getDepartment()+","+getPosition()+","+basicSalary+","+bonus+"\n";
        //id,name,age,department,position are private so their getters are used
    }
}
