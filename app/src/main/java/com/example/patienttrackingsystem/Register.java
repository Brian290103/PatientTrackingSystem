package com.example.patienttrackingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class Register extends AppCompatActivity {


    FloatingActionButton floatingActionButton;
    TextView login_page;
    TextInputLayout gender_layout;
    AutoCompleteTextView gender_dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login_page=findViewById(R.id.loginTxt);
        login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
                finish();
            }
        });

        floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
                finish();
            }
        });

        gender_layout=findViewById(R.id.gender_layout);
        gender_dropdown=findViewById(R.id.gender_dropdown);

        String [] gender_list={"Male","Female"};
        ArrayAdapter<String>gender_adapter=new ArrayAdapter<>(Register.this,R.layout.gender_list,gender_list);
        gender_dropdown.setAdapter(gender_adapter);
    }
}