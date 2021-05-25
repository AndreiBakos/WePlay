package com.example.weplay.Playground.Trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.weplay.ContactData.Contact;
import com.example.weplay.Database.DatabaseHandler;
import com.example.weplay.R;

import java.util.ArrayList;
import java.util.List;

public class TriviaLeaderboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_leaderboard);

        ImageView leaderboard_back_btn = findViewById(R.id.leaderboard_back_btn);
        ImageView showHomeScores = findViewById(R.id.id_showTriviaLeaderboardScores);
        ImageView showInternetScores = findViewById(R.id.id_showInternetLeaderboardResults);
        ListView lv = findViewById(R.id.id_triviaLeaderboardListView);
        Intent intent = getIntent();
        String username = intent.getStringExtra("ForTriviaLeaderboardUsername");

        leaderboard_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(v,View.ALPHA,0f,1f).setDuration(400).start();
                Intent backIntent = new Intent();
                finish();
            }
        });
        showHomeScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(v, View.ROTATION, 0f, 90f, 0f,-90f,0f).setDuration(800).start();
                DatabaseHandler db = new DatabaseHandler(v.getContext());

                ObjectAnimator.ofFloat(lv,View.SCALE_X,0.95f,1f).setDuration(200).start();
                ObjectAnimator.ofFloat(lv,View.SCALE_Y,0.95f,1f).setDuration(200).start();

                List<Contact.Scores> scoresList = db.getUserScores(username,"TRIVIA");
                List<String> scores = new ArrayList<>();
                for(int i = 0;i < scoresList.size();i++){
                    scores.add("                           "+ scoresList.get(i).getUsername() + "       "+  scoresList.get(i).getScore() + "\n");
                }
                //Toast.makeText(TriviaLeaderboard.this,scores.toString(),Toast.LENGTH_LONG).show();

                ArrayAdapter scoresAdapter = new ArrayAdapter<String>(TriviaLeaderboard.this, android.R.layout.simple_dropdown_item_1line,scores);
                lv.setAdapter(scoresAdapter);
            }
        });
        showInternetScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(v, View.ROTATION, 0f, 90f, 0f,-90f,0f).setDuration(800).start();

                ObjectAnimator.ofFloat(lv,View.SCALE_X,0.95f,1f).setDuration(200).start();
                ObjectAnimator.ofFloat(lv,View.SCALE_Y,0.95f,1f).setDuration(200).start();

                DatabaseHandler db = new DatabaseHandler(v.getContext());
                List<Contact.Scores> all_scoresList = db.getAllScores("TRIVIA");

                List<String> all_scores = new ArrayList<>();
                for(int i = 0;i < all_scoresList.size();i++){
                    all_scores.add("                           "+all_scoresList.get(i).getUsername() + "       "+  all_scoresList.get(i).getScore() + "\n");
                }
                //Toast.makeText(TriviaLeaderboard.this,scores.toString(),Toast.LENGTH_LONG).show();
                ArrayAdapter all_scoresAdapter = new ArrayAdapter<String>(TriviaLeaderboard.this, android.R.layout.simple_dropdown_item_1line,all_scores);
                lv.setAdapter(all_scoresAdapter);
            }
        });
    }
}