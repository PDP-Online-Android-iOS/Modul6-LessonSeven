package dev.ogabek.java.network;

import dev.ogabek.java.network.services.EmployeeService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHttp {

    public static final boolean IS_TESTER = false;
    private static final String SERVER_DEVELOPMENT = "http://dummy.restapiexample.com/api/v1/employees";
    private static final String SERVER_PRODUCTION = "https://62220b53666291106a1b35d3.mockapi.io/";

    public static String server() {
        if (IS_TESTER) {
            return SERVER_DEVELOPMENT;
        } else {
            return SERVER_PRODUCTION;
        }
    }

    public static Retrofit getRetrofit() {
        Retrofit builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(server())
                .build();
        return builder;
    }

    public static EmployeeService employeeService = getRetrofit().create(EmployeeService.class);

}
