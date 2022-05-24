package dev.ogabek.java.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import dev.ogabek.java.R;
import dev.ogabek.java.adapter.Adapter;
import dev.ogabek.java.helper.Logger;
import dev.ogabek.java.model.Employee;
import dev.ogabek.java.network.VolleyHandler;
import dev.ogabek.java.network.VolleyHttp;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_main;
    ProgressBar loading;
    FloatingActionButton btn_create;

    private final ArrayList<Employee> employees = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        get();

    }

    private void initViews() {
        rv_main = findViewById(R.id.rv_main);
        loading = findViewById(R.id.loading);
        btn_create = findViewById(R.id.btn_create);

        rv_main.setLayoutManager(new LinearLayoutManager((this)));

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });
    }

    public void dialogPoster(Employee employee) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete Poster")
                .setMessage("Are you sure you want to delete this poster?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    delete(employee.getId());
                })
                .setNegativeButton("No", null)
                .setIcon(R.drawable.ic_dialog_alert)
                .show();
    }

    private void get() {
        loading.setVisibility(View.VISIBLE);
        VolleyHttp.get(VolleyHttp.API_GET_ALL, VolleyHttp.paramsEmpty(), new VolleyHandler() {
            @Override
            public void onSuccess(String respond) {
                loading.setVisibility(View.GONE);
                Logger.d("VolleyHttp", respond);
                Employee[] employeesList = new Gson().fromJson(respond, Employee[].class);

                employees.clear();
                employees.addAll(Arrays.asList(employeesList));

                refreshAdapter(employees);
            }

            @Override
            public void onError(String error) {
                Logger.d("VolleyHttp", error);
                loading.setVisibility(View.VISIBLE);
            }
        });
    }

    private void post() {
        Employee employee = new Employee("OgabekDev", "18000", 18, "", 1);
        loading.setVisibility(View.VISIBLE);
        VolleyHttp.post(VolleyHttp.API_POST, VolleyHttp.paramsPost(employee), new VolleyHandler() {
            @Override
            public void onSuccess(String respond) {
                loading.setVisibility(View.GONE);
                Logger.d("VolleyHttp", respond);

                get();

                refreshAdapter(employees);
            }

            @Override
            public void onError(String error) {
                Logger.d("VolleyHttp", error);
                loading.setVisibility(View.VISIBLE);
            }
        });
    }

    private void delete(int id) {
        loading.setVisibility(View.VISIBLE);
        VolleyHttp.delete(VolleyHttp.API_DELETE + id, new VolleyHandler() {
            @Override
            public void onSuccess(String respond) {
                loading.setVisibility(View.GONE);
                Logger.d("VolleyHttp", respond);

                get();

                refreshAdapter(employees);
            }

            @Override
            public void onError(String error) {
                Logger.d("VolleyHttp", error);
                loading.setVisibility(View.VISIBLE);
            }
        });
    }

    private void refreshAdapter(ArrayList<Employee> employees) {
        Adapter adapter = new Adapter(this, employees);
        rv_main.setAdapter(adapter);
    }

}