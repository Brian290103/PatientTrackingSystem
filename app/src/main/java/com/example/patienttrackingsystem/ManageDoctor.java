package com.example.patienttrackingsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class ManageDoctor extends AppCompatActivity {
    //ConstraintLayout btnAddDoctor;

    AlertDialog alertDialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctor);

        findViewById(R.id.btn_addDoctor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_add_doctor, null);

                builder=new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Add Doctor")
                        .setCancelable(true)
                        .setView(view);

                alertDialog=builder.create();
                alertDialog.show();

            }
        });
    }
}