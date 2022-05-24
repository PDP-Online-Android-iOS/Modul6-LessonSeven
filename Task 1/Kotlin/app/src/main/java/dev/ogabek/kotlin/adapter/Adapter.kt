package dev.ogabek.kotlin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.ogabek.kotlin.R
import dev.ogabek.kotlin.activity.MainActivity
import dev.ogabek.kotlin.model.Employee

class Adapter(private val context: MainActivity, private val employees: List<Employee>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_poster_list, parent, false)
        return EmployeeViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val employee: Employee = employees[position]
        if (holder is EmployeeViewHolder) {
            holder.body.text = "${employee.employee_name}\t${employee.getEmployee_age()} years old"
            holder.title.text = employee.employee_salary
            holder.item.setOnLongClickListener {
                context.dialogPoster(employee)
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return employees.size
    }

    private class EmployeeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var body: TextView = view.findViewById(R.id.tv_body)
        var title: TextView = view.findViewById(R.id.tv_title)
        var item: LinearLayout = view.findViewById(R.id.ll_item)

    }

}