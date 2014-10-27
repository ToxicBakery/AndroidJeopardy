package com.doctoredapps.androidjeopardy.model;

import android.database.Observable;

/**
 * Created by MattDupree on 10/26/14.
 */
public class Player extends Observable<Player.OnPlayerScoreChangedListener>{

    private int score;
    private String finalJeopardyAnswer;
    private final String id;

    public Player(String id) {
        this.id = id;
    }

    public void increaseScoreBy(int scoreValue) {
        notifyObserversScoreChanged();
    }

    public void decreaseScoreBy(int scoreValue) {
        notifyObserversScoreChanged();
    }


    private void notifyObserversScoreChanged() {
        for (OnPlayerScoreChangedListener listener : mObservers) {
            listener.onPlayerScoreChanged(id);
        }
    }


    /**
     * Created by MattDupree on 10/26/14.
     */
    public static interface OnPlayerScoreChangedListener {
        public void onPlayerScoreChanged(String playerId);
    }
}
