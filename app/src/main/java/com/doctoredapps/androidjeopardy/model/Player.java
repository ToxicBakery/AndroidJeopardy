package com.doctoredapps.androidjeopardy.model;

import android.database.Observable;

/**
 * Created by MattDupree on 10/26/14.
 */
public class Player {

    private int score;
    private String finalJeopardyAnswer;
    private final String id;
    private boolean isInControl;

    private PlayerControlObservable playerControlObservable;
    private PlayerScoreObservable playerScoreObservable;

    public Player(String id) {
        this.id = id;
        playerControlObservable = new PlayerControlObservable();
        playerScoreObservable = new PlayerScoreObservable();
    }

    void increaseScoreBy(int scoreValue) {
        score += scoreValue;
        playerScoreObservable.notifyObserversScoreChanged(id);
    }

    void decreaseScoreBy(int scoreValue) {
        score -= scoreValue;
        playerScoreObservable.notifyObserversScoreChanged(id);
    }

    void setInControl(boolean inControl) {
        isInControl = inControl;
    }

    public void registerOnPlayerScoreChangedListener(OnPlayerScoreChangedListener listener) {
        playerScoreObservable.registerObserver(listener);
    }

    public void unRegisterOnPlayerScoreChangedListener(OnPlayerScoreChangedListener listener) {
        playerScoreObservable.unregisterObserver(listener);
    }


    public void registerOnPlayerControlChangedListner(OnPlayerControlChanged listener) {
        playerControlObservable.registerObserver(listener);
    }

    public void unRegisterOnPlayerControlChangedListner(OnPlayerControlChanged listener) {
        playerControlObservable.unregisterObserver(listener);
    }



    private static class PlayerControlObservable extends Observable<OnPlayerControlChanged>{
        private void notifyPlayerControlChanged(String playerId) {
            for (OnPlayerControlChanged listener : mObservers) {
                listener.onPlayerControlChanged(playerId);
            }
        }
    }

    private static class PlayerScoreObservable extends Observable<OnPlayerScoreChangedListener>{
        private void notifyObserversScoreChanged(String playerId) {
            for (OnPlayerScoreChangedListener listener : mObservers) {
                listener.onPlayerScoreChanged(playerId);
            }
        }
    }



    /**
     * Created by MattDupree on 10/26/14.
     */
    public static interface OnPlayerControlChanged {
        public void onPlayerControlChanged(String playerId);
    }


    /**
     * Created by MattDupree on 10/26/14.
     */
    public static interface OnPlayerScoreChangedListener {
        public void onPlayerScoreChanged(String playerId);
    }

}
