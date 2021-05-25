package com.example.weplay.Playground.Trivia;


public class Questions {
    private String answerId;
    private boolean answerResult;

    public Questions(String answerId,boolean answerResult){
        this.answerId = answerId;
        this.answerResult = answerResult;
    }
    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public boolean isAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(boolean answerResult) {
        this.answerResult = answerResult;
    }
}

