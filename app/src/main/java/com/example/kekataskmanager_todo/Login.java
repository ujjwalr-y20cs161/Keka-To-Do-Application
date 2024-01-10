package com.example.kekataskmanager_todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText name,mail;

        name = findViewById(R.id.nameText);mail = findViewById(R.id.emailText);
        Button login = findViewById(R.id.LoginBtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("TODO",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();



                if(name.getText().toString().isEmpty()) {
                    name.requestFocus();
                    name.setError("Enter your Name!");
                }
                else if(mail.getText().toString().isEmpty()){
                    mail.requestFocus();
                    mail.setError("Mail's missing");
                } else {


                    editor.putString("userName", name.getText().toString());
                    editor.putString("userEmail", mail.getText().toString());

                    editor.apply();
                    editor.commit();

                    startActivity(new Intent(getApplicationContext(), DashBoard.class));
                    Toast.makeText(getApplicationContext(), "Welcome " + name, Toast.LENGTH_SHORT);
                    finish();
                }
            }
        });
    }
}