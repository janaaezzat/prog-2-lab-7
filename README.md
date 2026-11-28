# Employee Management System

## Group Members IDs:

- Member 1 :9876
- Member 2 :10055
- Member 3 :10459
- Member 4 :11165


## System Description

The Employee Management System is a Java desktop application built with Java Swing. It allows an administrator to log in and manage employees and departments through a graphical user interface.

The application supports:
- Adding, viewing, searching, updating, and deleting employees.
- Managing full-time and part-time employees, with salary calculated according to employee type.
- Filtering employees by department.
- Adding and deleting departments. A department cannot be deleted while employees are assigned to it.
- Saving employee and department records to text files and loading them when the application starts.


## Compile and Run
Open the project in a Java IDE (such as NetBeans or IntelliJ), then run `Main.java` inside the `main` package.

## Demonstration Login Credentials

The application currently uses fixed demonstration credentials:

- **Username:** `admin`
- **Password:** `admin123`

## Data Storage

Application data is stored locally in plain-text `.txt` files inside `src/filehandling/`:

- `src/filehandling/employees.txt` — employee records.
- `src/filehandling/departments.txt` — department records.

The files use comma-separated values (one record per line; no header row).

**Employee record format** (8 fields):

```text
EmployeeType,ID,Name,Age,Department,Position,PayField1,PayField2
```
- Full-time employee: `FullTimeEmployee,ID,Name,Age,Department,Position,BasicSalary,Bonus`
- Part-time employee: `PartTimeEmployee,ID,Name,Age,Department,Position,HourlyRate,HoursWorked`

**Department record format** (2 fields):

```text
ID,DepartmentName
```

The application loads these files at startup and writes changes back to them when employee or department records are added, updated, or deleted. If the department file is empty or missing, the application initializes the default departments: IT, Human Resources, Finance, Marketing, and Sales.
