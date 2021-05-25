package com.example.weplay;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weplay.Database.DatabaseHandler;
import com.example.weplay.Playground.Playgroung;
import com.example.weplay.Profile.ProfileActivity;


public class MainActivity extends AppCompatActivity {
    ImageView menuBtn, close_btn, game_btn, profile_btn;
    boolean isVisible = true;
    ImageView hintImage,changeLeftImage,changeRightImage;
    private int currentImage = 0,i = 0;
    int[] images =new int[] {R.drawable.hint1_1,R.drawable.hint2,R.drawable.hint3_2,R.drawable.hint4_2};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting up database
        DatabaseHandler db = new DatabaseHandler(this);

        //defining buttons
        menuBtn = findViewById(R.id.id_menuBtn);
        close_btn = findViewById(R.id.id_closeBtn);

        profile_btn = findViewById(R.id.id_profileBtn);
        profile_btn.animate().setDuration(0).alpha(0.0f);
        profile_btn.setVisibility(View.VISIBLE);

        game_btn = findViewById(R.id.id_gameBtn);
        game_btn.animate().setDuration(0).alpha(0.0f);
        game_btn.setVisibility(View.VISIBLE);

        ///-----test
        changeLeftImage = findViewById(R.id.id_changeImageLeftBtn);
        changeRightImage = findViewById(R.id.id_ChangeImageRightBtn);
        hintImage = findViewById(R.id.id_hintImage);
        hintImage.setImageResource(images[currentImage]);
        ///-----test
        //Setting the top text to be the user text
        TextView greet = findViewById(R.id.id_userHomeGreet);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("username");
            //The key argument here must match that used in the other activity
            greet.setText(value);
        }
        //Button for animating home button + popping game/profile buttons
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVisible) {
                    ObjectAnimator.ofFloat(close_btn,View.SCALE_X,0.95f,1f).setDuration(200).start();
                    close_btn.animate().setDuration(1000).rotation(360f).alpha(0f).start();
                    v.animate().setDuration(1500).alpha(1f).rotation(360f).start();
                    game_btn.animate().setDuration(800).alpha(0f).rotation(-360f).start();
                    profile_btn.animate().setDuration(800).alpha(0f).rotation(-360f).start();
                    isVisible = true;
                    //sets empty setOnClickListeners when game_btn and profile_btn are not visible
                    game_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                    profile_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                } else {
                    ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(100).start();
                    v.animate().setDuration(800).alpha(0f).rotation(-360f).start();
                    close_btn.animate().setDuration(1000).alpha(1f).rotation(-360f).start();
                    game_btn.animate().setDuration(1000).alpha(1.0f).rotation(360f).start();
                    profile_btn.animate().setDuration(1000).alpha(1.0f).rotation(360f).start();
                    isVisible = false;
                    //sets setOnClickListeners when game_btn and profile_btn are visible
                    game_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(200).start();
                            Intent playground_intent = new Intent(MainActivity.this, Playgroung.class);
                            TextView greet = findViewById(R.id.id_userHomeGreet);
                            playground_intent.putExtra("UsernameForPlayground",greet.getText().toString().trim());
                            startActivityForResult(playground_intent,1);
                        }
                    });
                    profile_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(200).start();
                            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                            TextView greet = findViewById(R.id.id_userHomeGreet);
                            intent.putExtra("UsernameFromHomeUsername",greet.getText().toString().trim());
                            startActivityForResult(intent,0);
                        }
                    });
                }
            }
        });

        changeRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(v,View.ALPHA,0f,1f).setDuration(100).start();
                ObjectAnimator.ofFloat(hintImage,View.SCALE_X,0.95f,1f).setDuration(200).start();
                ObjectAnimator.ofFloat(hintImage,View.SCALE_Y,0.95f,1f).setDuration(200).start();
                currentImage++;
                if(currentImage == images.length - 1){
                    changeLeftImage.setImageResource(R.drawable.arrow10);
                    changeRightImage.setImageResource(R.drawable.arrow8);
                    currentImage %= images.length;
                    hintImage.setImageResource(images[currentImage]);
                }else{
                    currentImage %= images.length;
                    hintImage.setImageResource(images[currentImage]);
                    changeLeftImage.setImageResource(R.drawable.arrow9);
                    changeRightImage.setImageResource(R.drawable.arrow7);
                }
            }
        });

        changeLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentImage == 0){
                    ObjectAnimator.ofFloat(v,View.ALPHA,0f,1f).setDuration(100).start();
                    ObjectAnimator.ofFloat(hintImage,View.SCALE_X,0.95f,1f).setDuration(200).start();
                    ObjectAnimator.ofFloat(hintImage,View.SCALE_Y,0.95f,1f).setDuration(200).start();
                    currentImage = images.length - 1;
                    hintImage.setImageResource(images[currentImage]);
                    changeLeftImage.setImageResource(R.drawable.arrow10);
                    changeRightImage.setImageResource(R.drawable.arrow8);
                }else {
                    ObjectAnimator.ofFloat(v,View.ALPHA,0f,1f).setDuration(100).start();
                    ObjectAnimator.ofFloat(hintImage,View.SCALE_X,0.95f,1f).setDuration(200).start();
                    ObjectAnimator.ofFloat(hintImage,View.SCALE_Y,0.95f,1f).setDuration(200).start();
                    currentImage--;
                    hintImage.setImageResource(images[currentImage]);
                    changeLeftImage.setImageResource(R.drawable.arrow9);
                    changeRightImage.setImageResource(R.drawable.arrow7);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == RESULT_OK) {
            String editTextValue = data.getStringExtra("FromProfileResult");
            TextView a = findViewById(R.id.id_userHomeGreet);
            a.setText(String.valueOf(editTextValue));
        }
    }

}