package dev.ogabek.kotlin.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import dev.ogabek.kotlin.R
import dev.ogabek.kotlin.model.Employee

class EditAndCreateActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var saley: EditText
    private lateinit var age: EditText
    private lateinit var image: EditText

    private lateinit var save: Button

    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_and_create)

        initViews()
        getInfo()

    }

    private fun getInfo() {
        val employee: Employee = intent.getSerializableExtra("data") as Employee
        name.setText(employee.employee_name)
        saley.setText(employee.employee_salary)
        age.setText(employee.getEmployee_age())
        image.setText(employee.profile_image)
        id = employee.id
    }

    private fun initViews() {
        name = findViewById(R.id.et_name)
        saley = findViewById(R.id.et_salary)
        age = findViewById(R.id.et_age)
        image = findViewById(R.id.et_image_url)

        save = findViewById(R.id.btn_save)

        save.setOnClickListener {
            val intent = Intent()
            if (name.text.toString().isNotEmpty() && saley.text.toString().isNotEmpty() && age.text.toString().isNotEmpty() && image.text.toString().isNotEmpty()) {
                val employee = Employee(name.text.toString(), saley.text.toString(), age.text.toString().toInt(), image.text.toString(), id)
                intent.putExtra("data", employee)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

    }
}