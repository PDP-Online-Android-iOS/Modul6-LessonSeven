package dev.ogabek.kotlin.network

import dev.ogabek.kotlin.model.Employee
import retrofit2.Call
import retrofit2.http.*

interface EmployeeService {

    @GET("m6-l7-t3")
    fun get(): Call<List<Employee>>

    @POST("m6-l7-t3")
    fun post(@Body employee: Employee): Call<Employee>

    @PUT("m6-l7-t3/{id}")
    fun put(@Path("id") id: Int, @Body employee: Employee): Call<Employee>

    @DELETE("m6-l7-t3/{id}")
    fun delete(@Path("id") id: Int): Call<Employee>

}