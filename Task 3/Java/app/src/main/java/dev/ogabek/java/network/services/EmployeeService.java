package dev.ogabek.java.network.services;

import java.util.List;

import dev.ogabek.java.model.Employee;
import retrofit2.Call;
import retrofit2.http.*;

public interface EmployeeService {

    @GET("m6-l7-t3")
    Call<List<Employee>> get();

    @PUT("m5-l7-t3/{id}")
    Call<Employee> put(@Path("id") int id, @Body Employee employee);

    @POST("m5-l7-t3")
    Call<Employee> post(@Body Employee employee);

    @DELETE("m5-l7-t3/{id}")
    Call<Employee> delete(@Path("id") int id);

}
