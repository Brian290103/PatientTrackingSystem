package com.example.patienttrackingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MakeAppointment extends AppCompatActivity {

    ProgressDialog progressDialog;
    ImageView btn_back;
    private RecyclerView recyclerView;
    private SpecialtyAdapter specialtyAdapter;
    private ArrayList<SpecialtyModel> specialtyModels;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);

        specialtyModels = new ArrayList<>();
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
        progressDialog.setMessage("Generating Appointments...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_SPECIALITY,
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

                                    int id = result.getInt("id");
                                    String name = result.getString("name");
                                    String desc = result.getString("desc");

                                    SpecialtyModel dataModel = new SpecialtyModel(id, name, desc);
                                   // SpecialtyModel dataModel = new SpecialtyModel(i,"Denstist","A Person who checks our teeth");
                                    specialtyModels.add(dataModel);

                                    specialtyAdapter = new SpecialtyAdapter(MakeAppointment.this, specialtyModels);
                                    recyclerView.setAdapter(specialtyAdapter);

                                }

                            } else {
                                Toast.makeText(MakeAppointment.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MakeAppointment.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(MakeAppointment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestHandler.getInstance(MakeAppointment.this).addToRequestQueue(stringRequest);


    }


    private void searchArrayList(String newText) {
        ArrayList<SpecialtyModel> dataSearchArrayList=new ArrayList<>();

        for (SpecialtyModel data : specialtyModels) {
            if (data.getName().toLowerCase().contains(newText.toLowerCase())) {
                dataSearchArrayList.add(data);
            }
        }

        if (dataSearchArrayList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            specialtyAdapter.setSearchList(dataSearchArrayList);
        }
    }
}