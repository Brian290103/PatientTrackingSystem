package com.example.patienttrackingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDoctor extends AppCompatActivity {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://android-patient-tracker-bf263-default-rtdb.firebaseio.com/");
    TextInputEditText txtEmail, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        txtEmail = findViewById(R.id.txt_email);
        txtPass = findViewById(R.id.txtPass);

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddDoctor.this, "Clicked", Toast.LENGTH_SHORT).show();
                
                String email=txtEmail.getText().toString();
                String pass=txtPass.getText().toString();
            }
        });
    }
}