package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidMoveException;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue
    private Long Id;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus = GameStatus.WAITING_FOR_ROUND;

    private Integer score = 0;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private final List<Round> rounds = new ArrayList<>();

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Integer getScore() {
        return score;
    }

    public Long getId() {
        return Id;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void start(String word) throws InvalidMoveException {
        if (gameStatus != GameStatus.WAITING_FOR_ROUND) {
            throw new InvalidMoveException("Move is not allowed, game has already been started.");
        }

        this.gameStatus = GameStatus.PLAYING;
        this.score = 0;

        Round round = new Round(word);
        this.rounds.add(round);
    }

    public void guessWord(String NextWord, String guess) throws InvalidMoveException {
        if (gameStatus != GameStatus.PLAYING) {
            throw new InvalidMoveException("Move is not allowed, game is not playing.");
        }

        Round lastRound = rounds.get(rounds.size() - 1);

        lastRound.guess(guess);

        if (hasPlayerLost()) {
            this.gameStatus = GameStatus.LOSE;

            if (this.score == 0){
                this.setScore(0);
            }else {
                this.score = score - 1;
            }

        } else if (lastRound.isWordGuessed()) {

            Round round = new Round(NextWord);
            this.rounds.add(round);
            this.score = score + 1;
            this.gameStatus = GameStatus.PLAYING;
        }
    }

    public boolean hasPlayerLost() {
        Round lastRound = rounds.get(rounds.size() - 1);
        return lastRound.hasNoAttempts();
    }

    public List<Feedback> getCurrentFeedback() {
        Round lastRound = rounds.get(rounds.size() - 1);
        return lastRound.getFeedbackList();
    }

    public String getCurrentHint() {
        Round lastRound = rounds.get(rounds.size() - 1);
        return lastRound.getHint();
    }

    public int getCurrentAttempts() {
        Round lastRound = rounds.get(rounds.size() - 1);
        return lastRound.getAttempts();
    }

    public Integer getNextLength() {
        int nextLength = 5;
        Round lastRound = rounds.get(rounds.size() - 1);
        if (lastRound.getWordToGuess().length() == 5) {
            nextLength = nextLength + 1;
        }
        if (lastRound.getWordToGuess().length() == 6) {
            nextLength = nextLength + 2;
        }
        return nextLength;
    }
}
