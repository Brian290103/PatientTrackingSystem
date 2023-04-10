package com.example.patienttrackingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class loginStaff extends AppCompatActivity {

    Button btn_loginStaff;
    TextView btn_patientLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_staff);

        btn_loginStaff = findViewById(R.id.btn_loginStaff);
        btn_loginStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginStaff.this, DashboardDr.class));
            }
        });

        btn_patientLogin = findViewById(R.id.btn_patientLogin);
        btn_patientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}