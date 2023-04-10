package com.example.patienttrackingsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewAppointments extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<AppointmentsModel> appointmentsModels;
    AppointmentsAdapter appointmentsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointments);

        findViewById(R.id.btn_back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(NewAppointments.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recycler_view);
        appointmentsModels = new ArrayList<>();
        retrieveData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void retrieveData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_NEW_APPOINTMENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);

                            if (!jsonObject.getBoolean("error")) {
                                JSONArray array = jsonObject.getJSONArray("result");

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject result = array.getJSONObject(i);

                                  /*  int id = result.getInt("id");
                                    int user_id = result.getInt("user_id");
                                    String user_name = result.getString("user_name");
                                    int doctor_id = result.getInt("doctor_id");
                                    int speciality_id = result.getInt("speciality_id");
                                    String message = result.getString("message");
                                    String app_date = result.getString("app_date");
                                    String app_time = result.getString("app_time");

                                    boolean isPaid = false;
                                    boolean isApproved = false;

                                    if (result.getInt("isPaid") == 0) isPaid = false;
                                    else isPaid = true;

                                    if (result.getInt("isApproved") == 0) isApproved = false;
                                    else isApproved = true;

                                    String date = result.getString("date");

                                    AppointmentsModel dataModel = new AppointmentsModel(id,user_id,doctor_id,speciality_id,isPaid,isApproved,user_name,message,app_date,app_time,date);
                                    appointmentsModels.add(dataModel);

                                    appointmentsAdapter = new AppointmentsAdapter(NewAppointments.this, appointmentsModels);
                                    recyclerView.setAdapter(appointmentsAdapter);
                                    
                                   */
                                    Toast.makeText(NewAppointments.this, "I better works", Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                Toast.makeText(NewAppointments.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NewAppointments.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(NewAppointments.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("doctor_id", "1");

                return params;
            }
        };

        RequestHandler.getInstance(NewAppointments.this).addToRequestQueue(stringRequest);

    }
}