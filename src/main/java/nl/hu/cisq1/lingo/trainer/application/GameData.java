package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

import java.util.List;

public class GameData {
    private Long id;
    private GameStatus gameStatus;
    private String hint;
    private List<Feedback> feedbackList;
    private int attempts;
    private int score;

    public GameData(Long id, GameStatus gameStatus, String hint, List<Feedback> feedbackList, int attempts, int score) {
        this.id = id;
        this.gameStatus = gameStatus;
        this.hint = hint;
        this.feedbackList = feedbackList;
        this.attempts = attempts;
        this.score = score;
    }
    public Long getId() {
        return id;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public String getHint() {
        return hint;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getScore() {
        return score;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
