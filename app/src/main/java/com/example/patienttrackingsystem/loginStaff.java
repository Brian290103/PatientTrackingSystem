package com.example.patienttrackingsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginStaff extends AppCompatActivity {

    TextInputEditText txtEmail, txtPassword;

    Button btn_loginStaff;
    TextView btn_patientLogin;

    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_staff);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPass);
        //auth = FirebaseAuth.getInstance();



        btn_loginStaff = findViewById(R.id.btn_loginStaff);
        btn_loginStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                loginUser(email, password);

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



    private void loginUser(String email, String password) {


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_DOCTOR_BY_EMAIl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(loginStaff.this, "Response: "+response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);

                            if (!jsonObject.getBoolean("error")) {

                                JSONArray array = jsonObject.getJSONArray("result");
                                JSONObject result = array.getJSONObject(0);

                                SharedPrefManager.getInstance(getApplicationContext())
                                        .staffLogIn(
                                                result.getInt("user_id"),
                                                result.getInt("id_no"),
                                                result.getString("fname"),
                                                result.getString("lname"),
                                                result.getString("gender"),
                                                result.getString("phone"),
                                                result.getString("email"),
                                                result.getString("address"),
                                                result.getInt("speciality_id"),
                                                result.getInt("isPresent"),
                                                result.getString("available_times"),
                                                result.getString("date")
                                        );

                                Toast.makeText(getApplicationContext(), "User Logged In Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), DashboardDr.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            //  e.printStackTrace();
                           // Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "An Error Occurred, Try Again Later", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dr_email", email);
                return params;
            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);




       /* auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if (email.equals("admin@gmail.com")) {
                    Toast.makeText(loginStaff.this, "Login Successful!\nAdmin: " + email, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(loginStaff.this, DashboardAdmin.class));
                    finish();
                } else {
                    Toast.makeText(loginStaff.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(loginStaff.this, DashboardDr.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(loginStaff.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(loginStaff.this, "Login Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        */


    }
}