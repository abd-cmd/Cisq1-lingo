package nl.hu.cisq1.lingo.trainer.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

@Entity
public class Round {
    public static final int MAX_ATTEMPTS = 5;

    @Id
    @GeneratedValue
    private long id;
    private String wordToGuess;

    private String hint;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private final List<Feedback> feedbackList = new ArrayList<>();

    private int attempts = 0;

    public Round() {
    }

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.hint = createFirstHint(wordToGuess);
    }

    private String createFirstHint(String wordToGuess) {
        // de eerste letter + 4 puntjes
        String hint = String.valueOf(wordToGuess.charAt(0));

        for (int i = 1; i < wordToGuess.length(); i++) {
            hint += ".";
        }

        return hint;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public void guess(String attempt) {
        this.attempts++;

        if (attempt.length() != wordToGuess.length()) {
            Feedback feedback = Feedback.invalid(attempt, wordToGuess);
            this.feedbackList.add(feedback);
            return;
        }

        Feedback feedback = this.giveFeedback(wordToGuess, attempt);
        this.feedbackList.add(feedback);
        this.hint = feedback.giveHint(this.hint, wordToGuess);
    }

    private Feedback giveFeedback(String wordToGuess, String attempt) {
        if (wordToGuess.equals(attempt)) {
            return Feedback.correct(attempt);
        }

        List<Mark> marks = new ArrayList<>();
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == attempt.charAt(i)) {
                marks.add(CORRECT);
            } else if (wordToGuess.contains(String.valueOf(attempt.charAt(i)))) {
                marks.add(PRESENT);
            } else {
                marks.add(ABSENT);
            }
        }

        return new Feedback(attempt, marks);
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public boolean isWordGuessed() {
        Feedback feedback = feedbackList.get(feedbackList.size() - 1);
        return feedback.isWordGuessed();
    }

    public boolean hasNoAttempts() {
        if (this.isWordGuessed()) {
            return false;
        }

        return this.attempts == MAX_ATTEMPTS;
    }

    public String getHint() {
        return hint;
    }

    public int getAttempts() {
        return attempts;
    }
}
