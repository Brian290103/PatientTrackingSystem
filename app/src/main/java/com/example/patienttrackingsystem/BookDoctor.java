package com.example.patienttrackingsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class BookDoctor extends AppCompatActivity {

    private int specialityId;
    private TextView txt_specialityName;
    ProgressDialog progressDialog;
    // DoctorModel doctorModel;
    ArrayList<DoctorModel> doctorModels;
    DoctorAdapter doctorAdapter;
    RecyclerView recyclerView;

    private  SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_doctor);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_specialityName = findViewById(R.id.txt_speciality_name);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            specialityId = bundle.getInt("ID");
            txt_specialityName.setText(bundle.getString("Name"));

            //Toast.makeText(this, "ID: "+specialityId, Toast.LENGTH_SHORT).show();
        }


        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);

        doctorModels = new ArrayList<>();

        retrieveData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchArrayList(newText);
                return false;
            }
        });
    }

    private void retrieveData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Doctors...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_DOCTOR_BY_SPECIALITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        JSONObject jsonObject = null;
                        //Toast.makeText(BookDoctor.this, "Reached", Toast.LENGTH_SHORT).show();
                        //System.out.println("reached");
                        try {
                            jsonObject = new JSONObject(response);

                            if (!jsonObject.getBoolean("error")) {
                                JSONArray array = jsonObject.getJSONArray("result");

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject result = array.getJSONObject(i);

                                    int user_id = result.getInt("user_id");
                                    String fname = result.getString("fname");
                                    String lname = result.getString("lname");
                                    int isPresent = result.getInt("isPresent");
                                    String available_times = result.getString("available_times");

                                    //  DoctorModel doctorModel = new DoctorModel(user_id,0,0,0,isPresent,fname,lname,"","","",available_times,"");
                                    DoctorModel doctorModel = new DoctorModel(user_id, 0, 0, specialityId, isPresent, fname, lname, "", "", "", available_times, "");
                                    doctorModels.add(doctorModel);

                                    doctorAdapter = new DoctorAdapter(BookDoctor.this, doctorModels);
                                    recyclerView.setAdapter(doctorAdapter);


                                }

                            } else {
                                Toast.makeText(BookDoctor.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BookDoctor.this, "Try Catch Exception Error\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(BookDoctor.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("speciality_id", Integer.toString(specialityId));
                return params;
            }
        };


        RequestHandler.getInstance(BookDoctor.this).addToRequestQueue(stringRequest);


    }


    private void searchArrayList(String newText) {
        ArrayList<DoctorModel> dataSearchArrayList = new ArrayList<>();

        for (DoctorModel data : doctorModels) {
            String name = data.getFname() + " " + data.getLname();

            if (name.toLowerCase().contains(newText.toLowerCase())) {
                dataSearchArrayList.add(data);
            }
        }

        if (dataSearchArrayList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            doctorAdapter.setSearchList(dataSearchArrayList);
        }
    }
}