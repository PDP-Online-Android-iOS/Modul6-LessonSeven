package dev.ogabek.java.network;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dev.ogabek.java.MyApplication;
import dev.ogabek.java.model.Employee;

public class VolleyHttp {

    private static final String TAG = VolleyHttp.class.toString();

    public static final boolean IS_TESTER = true;
    public static final String SERVER_DEVELOPMENT = "http://dummy.restapiexample.com/api/v1/";
    public static final String SERVER_PRODUCTION = "https://62220b53666291106a1b35d3.mockapi.io/";

    public static String server(String url) {
        if (IS_TESTER) {
            return SERVER_DEVELOPMENT + url;
        } else {
            return SERVER_PRODUCTION + url;
        }
    }

    public static HashMap<String, String> header() {
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-type", "application/json; charset=UTF-8");
        return header;
    }

    // Request Methods

    public static void get(String api, HashMap<String, String> params, VolleyHandler volleyHandler) {
        StringRequest request = new StringRequest(
                Request.Method.GET, server(api),
                volleyHandler::onSuccess,
                error -> volleyHandler.onError(error.getMessage())
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        MyApplication.instance.addToRequestQueue(request);
    }

    public static void post(String api, HashMap<String, String> body, VolleyHandler volleyHandler) {
        StringRequest request = new StringRequest(
                Request.Method.POST, server(api),
                volleyHandler::onSuccess,
                error -> volleyHandler.onError(error.getMessage())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return header();
            }

            @Override
            public byte[] getBody() {
                return new JSONObject(body).toString().getBytes();
            }
        };
        MyApplication.instance.addToRequestQueue(request);
    }

    public static void put(String api, HashMap<String, String> body, VolleyHandler volleyHandler) {
        StringRequest request = new StringRequest(
                Request.Method.PUT, server(api),
                volleyHandler::onSuccess,
                error -> volleyHandler.onError(error.getMessage())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return header();
            }

            @Override
            public byte[] getBody() {
                return new JSONObject(body).toString().getBytes();
            }
        };
        MyApplication.instance.addToRequestQueue(request);
    }

    public static void delete(String api, VolleyHandler volleyHandler) {
        StringRequest request = new StringRequest(
                Request.Method.DELETE, server(api),
                volleyHandler::onSuccess,
                error -> volleyHandler.onError(error.getMessage())
        );
        MyApplication.instance.addToRequestQueue(request);
    }

    public static final String API_GET_ALL = "employees";
    public static final String API_GET_SINGLE = "employee/";
    public static final String API_PUT = "update/";
    public static final String API_DELETE = "delete/";
    public static final String API_POST = "create";

    // Params

    public static HashMap<String, String> paramsEmpty() {
        return new HashMap<>();
    }

    public static HashMap<String, String> paramsPost(Employee employee) {
        HashMap<String, String> body = new HashMap<>();
        body.put("employee_name", employee.getEmployee_name());
        body.put("employee_salary", employee.getEmployee_salary());
        body.put("employee_age", employee.getEmployee_age());
        body.put("profile_image", employee.getProfile_image());
        return body;
    }

    public static HashMap<String, String> paramsPut(Employee employee) {
        HashMap<String, String> body = new HashMap<>();
        body.put("employee_name", employee.getEmployee_name());
        body.put("employee_salary", employee.getEmployee_salary());
        body.put("employee_age", employee.getEmployee_age());
        body.put("profile_image", employee.getProfile_image());
        return body;
    }

}
