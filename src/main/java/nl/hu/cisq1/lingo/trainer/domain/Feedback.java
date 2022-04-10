package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.data.MarksConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Feedback {

    @Id
    @GeneratedValue
    private Long Id;
    private String attempt;

    @Convert(converter = MarksConverter.class)
    private List<Mark> markList = new ArrayList<>();

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.markList = marks;
    }

    public Feedback() {
    }

    public static Feedback invalid(String attempt, String wordToGuess) {
        List<Mark> marks = new ArrayList<>();

        for (int i = 0; i < wordToGuess.length(); i++) {
            marks.add(Mark.INVALID);
        }

        return new Feedback(attempt, marks);
    }

    public static Feedback correct(String attempt) {
        List<Mark> marks = new ArrayList<>();

        for (int i = 0; i < attempt.length(); i++) {
            marks.add(Mark.CORRECT);
        }

        return new Feedback(attempt, marks);
    }

    public String giveHint(String previousHint, String wordToGuess) {
        StringBuilder nextHint = new StringBuilder();

        for (int i = 0; i < markList.size(); i++) {
            if (markList.get(i).equals(Mark.CORRECT)) {
                nextHint.append(wordToGuess.charAt(i));
            } else {
                nextHint.append(previousHint.charAt(i));
            }
        }

        return nextHint.toString();
    }

    public boolean isWordGuessed() {
        return this.markList.stream().allMatch(Mark.CORRECT::equals);
    }

    public boolean isAttemptInvalid() {
        return this.markList.stream().anyMatch(Mark.INVALID::equals);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(markList, feedback.markList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, markList);
    }

    public List<Mark> getMarkList() {
        return this.markList;
    }
}
