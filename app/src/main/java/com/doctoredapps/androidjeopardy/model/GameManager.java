package com.doctoredapps.androidjeopardy.model;

/**
 * Created by MattDupree on 10/26/14.
 */
public class GameManager {


    private static GameManager ourInstance;

    private Game currentGame;

    public static GameManager getInstance() {
        if (ourInstance == null)
            ourInstance = new GameManager();

        return ourInstance;
    }

    private GameManager() {
    }


    public void startGame() {
        currentGame = new Game(null, null);
    }

    public void endGame() {
        currentGame = null;
    }

    public Game getCurrentGame() {
        return currentGame;
    }





}
