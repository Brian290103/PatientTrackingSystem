package com.example.patienttrackingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Animation bottomAnim;
    TextView call_signUp;

    ImageView image;
    TextView logo, slogan;

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CardView cardView=findViewById(R.id.cardView);

        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        cardView.setAnimation(bottomAnim);


        //Hooks
        image =findViewById(R.id.logo_image);
        logo =findViewById(R.id.logo_name);
        slogan =findViewById(R.id.slogan_name);


        call_signUp=findViewById(R.id.textView7);
        call_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Register.class);
                // startActivity(intent);
                //finish();//To remove this activity from the activity store

                Pair[] pairs=new Pair[3];
                pairs[0]=new Pair<View,String>(image,"logo_image");
                pairs[1]=new Pair<View,String>(logo,"logo_name");
                pairs[2]=new Pair<View,String>(slogan,"slogan_name");

                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                startActivity(intent,options.toBundle());
                //finish();
            }
        });

        login=findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Dashboard.class));
                finish();
            }
        });

    }
}