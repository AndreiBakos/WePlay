package com.example.weplay;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;

import com.example.weplay.ContactData.Contact;
import com.example.weplay.Database.DatabaseHandler;
import com.example.weplay.Util.Util;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextInputLayout username = findViewById(R.id.signUp_username);
        TextInputLayout email = findViewById(R.id.signUp_email);
        TextInputLayout password = findViewById(R.id.signUp_password);
        Button SignUpSubmit = findViewById(R.id.id_signUpSubmitBtn);
        TextView LoginRedirect = findViewById(R.id.id_signUpRedir);
        //Creating DatabaseHandler object
        DatabaseHandler db = new DatabaseHandler(this);

        SignUpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact();
                String strusername = username.getEditText().getText().toString().trim();
                String stremail = email.getEditText().getText().toString().trim();
                String strpassword = password.getEditText().getText().toString().trim();
                //checking if all the fields are empty
                if(strusername.isEmpty() && stremail.isEmpty() && strpassword.isEmpty()) {
                    username.setError("Completet The Field!");
                    email.setError("Completet The Field");
                    password.setError("Complete The Field");
                }
                //checking if username and email fields are empty and if the password is bigger than 8
                else if(strusername.isEmpty() && stremail.isEmpty()){
                    username.setError("Completet The Field!");
                    email.setError("Completet The Field");
                    if(strpassword.length() < 8)
                        password.setError("Password too short");
                    else
                        password.setError(null);
                }
                //checking if username and password fields are empty and if the email is valid
                else if(strusername.isEmpty() && strpassword.isEmpty()){
                    username.setError("Completet The Field!");
                    if(!stremail.matches("^[A-Za-z0-9+_.-]+@[a-z]+(.+)[a-z]$"))
                        email.setError("Invalid Email");
                    else
                        email.setError(null);
                    password.setError("Complete The Field");
                }
                //checking if email and password fields are empty
                else if(stremail.isEmpty() && strpassword.isEmpty()){
                    if(strusername.length() > 11)
                        username.setError("Username too long");
                    else
                        username.setError(null);
                    email.setError("Completet The Field");
                    password.setError("Complete The Field");
                }
                //checking if just username is empty,password bigger than 8 and if email is valid
                else if(strusername.isEmpty()){
                    username.setError("Completet The Field!");
                    if(!stremail.matches("^[A-Za-z0-9+_.-]+@[a-z]+(.+)[a-z]$"))
                        email.setError("Invalid Email");
                    else
                        email.setError(null);
                    if(strpassword.length() < 8)
                        password.setError("Password too short");
                    else
                        password.setError(null);
                }
                //checking if just email and password bigger than 8
                else if(stremail.isEmpty()){
                    if(strusername.length() > 11)
                        username.setError("Username too long");
                    else
                        username.setError(null);

                    email.setError("Completet The Field");
                    if(strpassword.length() < 8)
                        password.setError("Password too short");
                    else
                        password.setError(null);
                }
                //checking if password is empty and if email is valid
                else if(strpassword.isEmpty()){
                    if(strusername.length() > 11)
                        username.setError("Username too long");
                    else
                        username.setError(null);

                    if(!stremail.matches("^[A-Za-z0-9+_.-]+@[a-z]+(.+)[a-z]$"))
                        email.setError("Invalid Email");
                    else
                        email.setError(null);
                    password.setError("Complete The Field");
                }
                else {
                        if(strusername.length() > 11)
                            username.setError("Username too long");
                        else
                            username.setError(null);

                        email.setError(null);
                        password.setError(null);
                        //checks if email is valid using regular expressions
                        if(!stremail.matches("^[A-Za-z0-9]+@[a-z]+(.+)[a-z]$")){
                            email.setError("Invalid Email");
                            if(strusername.length() > 11)
                                username.setError("Username too long");
                            else
                                username.setError(null);

                            if(strpassword.length() < 8)
                                password.setError("Password too short");
                            else
                                password.setError(null);
                        }else {
                            if(strusername.length() > 11)
                                username.setError("Username too long");
                            else
                                username.setError(null);

                            if(strpassword.length() < 8){
                                password.setError("Password too short");
                            }else {
                                if(strusername.length() <= 11){
                                    username.setError(null);
                                    if (db.CheckIfUserExists(strusername, stremail, null) > 0) {
                                        Toast.makeText(getApplicationContext(),
                                                "User Already exists!",
                                                Toast.LENGTH_LONG)
                                                .show();
                                    }else {
                                        Contact.Users user = contact.new Users(strusername, stremail, strpassword);
                                        db.addUser(user);
                                        db.addDefaultScore(strusername);
                                        Intent intent = new Intent(v.getContext(), Login.class);
                                        Toast.makeText(getApplicationContext(),
                                                "Sign Up succesful!",
                                                Toast.LENGTH_LONG)
                                                .show();
                                        startActivity(intent);
                                        finish();
                                    }
                                }else{
                                    username.setError("Username too long");
                                }
                            }
                        }
                    }
                ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(200).start();
            }
        });

        LoginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(200).start();
                Intent intent = new Intent(v.getContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}