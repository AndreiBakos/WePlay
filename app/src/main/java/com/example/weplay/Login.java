package com.example.weplay;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.example.weplay.Database.DatabaseHandler;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout email = findViewById(R.id.id_LoginEmail);
        TextInputLayout password = findViewById(R.id.id_password);
        Button Submit = findViewById(R.id.id_loginSubmitBtn);
        TextView SignUpRedirect = findViewById(R.id.id_signUpRedir);
        //Creating DatabaseHandler object
        DatabaseHandler db = new DatabaseHandler(this);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = email.getEditText().getText().toString().trim();
                String strPassword = password.getEditText().getText().toString().trim();
                if(strEmail.isEmpty() && strPassword.isEmpty()){
                    email.setError("Complete the field!");
                    password.setError("Complete the field!");
                }
                else if(strEmail.isEmpty()) {
                    email.setError("Complete the field");
                    password.setError(null);
                }else if(strPassword.isEmpty()){
                    if(!strEmail.matches("^[A-Za-z0-9]+@[a-z]+(.+)[a-z]$"))
                        email.setError("Invalid Email");
                    else
                        email.setError(null);
                    password.setError("Complete the field!");
                }else {
                    email.setError(null);
                    password.setError(null);
                    if(!strEmail.matches("^[A-Za-z0-9]+@[a-z]+(.+)[a-z]$"))
                        email.setError("Invalid Email");
                    else {
                        if (db.CheckIfUserExists(null, strEmail, strPassword) > 0) {
                            //getting the username by making query with email
                            String username = db.getUser(strEmail).getUsername();
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(v.getContext(), "Username does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(200).start();
            }
        });

        SignUpRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(200).start();
                Intent intent = new Intent(v.getContext(),SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }
}