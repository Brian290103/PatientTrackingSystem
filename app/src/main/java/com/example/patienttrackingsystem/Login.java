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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {
    Animation bottomAnim;
    TextView call_signUp;
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


        n_id = findViewById(R.id.txt_n_id);
        pass = findViewById(R.id.txt_pass);

        login = findViewById(R.id.button);
        progressDialog = new ProgressDialog(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_no = n_id.getText().toString();
                String pwd = pass.getText().toString();

                String URL = "http://172.16.25.26/Android%20Patient%20Tracking%20System/login.php";

                progressDialog.setMessage("Signing in...");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);

                                    String message= jsonObject.getString("message");
                                    Toast.makeText(Login.this,message, Toast.LENGTH_SHORT).show();

                                    if(message.equals("Login Succesfull")){
                                        startActivity(new Intent(Login.this,Dashboard.class));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
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
                        params.put("pwd", pwd);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                requestQueue.add(stringRequest);

                //   startActivity(new Intent(Login.this,Dashboard.class));
                //  finish();
            }
        });


    }
}