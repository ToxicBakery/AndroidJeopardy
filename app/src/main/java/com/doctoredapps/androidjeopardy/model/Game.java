package com.doctoredapps.androidjeopardy.model;

import android.database.Observable;

import java.util.HashMap;

/**
 * Created by MattDupree on 10/26/14.
 */
public class Game implements Round.OnRoundEndedListener{

    private final Round[] rounds;
    private final HashMap<String, Player> players;

    private Round currentRound;
    private Answer currentAnswer;

    private ScoreKeeper scoreManager;
    private PlayerControlKeeper playerControlManager;

    public Game(Round[] rounds, HashMap<String, Player> players) {
        this.rounds = rounds;
        this.players = players;
        this.currentRound = rounds[0];
    }

    public void incrementPlayerScore(String playerId) {
        scoreManager.notifyPlayerScoreChanged(playerId);
    }

    public void decrementPlayerScore(String playerId) {
        scoreManager.notifyPlayerScoreChanged(playerId);
    }

    public void givePlayerControl(String playerId) {
        playerControlManager.notifyPlayerControlChanged(playerId);
    }





    public void registerOnScoreChangedListner(OnPlayerScoreChangedListener listener) {
        scoreManager.registerObserver(listener);
    }

    public void unRegisterOnScoreChangedListner(OnPlayerScoreChangedListener listener) {
        scoreManager.unregisterObserver(listener);
    }

    public void registerOnPlayerControlChangedListner(OnPlayerControlChanged listener) {
        playerControlManager.registerObserver(listener);
    }

    public void unRegisterOnPlayerControlChangedListner(OnPlayerControlChanged listener) {
        playerControlManager.unregisterObserver(listener);
    }

    @Override
    public void onRoundEnded() {
        moveToNextRound();
    }

    private void moveToNextRound() {

    }


    private class ScoreKeeper extends Observable<OnPlayerScoreChangedListener>{
        private void notifyPlayerScoreChanged(String playerId) {
            for (OnPlayerScoreChangedListener listener : mObservers) {
                listener.onPlayerScoreChanged(playerId);
            }
        }
    }

    private class PlayerControlKeeper extends Observable<OnPlayerControlChanged>{
        private void notifyPlayerControlChanged(String playerId) {
            for (OnPlayerControlChanged listener : mObservers) {
                listener.onPlayerControlChanged(playerId);
            }
        }
    }


    /**
     * Created by MattDupree on 10/26/14.
     */
    public static interface OnPlayerScoreChangedListener {
        public void onPlayerScoreChanged(String playerId);
    }

    /**
     * Created by MattDupree on 10/26/14.
     */
    public static interface OnPlayerControlChanged {
        public void onPlayerControlChanged(String playerId);
    }
}
