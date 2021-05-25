package com.example.weplay.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weplay.Database.DatabaseHandler;
import com.example.weplay.Login;
import com.example.weplay.Playground.Playgroung;
import com.example.weplay.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileActivity extends AppCompatActivity {
    ImageView editBtn,submitUsernameBtn,backHomeBtn,gameBtn,logoutBtn,menu;
    TextView usernameProfileText;
    TextInputLayout usernameTextChange;
    TextInputEditText textInputEditText;
    boolean isEditBtnVisible = true, isCancelBtnClicked = false,isMenuBtnClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Elements for Menu Button:
        menu = findViewById(R.id.id_ProfileRotateMenu);
        //Go home button
        backHomeBtn = findViewById(R.id.id_backHomeBtn);
        backHomeBtn.animate().setDuration(0).alpha(0f).scaleX(0.60f).scaleY(0.60f).start();
        backHomeBtn.setVisibility(View.VISIBLE);
        //Go to game page button
        gameBtn = findViewById(R.id.id_ProfileGameBtn);
        gameBtn.animate().setDuration(0).alpha(0f).scaleX(0.60f).scaleY(0.60f).start();
        gameBtn.setVisibility(View.VISIBLE);
        //LOGOUT button
        logoutBtn = findViewById(R.id.id_logoutBtn);
        logoutBtn.animate().setDuration(0).alpha(0f).scaleX(0.60f).scaleY(0.60f).start();
        logoutBtn.setVisibility(View.VISIBLE);
        //elements for the top part of the app
        editBtn = findViewById(R.id.id_editBtn);
        submitUsernameBtn = findViewById(R.id.id_checkSaveUsername);
        usernameProfileText = findViewById(R.id.id_usernameProfileText);
        //Get the string for the username title
        Intent intent = getIntent();
        String username_str_value = intent.getStringExtra("UsernameFromHomeUsername");
        usernameProfileText.setText(username_str_value);
        //cancel button
        ImageView cancelSaveBtn = findViewById(R.id.id_CancelSaveUsername);
        cancelSaveBtn.setVisibility(View.INVISIBLE);
        cancelSaveBtn.animate().setDuration(0).alpha(0f).start();
        //------
        usernameTextChange = findViewById(R.id.id_UsernameTextChange);
        textInputEditText = findViewById(R.id.id_textInputEditText);
        textInputEditText.setText(usernameProfileText.getText().toString().trim());
        usernameTextChange.animate().setDuration(0).alpha(0f).start();
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditBtnVisible) {
                    String username = usernameTextChange.getEditText().getText().toString().trim();
                    cancelSaveBtn.setVisibility(View.VISIBLE);
                    v.animate().setDuration(800).rotation(360f).alpha(0f).start();
                    cancelSaveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            usernameTextChange.setError(null);
                            usernameProfileText.setText(username);
                            textInputEditText.setText(username);
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            submitUsernameBtn.animate().setDuration(800).rotation(-360f).alpha(0f).start();
                            v.animate().setDuration(800).rotation(-360f).alpha(0f).start();
                            editBtn.animate().setDuration(1000).rotation(-360f).alpha(1f).start();
                            usernameTextChange.setVisibility(View.INVISIBLE);
                            usernameProfileText.animate().setDuration(1000).alpha(1f).start();
                            usernameTextChange.animate().setDuration(500).alpha(0f).start();
                            isEditBtnVisible = true;
                            isCancelBtnClicked = true;
                        }
                    });
                    submitUsernameBtn.animate().setDuration(1000).rotation(360f).alpha(1f).start();
                    cancelSaveBtn.animate().setDuration(1000).rotation(360f).alpha(1f).start();
                    usernameTextChange.setVisibility(View.VISIBLE);
                    usernameProfileText.animate().setDuration(500).alpha(0f).start();
                    usernameTextChange.animate().setDuration(1000).alpha(1f).start();
                    isEditBtnVisible = false;
                    isCancelBtnClicked = false;
                }else {
                    String username = usernameTextChange.getEditText().getText().toString().trim();

                    if (!isCancelBtnClicked) {
                        if (!username.isEmpty()) {usernameTextChange.setError(null);
                            if (username.length() <= 11) {usernameTextChange.setError(null);
                                DatabaseHandler db = new DatabaseHandler(v.getContext());
                                if (db.CheckIfUsernameExists(username) < 1) {
                                    String username_str_value = usernameProfileText.getText().toString().trim();
                                    usernameTextChange.setError(null);
                                    db.ChangeUsername(username_str_value, username);
                                    usernameProfileText.setText(username);
                                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                    submitUsernameBtn.animate().setDuration(800).rotation(-360f).alpha(0f).start();
                                    cancelSaveBtn.animate().setDuration(800).rotation(-360f).alpha(0f).start();
                                    v.animate().setDuration(1000).rotation(-360f).alpha(1f).start();
                                    usernameTextChange.setVisibility(View.INVISIBLE);
                                    usernameProfileText.animate().setDuration(1000).alpha(1f).start();
                                    usernameTextChange.animate().setDuration(500).alpha(0f).start();
                                    isEditBtnVisible = true;
                                } else {
                                    usernameTextChange.setError("Username already exists");
                                }
                            } else {
                                usernameTextChange.setError("Username too long");
                            }
                        } else {
                            usernameTextChange.setError("Empty field");
                        }
                    }
                }
            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isMenuBtnClicked){
                    ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(200).start();
                    v.animate().setDuration(600).rotation(360f).scaleX(0.60f).scaleY(0.60f).start();
                    gameBtn.animate().setDuration(600).alpha(1f).rotation(360f).scaleX(0.90f).scaleY(0.90f).start();
                    gameBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(200).start();
                            ObjectAnimator.ofFloat(v,View.SCALE_Y,0.95f,1f).setDuration(200).start();
                            Intent playground_intent = new Intent(v.getContext(), Playgroung.class);
                            TextView greet = findViewById(R.id.id_userHomeGreet);
                            playground_intent.putExtra("UsernameForPlayground",usernameProfileText.getText().toString().trim());
                            startActivityForResult(playground_intent,1);
                        }
                    });

                    logoutBtn.animate().setDuration(600).alpha(1f).rotation(-360f).scaleX(0.90f).scaleY(0.90f).start();
                    logoutBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(200).start();
                            ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(200).start();
                            Intent intent = new Intent(v.getContext(), Login.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    backHomeBtn.animate().setDuration(600).alpha(1f).rotation(360f).scaleX(0.90f).scaleY(0.90f).start();
                    backHomeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(200).start();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("FromProfileResult", usernameProfileText.getText().toString().trim());
                            setResult(RESULT_OK,returnIntent);
                            finish();
                        }
                    });

                    isMenuBtnClicked = true;
                }else{
                    ObjectAnimator.ofFloat(v,View.SCALE_X,0.55f,0.60f).setDuration(200).start();
                    v.animate().setDuration(1000).rotation(-360f).scaleX(1f).scaleY(1f).start();
                    gameBtn.animate().setDuration(800).alpha(0f).rotation(-360f).scaleX(0.60f).scaleY(0.60f).start();
                    gameBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    logoutBtn.animate().setDuration(800).alpha(0f).rotation(360f).scaleX(0.60f).scaleY(0.60f).start();
                    logoutBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    backHomeBtn.animate().setDuration(800).alpha(0f).rotation(-360f).scaleX(0.60f).scaleY(0.60f).start();
                    backHomeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    isMenuBtnClicked = false;
                }
            }
        });
    }
}