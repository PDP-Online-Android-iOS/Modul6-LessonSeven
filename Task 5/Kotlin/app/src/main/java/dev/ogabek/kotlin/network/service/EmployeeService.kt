package dev.ogabek.kotlin.network.service

import dev.ogabek.kotlin.model.Employee
import dev.ogabek.kotlin.model.Respond
import dev.ogabek.kotlin.model.RespondDelete
import dev.ogabek.kotlin.model.RespondUpdate
import retrofit2.Call
import retrofit2.http.*

interface EmployeeService {

    @GET("employees")
    fun get(): Call<Respond>

    @POST("create")
    fun post(@Body employee: Employee): Call<RespondUpdate>

    @PUT("update/{id}")
    fun put(@Path("id") id: Int, @Body employee: Employee): Call<RespondUpdate>

    @DELETE("delete/{id}")
    fun delete(@Path("id") id: Int): Call<RespondDelete>

}