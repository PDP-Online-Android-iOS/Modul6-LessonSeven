package dev.ogabek.kotlin.model

data class Employee(
    val employee_name: String,
    val employee_salary: String,
    private val employee_age: Int,
    val profile_image: String,
    val id: Int
) {

    fun getEmployee_age(): String {
        return employee_age.toString()
    }

}
