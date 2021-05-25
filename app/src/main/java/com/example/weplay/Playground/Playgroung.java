package com.example.weplay.Playground;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weplay.Database.DatabaseHandler;
import com.example.weplay.MainActivity;

import com.example.weplay.Playground.Trivia.TriviaGame;
import com.example.weplay.R;

import org.w3c.dom.Text;

public class Playgroung extends AppCompatActivity {
    boolean isPlayBtnPressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playgroung);

        ImageView second_gameBtn = findViewById(R.id.id_SecondGameBtn);
        second_gameBtn.animate().alpha(0f).setDuration(0).start();
        second_gameBtn.setVisibility(View.VISIBLE);

        ImageView playBtn = findViewById(R.id.id_Playground_PlayBtn);
        ImageView closeBtn = findViewById(R.id.id_Playground_Close_PlayBtn);
        ImageView triviaBtn = findViewById(R.id.id_PlaygroundTriviaBtn);
        triviaBtn.animate().alpha(0f).setDuration(0).start();
        triviaBtn.setVisibility(View.VISIBLE);

        ImageView homeRedir = findViewById(R.id.id_redirToHomeBtn);
        TextView usernameTopText = findViewById(R.id.id_PlaygroundUsernameTopText);
        Intent intent = getIntent();
        String editTextValue = intent.getStringExtra("UsernameForPlayground");
        usernameTopText.setText("Hi,\n" + String.valueOf(editTextValue));
        homeRedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(v,View.ALPHA,0f,1f).setDuration(100).start();
                Intent redirHome = new Intent(Playgroung.this, MainActivity.class);
                redirHome.putExtra("FromProfileResult", usernameTopText.getText().toString().trim());
                setResult(RESULT_OK,redirHome);
                finish();
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPlayBtnPressed){
                    ObjectAnimator.ofFloat(v,View.SCALE_X,0.95f,1f).setDuration(200).start();
                    ObjectAnimator.ofFloat(v,View.SCALE_Y,0.95f,1f).setDuration(200).start();

                    v.animate().setDuration(1000).alpha(0f).rotation(-360f).start();
                    closeBtn.animate().setDuration(1500).alpha(1f).rotation(-360f).start();

                    ObjectAnimator.ofFloat(triviaBtn,View.SCALE_X,0.65f,1f).setDuration(200).start();
                    ObjectAnimator.ofFloat(triviaBtn,View.SCALE_Y,0.95f,1f).setDuration(200).start();

                    second_gameBtn.animate().setDuration(1000).alpha(1f).rotation(360f).start();
                    second_gameBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ObjectAnimator.ofFloat(v,View.ALPHA,0f,1f).setDuration(200).start();
                            Toast.makeText(v.getContext(),"GAME DEVELOPMENT IN PROGRESS!",Toast.LENGTH_SHORT).show();
                        }
                    });

                    triviaBtn.animate().setDuration(1000).alpha(1f).rotation(360f).start();
                    triviaBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //-------------------
                            DatabaseHandler db = new DatabaseHandler(v.getContext());
                            String highScoreResult = db.getScore(editTextValue,"TRIVIA");
                            //---------------------
                            ObjectAnimator.ofFloat(v,View.ALPHA,0f,1f).setDuration(200).start();
                            Intent triviaGame = new Intent(v.getContext(),TriviaGame.class);
                            Intent intent = getIntent();
                            String editTextValue = intent.getStringExtra("UsernameForPlayground");
                            triviaGame.putExtra("UsernameForTriviaGame",editTextValue);
                            triviaGame.putExtra("HighScoreForTriviaGame",highScoreResult);
                            startActivityForResult(triviaGame,3);
                        }
                    });

                    isPlayBtnPressed = true;
                }else{
                    ObjectAnimator.ofFloat(closeBtn,View.SCALE_X,0.95f,1f).setDuration(200).start();
                    ObjectAnimator.ofFloat(closeBtn,View.SCALE_Y,0.95f,1f).setDuration(200).start();

                    v.animate().setDuration(1500).alpha(1f).rotation(360f).start();
                    closeBtn.animate().setDuration(1000).alpha(0f).rotation(360f).start();

                    ObjectAnimator.ofFloat(triviaBtn,View.SCALE_X,0.65f,1f).setDuration(200).start();
                    ObjectAnimator.ofFloat(triviaBtn,View.SCALE_Y,0.65f,1f).setDuration(200).start();
                    second_gameBtn.animate().setDuration(1000).alpha(0f).rotation(-360f).start();
                    second_gameBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    triviaBtn.animate().setDuration(1000).alpha(0f).rotation(-360f).start();
                    triviaBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    isPlayBtnPressed = false;
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3 && resultCode == RESULT_OK){
            String editTextString = data.getStringExtra("UsernameForTriviaGame");
            TextView usernameTopText = findViewById(R.id.id_PlaygroundUsernameTopText);
            usernameTopText.setText("Hi,\n"+ editTextString);
        }
    }
}