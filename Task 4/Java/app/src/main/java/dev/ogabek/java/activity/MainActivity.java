package dev.ogabek.java.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import dev.ogabek.java.model.Respond;
import dev.ogabek.java.network.VolleyHandler;
import dev.ogabek.java.network.VolleyHttp;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();

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

        btn_create.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditAndCreateActivity.class);
            intent.putExtra("data", new Employee());
            resultActivityForCreate.launch(intent);
        });
    }

    public void dialogPoster(Employee employee) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Select Option")
                .setMessage("What do you want to do")
                .setPositiveButton("Delete", (dialogInterface, i) -> {
                    delete(employee);
                })
                .setNegativeButton("Edit", ((dialogInterface, i) -> {
                    Intent intent = new Intent(MainActivity.this, EditAndCreateActivity.class);
                    intent.putExtra("data", employee);
                    resultActivityForEdit.launch(intent);
                }))
                .setNeutralButton("Cancel", null)
                .setIcon(R.drawable.ic_dialog_alert)
                .show();
    }

    private void get() {
        loading.setVisibility(View.VISIBLE);
        VolleyHttp.get(VolleyHttp.API_GET_ALL, VolleyHttp.paramsEmpty(), new VolleyHandler() {
            @Override
            public void onSuccess(String respond) {
                loading.setVisibility(View.GONE);
                Logger.d(TAG, respond);
                Respond result = new Gson().fromJson(respond, Respond.class);
                Employee[] employeesList = result.getData();
                employees.clear();
                employees.addAll(Arrays.asList(employeesList));
                refreshAdapter(employees);
            }

            @Override
            public void onError(String error) {
                Logger.d(TAG, error);
                loading.setVisibility(View.VISIBLE);
            }
        });
    }

    private void post(Employee employee) {
        loading.setVisibility(View.VISIBLE);
        VolleyHttp.post(VolleyHttp.API_POST, VolleyHttp.paramsPost(employee), new VolleyHandler() {
            @Override
            public void onSuccess(String respond) {
                loading.setVisibility(View.GONE);
                Logger.d(TAG, respond);
                get();
            }

            @Override
            public void onError(String error) {
                Logger.d(TAG, error);
                loading.setVisibility(View.VISIBLE);
            }
        });
    }

    private void delete(Employee employee) {
        loading.setVisibility(View.VISIBLE);
        VolleyHttp.delete(VolleyHttp.API_DELETE + employee.getId(), new VolleyHandler() {
            @Override
            public void onSuccess(String respond) {
                loading.setVisibility(View.GONE);
                Logger.d(TAG, respond);
                get();
            }

            @Override
            public void onError(String error) {
                Logger.d(TAG, error);
                loading.setVisibility(View.VISIBLE);
            }
        });
    }

    private void put(Employee employee) {
        loading.setVisibility(View.VISIBLE);
        VolleyHttp.put(VolleyHttp.API_PUT + employee.getId(), VolleyHttp.paramsPut(employee), new VolleyHandler() {
            @Override
            public void onSuccess(String respond) {
                loading.setVisibility(View.GONE);
                Logger.d(TAG, respond);
                get();
            }

            @Override
            public void onError(String error) {
                Logger.d(TAG, error);
                loading.setVisibility(View.VISIBLE);
            }
        });
    }

    private void refreshAdapter(ArrayList<Employee> employees) {
        Adapter adapter = new Adapter(this, employees);
        rv_main.setAdapter(adapter);
    }

    private final ActivityResultLauncher resultActivityForCreate = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            Employee employee = (Employee) data.getSerializableExtra("data");
            post(employee);
        }
    });

    private final ActivityResultLauncher resultActivityForEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            Employee employee = (Employee) data.getSerializableExtra("data");
            put(employee);
        }
    });

}