package dev.ogabek.java.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dev.ogabek.java.R;
import dev.ogabek.java.model.Employee;

public class EditAndCreateActivity extends AppCompatActivity {

    private EditText name;
    private EditText saley;
    private EditText age;
    private EditText image;

    private Button save;

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_create);

        initViews();
        getInfo();

    }

    private void getInfo() {
        Employee employee = (Employee) getIntent().getSerializableExtra("data");
        name.setText(employee.getEmployee_name());
        saley.setText(employee.getEmployee_salary());
        age.setText(employee.getEmployee_age());
        image.setText(employee.getProfile_image());
        id = employee.getId();
    }

    private void initViews() {
        name = findViewById(R.id.et_name);
        saley = findViewById(R.id.et_salary);
        age = findViewById(R.id.et_age);
        image = findViewById(R.id.et_image_url);

        save = findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (!name.getText().toString().equals("") && !saley.getText().toString().equals("") && !age.getText().toString().equals("") && !image.getText().toString().equals("")) {
                    Employee employee = new Employee(name.getText().toString(), saley.getText().toString(), Integer.parseInt(age.getText().toString()), image.getText().toString(), id);
                    intent.putExtra("data", employee);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}