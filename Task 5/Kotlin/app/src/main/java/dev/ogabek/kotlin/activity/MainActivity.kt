package dev.ogabek.kotlin.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import dev.ogabek.kotlin.R
import dev.ogabek.kotlin.adapter.Adapter
import dev.ogabek.kotlin.helper.Logger
import dev.ogabek.kotlin.model.Employee
import dev.ogabek.kotlin.model.Respond
import dev.ogabek.kotlin.model.RespondDelete
import dev.ogabek.kotlin.model.RespondUpdate
import dev.ogabek.kotlin.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var rv_main: RecyclerView
    private lateinit var loading: ProgressBar
    private lateinit var btn_create: FloatingActionButton
    private val employees = ArrayList<Employee>()

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        refreshAdapter(employees)
        get()
    }

    private fun initViews() {
        rv_main = findViewById(R.id.rv_main)
        loading = findViewById(R.id.loading)
        btn_create = findViewById(R.id.btn_create)
        rv_main.layoutManager = GridLayoutManager(this, 1)
        btn_create.setOnClickListener {
            val intent = Intent(this, EditAndCreateActivity::class.java)
            intent.putExtra("data", Employee())
            resultActivityForCreate.launch(intent)
        }

        rv_main.itemAnimator = DefaultItemAnimator()
        rv_main.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


    }

    fun dialogPoster(employee: Employee) {
        AlertDialog.Builder(this)
            .setTitle("Select Option")
            .setMessage("What do you want to do")
            .setPositiveButton(
                "Delete"
            ) { _, _ ->
                delete(employee)
            }
            .setNegativeButton("Edit") { _, _ ->
                val intent = Intent(this, EditAndCreateActivity::class.java)
                intent.putExtra("data", Employee(employee))
                resultActivityForEdit.launch(intent)
            }
            .setNeutralButton("Cancel", null)
            .setIcon(R.drawable.ic_dialog_alert)
            .show()
    }

    private fun refreshAdapter(employees: ArrayList<Employee>) {
        val adapter = Adapter(this, employees)
        rv_main.adapter = adapter
    }

    private var resultActivityForCreate = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val employee = data!!.getSerializableExtra("data") as Employee
            employee.id = employees.size
            post(employee)
        }
    }

    private var resultActivityForEdit = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val employee = data!!.getSerializableExtra("data") as Employee
            put(employee)
        }
    }

    private fun get() {
        loading.visibility = View.VISIBLE
        RetrofitHttp.employeeService.get().enqueue(object : Callback<Respond>{
            override fun onResponse(call: Call<Respond>, response: Response<Respond>) {
                loading.visibility = View.GONE
                Logger.d(TAG, response.toString())
                employees.clear()
                employees.addAll(response.body()!!.data)
                if (rv_main.adapter == null)
                    refreshAdapter(employees)
                else
                    rv_main.adapter!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Respond>, t: Throwable) {
                loading.visibility = View.VISIBLE
                Logger.e(TAG, t.localizedMessage)
            }
        })
    }

    private fun post(employee: Employee) {
        RetrofitHttp.employeeService.post(employee).enqueue(object : Callback<RespondUpdate>{
            override fun onResponse(call: Call<RespondUpdate>, response: Response<RespondUpdate>) {
                loading.visibility = View.GONE
                Logger.d(TAG, response.toString())
                get()
            }

            override fun onFailure(call: Call<RespondUpdate>, t: Throwable) {
                loading.visibility = View.VISIBLE
                Logger.e(TAG, t.localizedMessage)
            }
        })
    }

    private fun put(employee: Employee) {
        RetrofitHttp.employeeService.put(employee.id, employee).enqueue(object : Callback<RespondUpdate>{
            override fun onResponse(call: Call<RespondUpdate>, response: Response<RespondUpdate>) {
                loading.visibility = View.GONE
                Logger.d(TAG, response.toString())
                get()
            }

            override fun onFailure(call: Call<RespondUpdate>, t: Throwable) {
                loading.visibility = View.VISIBLE
                Logger.e(TAG, t.localizedMessage)
            }
        })
    }

    private fun delete(employee: Employee) {
        RetrofitHttp.employeeService.delete(employee.id).enqueue(object : Callback<RespondDelete>{
            override fun onResponse(call: Call<RespondDelete>, response: Response<RespondDelete>) {
                loading.visibility = View.GONE
                Logger.d(TAG, response.toString())
                get()
            }

            override fun onFailure(call: Call<RespondDelete>, t: Throwable) {
                loading.visibility = View.VISIBLE
                Logger.e(TAG, t.localizedMessage)
            }
        })
    }

}