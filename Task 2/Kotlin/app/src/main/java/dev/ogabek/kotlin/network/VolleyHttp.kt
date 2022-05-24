package dev.ogabek.kotlin.network

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import dev.ogabek.kotlin.MyApplication
import dev.ogabek.kotlin.model.Employee
import org.json.JSONObject

object VolleyHttp {
    private val TAG = VolleyHttp::class.java.toString()
    const val IS_TESTER = false
    private const val SERVER_DEVELOPMENT = "http://dummy.restapiexample.com/api/v1/employees"
    private const val SERVER_PRODUCTION = "https://62220b53666291106a1b35d3.mockapi.io/"
    fun server(url: String): String {
        return if (IS_TESTER) {
            SERVER_DEVELOPMENT + url
        } else {
            SERVER_PRODUCTION + url
        }
    }

    fun header(): HashMap<String, String> {
        val header = HashMap<String, String>()
        header["Content-type"] = "application/json; charset=UTF-8"
        return header
    }

    // Request Methods
    operator fun get(api: String, params: HashMap<String, String>, volleyHandler: VolleyHandler) {
        val request: StringRequest = object : StringRequest(
            Method.GET, server(api),
            volleyHandler::onSuccess,
            { error -> volleyHandler.onError(error.localizedMessage) }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return params
            }
        }
        MyApplication.instance!!.addToRequestQueue(request)
    }

    fun post(api: String, body: HashMap<String, String>, volleyHandler: VolleyHandler) {
        val request: StringRequest = object : StringRequest(
            Method.POST, server(api),
            volleyHandler::onSuccess,
            { error -> volleyHandler.onError(error.localizedMessage) }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return header();
            }

            override fun getBody(): ByteArray {
                return JSONObject(body as HashMap<*, *>).toString().toByteArray()
            }
        }
        MyApplication.instance!!.addToRequestQueue(request)
    }

    fun put(api: String, body: HashMap<String, String>, volleyHandler: VolleyHandler) {
        val request: StringRequest = object : StringRequest(
            Request.Method.PUT, server(api),
            volleyHandler::onSuccess,
            { error -> volleyHandler.onError(error.localizedMessage!!+ error.networkResponse) }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return header();
            }

            override fun getBody(): ByteArray {
                return JSONObject(body as HashMap<*, *>).toString().toByteArray()
            }
        }
        MyApplication.instance!!.addToRequestQueue(request)
    }

    fun delete(api: String, volleyHandler: VolleyHandler) {
        val request = StringRequest(
            Request.Method.DELETE, server(api),
            volleyHandler::onSuccess
        ) { error -> volleyHandler.onError(error.localizedMessage) }
        MyApplication.instance!!.addToRequestQueue(request)
    }

    const val API_GET_ALL = "m6-l7-t2"
    const val API_GET_SINGLE = "m6-l7-t2/"
    const val API_PUT = "m6-l7-t2/"
    const val API_DELETE = "m6-l7-t2/"
    const val API_POST = "m6-l7-t2"

    // Params
    fun paramsEmpty(): HashMap<String, String> {
        return HashMap()
    }

    fun paramsPost(employee: Employee): HashMap<String, String> {
        val body = HashMap<String, String>()
        body["employee_name"] = employee.employee_name
        body["employee_salary"] = employee.employee_salary
        body["employee_age"] = employee.getEmployee_age()
        body["profile_image"] = employee.profile_image
        return body
    }

    fun paramsPut(employee: Employee): HashMap<String, String> {
        val body = HashMap<String, String>()
        body["employee_name"] = employee.employee_name
        body["employee_salary"] = employee.employee_salary
        body["employee_age"] = employee.getEmployee_age()
        body["profile_image"] = employee.profile_image
        return body
    }
}