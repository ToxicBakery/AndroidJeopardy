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
    private int currentRoundIndex;

    private PlayerControlManager playerControlManager;

    Game(Round[] rounds, HashMap<String, Player> players) {
        this.rounds = rounds;
        this.players = players;
        this.currentRound = rounds[currentRoundIndex];
    }

    public void incrementPlayerScore(String playerId) {
        players.get(playerId).increaseScoreBy(currentRound.getCurrentAnswer().getScoreValue());
    }

    public void decrementPlayerScore(String playerId) {
        players.get(playerId).decreaseScoreBy(currentRound.getCurrentAnswer().getScoreValue());
    }

    public void givePlayerControl(String playerId) {
        playerControlManager.notifyPlayerControlChanged(playerId);
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


    private class PlayerControlManager extends Observable<OnPlayerControlChanged>{
        private void notifyPlayerControlChanged(String playerId) {
            for (OnPlayerControlChanged listener : mObservers) {
                listener.onPlayerControlChanged(playerId);
            }
        }
    }

    /**
     * Created by MattDupree on 10/26/14.
     */
    public static interface OnPlayerControlChanged {
        public void onPlayerControlChanged(String playerId);
    }
}
