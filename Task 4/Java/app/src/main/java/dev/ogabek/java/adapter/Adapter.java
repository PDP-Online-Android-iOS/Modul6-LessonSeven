package dev.ogabek.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.ogabek.java.R;
import dev.ogabek.java.activity.MainActivity;
import dev.ogabek.java.model.Employee;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final MainActivity context;
    private final List<Employee> employees;

    public Adapter(MainActivity context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster_list, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Employee employee = employees.get(position);

        if (holder instanceof EmployeeViewHolder) {
            ((EmployeeViewHolder) holder).body.setText(employee.getEmployee_name() + "\t" + employee.getEmployee_age() + " years old");
            ((EmployeeViewHolder) holder).title.setText(employee.getEmployee_salary());

            ((EmployeeViewHolder) holder).item.setOnLongClickListener(view -> {
                context.dialogPoster(employee);
                return false;
            });
        }
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    private static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView body, title;
        LinearLayout item;

        public EmployeeViewHolder(View view) {
            super(view);

            body = view.findViewById(R.id.tv_body);
            title = view.findViewById(R.id.tv_title);

            item = view.findViewById(R.id.ll_item);

        }
    }
}
