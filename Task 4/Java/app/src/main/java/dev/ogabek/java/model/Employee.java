package dev.ogabek.java.model;

import java.io.Serializable;

public class Employee implements Serializable {

    private String employee_name;
    private String employee_salary;
    private int employee_age;
    private String profile_image;
    private int id;

    public Employee(String employee_name, String employee_salary, int employee_age, String profile_image, int id) {
        this.employee_name = employee_name;
        this.employee_salary = employee_salary;
        this.employee_age = employee_age;
        this.profile_image = profile_image;
        this.id = id;
    }

    public Employee() { }

    public Employee(Employee employee) {
        this.employee_name = employee.employee_name;
        this.employee_salary = employee.employee_salary;
        this.employee_age = employee.employee_age;
        this.profile_image = employee.profile_image;
        this.id = employee.id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public String getEmployee_salary() {
        return employee_salary;
    }

    public String getEmployee_age() {
        return String.valueOf(employee_age);
    }

    public String getProfile_image() {
        return profile_image;
    }

    public int getId() {
        return id;
    }
}
