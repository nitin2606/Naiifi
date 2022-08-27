package com.example.naiifi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                Intent  intent = new Intent(SplashActivity.this , RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        thread.start();

    }
}