package com.example.patienttrackingsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    FloatingActionButton floatingActionButton;
    TextView login_page;
    TextInputLayout gender_layout;
    AutoCompleteTextView gender_dropdown;
    EditText txt_id_no, txt_fname, txt_lname, txt_phone, txt_email, txt_address, txt_pass, txt_cpass;
    Button reg_button;
    ProgressDialog progressDialog;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login_page = findViewById(R.id.loginTxt);
        login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });

        txt_id_no = findViewById(R.id.txt_id_no);
        txt_fname = findViewById(R.id.txt_fname);
        txt_lname = findViewById(R.id.txt_lname);
        txt_phone = findViewById(R.id.txt_phone);
        txt_email = findViewById(R.id.txt_email);
        txt_address = findViewById(R.id.txt_address);
        txt_pass = findViewById(R.id.txt_pass);
        txt_cpass = findViewById(R.id.txt_cpass);

        gender_layout = findViewById(R.id.gender_layout);
        gender_dropdown = findViewById(R.id.gender_dropdown);

        String[] gender_list = {"Male", "Female"};
        ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(Register.this, R.layout.gender_list, gender_list);
        gender_dropdown.setAdapter(gender_adapter);
        gender_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
                Toast.makeText(Register.this, gender, Toast.LENGTH_SHORT).show();
            }
        });

        progressDialog = new ProgressDialog(this);
        reg_button = findViewById(R.id.reg_button);
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_no = txt_id_no.getText().toString();
                String fname = txt_fname.getText().toString();
                String lname = txt_lname.getText().toString();
                String phone = txt_phone.getText().toString();
                String email = txt_email.getText().toString();
                String address = txt_address.getText().toString();
                String pass = txt_pass.getText().toString();
                String cpass = txt_cpass.getText().toString();

                if (id_no.isEmpty() || fname.isEmpty() || lname.isEmpty() || phone.isEmpty() || address.isEmpty() || pass.isEmpty() || cpass.isEmpty()) {

                    Toast.makeText(Register.this, "One of the fields is empty. Kindly fill all the required fields", Toast.LENGTH_LONG).show();

                } else {

                    if (cpass.equals(pass)) {

                        String URL = "http://172.16.25.26/Android%20Patient%20Tracking%20System/sign_up.php";

                        progressDialog.setMessage("Registering User...");
                        progressDialog.show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = new JSONObject(response);

                                            String message = jsonObject.getString("message");
                                            Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();

                                            if (message.equals("User Registered Successfully")) {
                                                Toast.makeText(Register.this, "Login to Continue", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Register.this, Login.class));
                                                finish();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                                Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("fname", fname);
                                params.put("lname", lname);
                                params.put("id_no", id_no);
                                params.put("gender", gender);
                                params.put("phone", phone);
                                params.put("email", email);
                                params.put("address", address);
                                params.put("pass", pass);
                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                        requestQueue.add(stringRequest);

                    } else {
                        Toast.makeText(Register.this, "The Passwords Don't Match. Kindly Try Again", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


    }
}