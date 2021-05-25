package com.example.weplay.Playground.Trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weplay.ContactData.Contact;
import com.example.weplay.Database.DatabaseHandler;
import com.example.weplay.MainActivity;
import com.example.weplay.R;

import java.util.ArrayList;
import java.util.List;

public class TriviaResult extends AppCompatActivity {
    TextView usernameTopText;
    ImageView retry_btn,leaderboard_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_result);

        usernameTopText = findViewById(R.id.id_TriviaResultUsername);
        Intent intent = getIntent();
        String username = intent.getStringExtra("UsernameForTriviaResult");
        String scoreResult = intent.getStringExtra("score");
        int score = Integer.parseInt(scoreResult);
        String numberOfQuestions = intent.getStringExtra("NumberOfQuestions");
        int questions_length = Integer.parseInt(numberOfQuestions);
        if(score == 0)
            usernameTopText.setText("Maybe next time :(");
        else if(score > 0 && score <=4)
            usernameTopText.setText("Doing good!");
        else if(score > 4 && score <= 10)
            usernameTopText.setText("Massive Score!");
        else if(score > 10 && score < questions_length)
            usernameTopText.setText("You are the best!");
        else if(score == questions_length)
            usernameTopText.setText("KING OF TRIVIA!");

        retry_btn = findViewById(R.id.id_TriviaResultRetryBtn);
        leaderboard_btn = findViewById(R.id.id_TriviaResultLeaderBoardBtn);

        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retry_intent = new Intent(v.getContext(),TriviaGame.class);
                retry_intent.putExtra("key",score);
                setResult(RESULT_OK,retry_intent);
                finish();
            }
        });

        leaderboard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(v,View.ALPHA,0f,1f).setDuration(400).start();
               Intent to_leaderboard_intent = new Intent(v.getContext(),TriviaLeaderboard.class);
               to_leaderboard_intent.putExtra("ForTriviaLeaderboardUsername",username);
               startActivity(to_leaderboard_intent);

            }
        });
    }
}