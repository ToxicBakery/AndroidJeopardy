package com.doctoredapps.androidjeopardy.model;

/**
 * Created by MattDupree on 10/26/14.
 */
public class Answer {

    private final int answerResId;
    private final Category category;
    private final int scoreValue;

    public Answer(int answerResId, Category category, int scoreValue) {
        this.answerResId = answerResId;
        this.category = category;
        this.scoreValue = scoreValue;
    }

    public int getScoreValue() {
        return scoreValue;
    }
}
