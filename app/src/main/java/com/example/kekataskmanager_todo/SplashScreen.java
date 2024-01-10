package com.example.kekataskmanager_todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("App", MODE_PRIVATE);
                String name = sharedPreferences.getString("userName","");

                if(name.equals("")) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }else{
//                    startActivity(new Intent(getApplicationContext(), DashBoard.class));
//                    finish();
                }
            }
        },2000);

    }
}