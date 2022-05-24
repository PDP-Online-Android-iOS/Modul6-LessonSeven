package dev.ogabek.java.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.ogabek.java.R;
import dev.ogabek.java.adapter.Adapter;
import dev.ogabek.java.helper.Logger;
import dev.ogabek.java.model.Employee;
import dev.ogabek.java.model.Respond;
import dev.ogabek.java.model.RespondDelete;
import dev.ogabek.java.model.RespondUpdate;
import dev.ogabek.java.network.RetrofitHttp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private void get() {
        loading.setVisibility(View.VISIBLE);
        RetrofitHttp.employeeService.get().enqueue(new Callback<Respond>() {
            @Override
            public void onResponse(Call<Respond> call, Response<Respond> response) {
                loading.setVisibility(View.GONE);
                Logger.d(TAG, response.toString());
                employees.clear();
                employees.addAll(response.body().getEmployees());
                if (rv_main.getAdapter() == null)
                    refreshAdapter(employees);
                else
                    rv_main.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Respond> call, Throwable t) {
                loading.setVisibility(View.VISIBLE);
                Logger.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    private void post(Employee employee) {
        loading.setVisibility(View.VISIBLE);
        RetrofitHttp.employeeService.post(employee).enqueue(new Callback<RespondUpdate>() {
            @Override
            public void onResponse(Call<RespondUpdate> call, Response<RespondUpdate> response) {
                loading.setVisibility(View.GONE);
                Logger.d(TAG, response.toString());
                get();
            }

            @Override
            public void onFailure(Call<RespondUpdate> call, Throwable t) {
                loading.setVisibility(View.VISIBLE);
                Logger.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    private void put(Employee employee) {
        loading.setVisibility(View.VISIBLE);
        RetrofitHttp.employeeService.put(employee.getId(), employee).enqueue(new Callback<RespondUpdate>() {
            @Override
            public void onResponse(Call<RespondUpdate> call, Response<RespondUpdate> response) {
                loading.setVisibility(View.GONE);
                Logger.d(TAG, response.toString());
                get();
            }

            @Override
            public void onFailure(Call<RespondUpdate> call, Throwable t) {
                loading.setVisibility(View.VISIBLE);
                Logger.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    private void delete(Employee employee) {
        loading.setVisibility(View.VISIBLE);
        RetrofitHttp.employeeService.delete(employee.getId()).enqueue(new Callback<RespondDelete>() {
            @Override
            public void onResponse(Call<RespondDelete> call, Response<RespondDelete> response) {
                loading.setVisibility(View.GONE);
                Logger.d(TAG, response.toString());
                get();
            }

            @Override
            public void onFailure(Call<RespondDelete> call, Throwable t) {
                loading.setVisibility(View.VISIBLE);
                Logger.e(TAG, t.getLocalizedMessage());
            }
        });
    }

}