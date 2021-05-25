package com.example.weplay.Playground.Trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weplay.ContactData.Contact;
import com.example.weplay.Database.DatabaseHandler;
import com.example.weplay.R;

import org.w3c.dom.Text;

public class TriviaGame extends AppCompatActivity {
    TextView currentScore;
    TextView highScore;
    TextView questionNumbering;
    TextView questions;
    ImageView falseBtn;
    ImageView trueBtn;
    ImageView prevQuestion;
    ImageView nextQuestion;
    Questions [] Questions = new Questions[]{
            new Questions("2! = 2",true),
            new Questions("HTTP means Hyper Text Transfer Protocol",true),
            new Questions("HTML is not a programming language",true),
            new Questions("Java cannot be used for building apps",false),
            new Questions("PHP is not a programming language",false),
            new Questions("CSS means Cascading Style Sheets",true),
            new Questions("HTML means Hypertext Markup Language",true),
            new Questions("React is used for building web/mobile apps",true),
            new Questions("React is a javascript framework",false),
            new Questions("Android is an operating system",true),
    };
    int index = 0,score = 0,hscore;
    boolean isAnswered = false, isTrueRotated = false, isFalseRotated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_game);

        Intent intent = getIntent();
        String username = intent.getStringExtra("UsernameForTriviaGame");
        String hscoreResult = intent.getStringExtra("HighScoreForTriviaGame");
        hscore = Integer.parseInt(hscoreResult);
        currentScore = findViewById(R.id.id_currentScore);
        highScore = findViewById(R.id.id_highScore);
        highScore.setText("High Score: "+ hscore);
        questionNumbering = findViewById(R.id.id_questionNumbering);
        questions = findViewById(R.id.id_questions);
        questions.setText(String.valueOf(Questions[index].getAnswerId()));
        questionNumbering.setText((index+1) + "/" +Questions.length);
        falseBtn = findViewById(R.id.id_falseBtn);
        trueBtn = findViewById(R.id.id_trueBtn);
        prevQuestion = findViewById(R.id.id_prevQuestion);
        nextQuestion = findViewById(R.id.id_NextQuestion);

        ImageView back_to_playground;
        back_to_playground = findViewById(R.id.id_backToPlayground);

        back_to_playground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(v,View.ALPHA,0f,1f).setDuration(400).start();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("UsernameForTriviaGame", username);
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAnswered = false;
                isTrueRotated = false;
                isTrueRotated = false;
                ObjectAnimator.ofFloat(v,View.ALPHA,0f,1f).setDuration(400).start();
                ObjectAnimator.ofFloat(questions,View.SCALE_X,0.95f,1f).setDuration(200).start();
                ObjectAnimator.ofFloat(questions,View.SCALE_Y,0.95f,1f).setDuration(200).start();
                trueBtn.animate().setDuration(0).alpha(1f).start();
                falseBtn.animate().setDuration(0).alpha(1f).start();
                trueBtn.setVisibility(View.VISIBLE);
                falseBtn.setVisibility(View.VISIBLE);
                if((index + 1) % Questions.length == 0){
                    if(score > hscore){
                        hscore = score;
                        highScore.setText("High Score: "+ hscore);
                    }
                    Contact contact = new Contact();
                    Contact.Scores scores = contact.new Scores(score,username,"TRIVIA");
                    DatabaseHandler db = new DatabaseHandler(v.getContext());
                    db.addScore(scores);
                    FinalResult(v);
                    score = 0;
                    currentScore.setText("Current score: 0");
                    index = 0;
                    questions.setText(String.valueOf(Questions[index].getAnswerId()));
                    questions.setBackgroundResource(R.color.white);
                    questions.setTextColor(Color.BLACK);
                    questionNumbering.setText((index+1) + "/"+Questions.length);
                    prevQuestion.setVisibility(View.INVISIBLE);
                }else{
                    index++;
                    questions.setText(String.valueOf(Questions[index].getAnswerId()));
                    questions.setBackgroundResource(R.color.white);
                    questions.setTextColor(Color.BLACK);
                    questionNumbering.setText((index+1) + "/"+Questions.length);
                    prevQuestion.setVisibility(View.VISIBLE);
                }
            }
        });

        prevQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAnswered = false;
                isTrueRotated = false;
                isTrueRotated = false;
                ObjectAnimator.ofFloat(v,View.ALPHA,0f,1f).setDuration(400).start();
                ObjectAnimator.ofFloat(questions,View.SCALE_X,0.95f,1f).setDuration(200).start();
                ObjectAnimator.ofFloat(questions,View.SCALE_Y,0.95f,1f).setDuration(200).start();
                trueBtn.animate().setDuration(0).alpha(1f).start();
                falseBtn.animate().setDuration(0).alpha(1f).start();
                trueBtn.setVisibility(View.VISIBLE);
                falseBtn.setVisibility(View.VISIBLE);
                if(index == 1){
                    prevQuestion.setVisibility(View.INVISIBLE);
                }
                if(index != 0) {
                    index--;
                    questions.setText(String.valueOf(Questions[index].getAnswerId()));
                    questions.setBackgroundResource(R.color.white);
                    questions.setTextColor(Color.BLACK);
                    questionNumbering.setText((index + 1) + "/" + Questions.length);

                }
            }
        });
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 5 && resultCode == RESULT_OK){
            hscore = data.getIntExtra("key",0);
        }
    }
*/
    public void TrueBtnClick(View v) {
        if (!isAnswered) {
            ObjectAnimator.ofFloat(v, View.ALPHA, 0f, 360f).setDuration(400).start();
            if (Questions[index].isAnswerResult()) {
                questions.setBackgroundResource(R.color.green);
                questions.setTextColor(Color.WHITE);
                score += 2;
                currentScore.setText(String.valueOf("Current score: " + score));
                ObjectAnimator.ofFloat(trueBtn, View.ROTATION, 0f, 90f, 0f,-90f,0f).setDuration(800).start();
                falseBtn.animate().alpha(0f).setDuration(300).start();
                falseBtn.setVisibility(View.INVISIBLE);
                isAnswered = true;
                isTrueRotated = true;
                isFalseRotated = false;
                    /*
                    trueBtn.setVisibility(View.INVISIBLE);
                    falseBtn.setVisibility(View.INVISIBLE);
                     */
            } else {
                questions.setBackgroundResource(R.color.red);
                questions.setTextColor(Color.WHITE);
                score -= 2;
                currentScore.setText(String.valueOf("Current score: " + score));
                ObjectAnimator.ofFloat(falseBtn, View.ROTATION, 0f, 90f, 0f,-90f,0f).setDuration(800).start();
                trueBtn.animate().alpha(0f).setDuration(300).start();
                trueBtn.setVisibility(View.INVISIBLE);
                isAnswered = true;
                isFalseRotated = true;
                isTrueRotated = false;
                    /*
                    trueBtn.setVisibility(View.INVISIBLE);
                    falseBtn.setVisibility(View.INVISIBLE);
                     */
            }
        }else{
            if(isTrueRotated)
                ObjectAnimator.ofFloat(trueBtn, View.ALPHA, 0f, 360f).setDuration(400).start();
            if(isFalseRotated)
                ObjectAnimator.ofFloat(falseBtn, View.ALPHA, 0f, 360f).setDuration(400).start();
        }
    }

    public void FalseBtnClick(View v) {
        ObjectAnimator.ofFloat(v, View.ALPHA, 0f, 1f).setDuration(400).start();
        if (!isAnswered) {
            if (!Questions[index].isAnswerResult()) {
                questions.setBackgroundResource(R.color.green);
                questions.setTextColor(Color.WHITE);
                score += 2;
                currentScore.setText("Current score: " + score);
                ObjectAnimator.ofFloat(falseBtn, View.ROTATION, 0f, 90f, 0f,-90f,0f).setDuration(800).start();
                trueBtn.animate().alpha(0f).setDuration(500).start();
                trueBtn.setVisibility(View.INVISIBLE);
                isAnswered = true;
                isTrueRotated = false;
                isFalseRotated = true;
            /*
            trueBtn.setVisibility(View.INVISIBLE);
            falseBtn.setVisibility(View.INVISIBLE);
            */
            } else {
                questions.setBackgroundResource(R.color.red);
                questions.setTextColor(Color.WHITE);
                score -= 2;
                currentScore.setText("Current score: " + score);
                ObjectAnimator.ofFloat(trueBtn, View.ROTATION, 0f, 90f, 0f,-90f,0f).setDuration(800).start();
                falseBtn.animate().alpha(0f).setDuration(500).start();
                falseBtn.setVisibility(View.INVISIBLE);
                isAnswered = true;
                isTrueRotated = true;
                isFalseRotated = false;
            /*
            trueBtn.setVisibility(View.INVISIBLE);
            falseBtn.setVisibility(View.INVISIBLE);
            */
            }
        }else{
            if(isTrueRotated)
                ObjectAnimator.ofFloat(trueBtn, View.ALPHA, 0f, 1f).setDuration(400).start();
            if(isFalseRotated)
                ObjectAnimator.ofFloat(falseBtn, View.ALPHA, 0f, 1f).setDuration(400).start();
        }
    }

    public void FinalResult(View v){
        Intent username_intent = getIntent();
        String username = username_intent.getStringExtra("UsernameForTriviaGame");
        Intent intent = new Intent(this,TriviaResult.class);
        String hs = String.valueOf(hscore);
        String s = String.valueOf(score);
        intent.putExtra("UsernameForTriviaResult",username);
        intent.putExtra("highScore",hs);
        intent.putExtra("NumberOfQuestions",String.valueOf(Questions.length * 2));
        intent.putExtra("score",s);
        startActivityForResult(intent,5);
    }
}