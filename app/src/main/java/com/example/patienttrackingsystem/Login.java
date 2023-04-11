package com.example.patienttrackingsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {
    Animation bottomAnim;
    TextView call_signUp, btn_patientLogin;
    ImageView image;
    TextView logo, slogan;

    EditText n_id, pass;
    Button login;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            return;
        }

        CardView cardView = findViewById(R.id.cardView);

        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        cardView.setAnimation(bottomAnim);


        //Hooks
        image = findViewById(R.id.logo_image);
        logo = findViewById(R.id.logo_name);
        slogan = findViewById(R.id.slogan_name);


        call_signUp = findViewById(R.id.textView7);
        call_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                // startActivity(intent);
                //finish();//To remove this activity from the activity store

                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logo, "logo_name");
                pairs[2] = new Pair<View, String>(slogan, "slogan_name");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());
                //finish();
            }
        });
        btn_patientLogin = findViewById(R.id.btn_patientLogin);
        btn_patientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, loginStaff.class));
            }
        });


        n_id = findViewById(R.id.txtEmail);
        pass = findViewById(R.id.txtPass);

        login = findViewById(R.id.btn_loginStaff);
        progressDialog = new ProgressDialog(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_no = n_id.getText().toString();
                String pwd = pass.getText().toString();

                progressDialog.setMessage("Signing in...");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);

                                    if (!jsonObject.getBoolean("error")) {
                                        SharedPrefManager.getInstance(Login.this)
                                                .userLogIn(
                                                        jsonObject.getInt("user_id"),
                                                        jsonObject.getInt("id_no"),
                                                        jsonObject.getString("fname"),
                                                        jsonObject.getString("lname"),
                                                        jsonObject.getString("gender"),
                                                        jsonObject.getString("phone"),
                                                        jsonObject.getString("email"),
                                                        jsonObject.getString("address"),
                                                        jsonObject.getString("date")
                                                );
                                        //Toast.makeText(Login.this, "User Logged In Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, Dashboard.class));
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                    }


                                } catch (JSONException e) {
                                    //  e.printStackTrace();
                                    //Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(Login.this, "An Error Occurred, Try Again Later", Toast.LENGTH_SHORT).show();


                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id_no", id_no);
                        params.put("pass", pwd);
                        return params;
                    }
                };

                RequestHandler.getInstance(Login.this).addToRequestQueue(stringRequest);

                //RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                // requestQueue.add(stringRequest);

                //   startActivity(new Intent(Login.this,Dashboard.class));
                //  finish();
            }
        });


    }
}