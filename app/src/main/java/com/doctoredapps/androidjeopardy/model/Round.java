package com.doctoredapps.androidjeopardy.model;

import android.database.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MattDupree on 10/26/14.
 */
public class Round extends Observable<Round.OnRoundEndedListener> {

    private final ArrayList<Category> categories;
    private final HashMap<Category, Answer[]> answers;

    private Round(ArrayList<Category> categories, HashMap<Category, Answer[]> answers) {
        this.categories = categories;
        this.answers = answers;
    }

    public static Round fromResource() {
        return null;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Answer> getCategoryAnswers(Category category) {
        return null;
    }

    /**
     * Created by MattDupree on 10/26/14.
     */
    static interface OnRoundEndedListener {
        void onRoundEnded();
    }
}
