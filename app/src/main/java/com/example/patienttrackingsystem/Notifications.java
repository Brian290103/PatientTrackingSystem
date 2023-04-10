package com.example.patienttrackingsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class Notifications extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<NotificationModel> notificationModels;
    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        notificationModels = new ArrayList<>();
        retrieveData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void retrieveData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Generating Appointments...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_NOTIFICATIONS,
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
                                    int user_id = result.getInt("user_id");
                                    String reason = result.getString("reason");

                                    Boolean isRead = false;

                                    if (result.getInt("isRead") == 0) isRead = false;
                                    else isRead = true;

                                    String date = result.getString("date");

                                    NotificationModel dataModel = new NotificationModel(id, user_id, isRead, reason, date);
                                    // SpecialtyModel dataModel = new SpecialtyModel(i,"Denstist","A Person who checks our teeth");
                                    notificationModels.add(dataModel);

                                    notificationAdapter = new NotificationAdapter(Notifications.this, notificationModels);
                                    recyclerView.setAdapter(notificationAdapter);

                                }

                            } else {
                                Toast.makeText(Notifications.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Notifications.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(Notifications.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", Integer.toString(SharedPrefManager.getInstance(Notifications.this).getUserId()));

                return params;
            }
        };

        RequestHandler.getInstance(Notifications.this).addToRequestQueue(stringRequest);


    }


}