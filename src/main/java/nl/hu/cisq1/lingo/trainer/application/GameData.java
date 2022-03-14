package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

public class GameData {
    private Long id;
    private GameStatus gameStatus;
    private String WordToGuess;
    private int MAX_ATTEMPTS;
    private int score;

    public GameData(Long id, GameStatus gameStatus, String wordToGuess, int MAX_ATTEMPTS , int score) {
        this.id = id;
        this.gameStatus = gameStatus;
        WordToGuess = wordToGuess;
        this.MAX_ATTEMPTS = MAX_ATTEMPTS;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getWordToGuess() {
        return WordToGuess;
    }

    public void setWordToGuess(String wordToGuess) {
        WordToGuess = wordToGuess;
    }

    public int getMAX_ATTEMPTS() {
        return MAX_ATTEMPTS;
    }

    public void setMAX_ATTEMPTS(int MAX_ATTEMPTS) {
        this.MAX_ATTEMPTS = MAX_ATTEMPTS;
    }

    public int getScore() {
        return score;
    }
}
