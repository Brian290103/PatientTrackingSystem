package com.example.patienttrackingsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardDr extends AppCompatActivity {

    ConstraintLayout btn_new_appointments;
    ConstraintLayout btn_add_patient;
    ConstraintLayout btn_addPatientMedicalReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_dr);

        btn_new_appointments=findViewById(R.id.btn_makeAppointment);
        btn_new_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardDr.this,NewAppointments.class));
            }
        });

        btn_add_patient=findViewById(R.id.btn_approvePatient);
        btn_add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardDr.this);

                View view=getLayoutInflater().inflate(R.layout.activity_add_patient,null);

                builder.setTitle("Add New Patient")
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setView(view);

                AlertDialog dialog = builder.create();
                dialog.show();            }
        });

        btn_addPatientMedicalReport=findViewById(R.id.btnCheckNotifications);
        btn_addPatientMedicalReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardDr.this);

                View view=getLayoutInflater().inflate(R.layout.activity_add_patient_medical_report,null);

                builder.setTitle("Add Patient Medical Report")
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setView(view);

                AlertDialog dialog = builder.create();
                dialog.show();            }
        });

    }
}