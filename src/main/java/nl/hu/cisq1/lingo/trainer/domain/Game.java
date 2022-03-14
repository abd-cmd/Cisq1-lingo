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

    private Integer Score = 0;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private final List<Round> rounds = new ArrayList<>();

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Integer getScore() {
        return Score;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public Long getId() {
        return Id;
    }

    public void start(String word) throws InvalidMoveException {
        if (gameStatus != GameStatus.WAITING_FOR_ROUND) {
            throw new InvalidMoveException("Move is not allowed, game has already been started.");
        }

        Round round = new Round(word);

        this.gameStatus = GameStatus.PLAYING;

        this.Score = 0;


    }

    public void GuessWord(String word, String wordToGuess) throws InvalidMoveException {
        if (this.hasEnded()) {
            throw new InvalidMoveException("Move is not allowed, game is not playing.");
        }

        if (!hasEnded()){
            Round round = new Round(wordToGuess);
            round.guess(word);
            this.gameStatus = GameStatus.PLAYING;
            this.Score = Score + 1;

        }else {
            this.gameStatus = GameStatus.LOSE;

        }
    }

    public boolean IsWordGuessed(){
        Round lastRound = rounds.get(rounds.size()-1);
        return lastRound.IsWordGuessed();
    }

    public List<Feedback> getFeedbackList(){
        Round lastRound = rounds.get(rounds.size()-1);
        return lastRound.getFeedbackList();
    }

    public boolean hasEnded() {
        Round lastRound = rounds.get(rounds.size()-1);
        return lastRound.CheckAttempts();
    }

//    public int nextLength(){
//
//    }
    
}
