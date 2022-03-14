package nl.hu.cisq1.lingo.trainer.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Round {
    public static final int MAX_ATTEMPTS = 5;

    @Id
    @GeneratedValue
    private long Id;
    private String Word_To_Guess;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private final List<Feedback> feedbackList = new ArrayList<>();

    public Round(){
    }

    public Round(String word_To_Guess){
        this.Word_To_Guess = word_To_Guess;
    }

    public String getWord_To_Guess() {
        return Word_To_Guess;
    }

    public void guess(String word_To_Guess) {
        Word_To_Guess = word_To_Guess;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public static int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }

    public boolean IsWordGuessed() {
        Feedback feedback = feedbackList.get(feedbackList.size() - 1);
        return feedback.isWordGuessed();
    }

    public boolean CheckAttempts(){
        if (MAX_ATTEMPTS != 0){
            return true;
        }else {
            return false;
        }
    }




}
