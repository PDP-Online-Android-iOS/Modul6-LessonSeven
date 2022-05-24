package dev.ogabek.kotlin.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import dev.ogabek.kotlin.R
import dev.ogabek.kotlin.adapter.Adapter
import dev.ogabek.kotlin.helper.Logger
import dev.ogabek.kotlin.model.Employee
import dev.ogabek.kotlin.network.VolleyHandler
import dev.ogabek.kotlin.network.VolleyHttp
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var rv_main: RecyclerView
    private lateinit var loading: ProgressBar
    private lateinit var btn_create: FloatingActionButton
    private val employees = ArrayList<Employee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        get()
    }

    private fun initViews() {
        rv_main = findViewById(R.id.rv_main)
        loading = findViewById(R.id.loading)
        btn_create = findViewById(R.id.btn_create)
        rv_main.layoutManager = LinearLayoutManager(this)
        btn_create.setOnClickListener {
            post()
        }
    }

    fun dialogPoster(employee: Employee) {
        AlertDialog.Builder(this)
            .setTitle("Delete Poster")
            .setMessage("Are you sure you want to delete this poster?")
            .setPositiveButton(
                "Yes"
            ) { _, _ ->
                delete(employee.id)
            }
            .setNegativeButton("No", null)
            .setIcon(R.drawable.ic_dialog_alert)
            .show()
    }

    private fun get() {
        loading.visibility = View.VISIBLE
        VolleyHttp.get(VolleyHttp.API_GET_ALL, VolleyHttp.paramsEmpty(), object : VolleyHandler {
            override fun onSuccess(respond: String) {
                loading.visibility = View.GONE
                Logger.d("VolleyHttp", respond)
                val employeesList = Gson().fromJson(respond, Array<Employee>::class.java)
                employees.clear()
                employees.addAll(employeesList)
                refreshAdapter(employees)
            }

            override fun onError(error: String) {
                Logger.d("VolleyHttp", error)
                loading.visibility = View.VISIBLE
            }
        })
    }

    private fun post() {
        val employee = Employee("OgabekDev", "18000", 18, "", 1)
        loading.visibility = View.VISIBLE
        VolleyHttp.post(VolleyHttp.API_POST, VolleyHttp.paramsPost(employee), object : VolleyHandler {
            override fun onSuccess(respond: String) {
                    loading.visibility = View.GONE
                    Logger.d("VolleyHttp", respond)
                    get()
                    refreshAdapter(employees)
                }

            override fun onError(error: String) {
                    Logger.d("VolleyHttp", error)
                    loading.visibility = View.VISIBLE
                }
            })
    }

    private fun delete(id: Int) {
        loading.visibility = View.VISIBLE
        VolleyHttp.delete(VolleyHttp.API_DELETE + id, object : VolleyHandler {
            override fun onSuccess(respond: String) {
                loading.visibility = View.GONE
                Logger.d("VolleyHttp", respond)
                get()
                refreshAdapter(employees)
            }

            override fun onError(error: String) {
                Logger.d("VolleyHttp", error)
                loading.visibility = View.VISIBLE
            }
        })
    }

    private fun refreshAdapter(employees: ArrayList<Employee>) {
        val adapter = Adapter(this, employees)
        rv_main.adapter = adapter
    }
}