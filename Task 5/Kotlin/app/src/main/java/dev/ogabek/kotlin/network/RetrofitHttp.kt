package dev.ogabek.kotlin.network

import dev.ogabek.kotlin.network.service.EmployeeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHttp {

    companion object {
        const val IS_TESTER = true

        private const val SERVER_DEVELOPMENT = "http://dummy.restapiexample.com/api/v1/"
        private const val SERVER_PRODUCTION = "https://62220b53666291106a1b35d3.mockapi.io/"

        private fun server(): String {
            return if (IS_TESTER) {
                SERVER_DEVELOPMENT
            } else {
                SERVER_PRODUCTION
            }
        }

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(server())
                .build()
        }

        val employeeService: EmployeeService = getRetrofit().create(EmployeeService::class.java)

    }

}