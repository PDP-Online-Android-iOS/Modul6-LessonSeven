package dev.ogabek.java.network.services;

import dev.ogabek.java.model.Employee;
import dev.ogabek.java.model.Respond;
import dev.ogabek.java.model.RespondDelete;
import dev.ogabek.java.model.RespondUpdate;
import retrofit2.Call;
import retrofit2.http.*;

public interface EmployeeService {

    @GET("employees")
    Call<Respond> get();

    @PUT("update/{id}")
    Call<RespondUpdate> put(@Path("id") int id, @Body Employee employee);

    @POST("create")
    Call<RespondUpdate> post(@Body Employee employee);

    @DELETE("delete/{id}")
    Call<RespondDelete> delete(@Path("id") int id);

}
