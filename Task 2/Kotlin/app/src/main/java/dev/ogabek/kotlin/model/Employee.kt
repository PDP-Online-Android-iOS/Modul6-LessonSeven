package dev.ogabek.kotlin.model

import java.io.Serializable

class Employee: Serializable {

    fun getEmployee_age(): String {
        if (employee_age == 0) return ""
        return employee_age.toString()
    }

    var employee_name: String = ""
    var employee_salary: String = ""
    private var employee_age: Int = 0
    var profile_image: String = ""
    var id: Int = 0

    constructor()

    constructor(employee_name: String, employee_salary: String, employee_age: Int, profile_image: String, id: Int) {
        this.employee_name = employee_name
        this.employee_salary = employee_salary
        this.employee_age = employee_age
        this.profile_image = profile_image
        this.id = id
    }

    constructor(employee: Employee) {
        employee_name = employee.employee_name
        employee_salary = employee.employee_salary
        employee_age = employee.employee_age
        profile_image = employee.profile_image
        id = employee.id
    }

    override fun toString(): String {
        return "\n" +
                "Name: $employee_name " +
                "Salary: $employee_salary " +
                "Age: $employee_age " +
                "Image: $profile_image " +
                "Id: $id"
    }

}
