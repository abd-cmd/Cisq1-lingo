package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @ParameterizedTest
    @MethodSource("guessRightExamples")
    @DisplayName("gives correct feedback when word is guessed")
    void correctFeedback(String wordToGuess, String attempt, List<Mark> expectedMarks) {
        // Given
        Round round = new Round(wordToGuess);

        // When
        round.guess(attempt);
        Feedback feedback = round.getFeedbackList().get(0);

        // Then
        assertEquals(expectedMarks, feedback.getMarkList());
    }

    static List<Arguments> guessRightExamples() {

        return List.of(
                Arguments.of("groep", "groep", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT)),
                Arguments.of("groep", "gaten", List.of(CORRECT, ABSENT, ABSENT, CORRECT, ABSENT))
        );
    }

    @ParameterizedTest
    @MethodSource("guessWrongExamples")
    @DisplayName("gives wrong feedback when word not is guessed")
    void WrongFeedback(String wordToGuess, String attempt, List<Mark> expectedMarks) {
        // Given
        Round round = new Round(wordToGuess);

        // When
        round.guess(attempt);
        Feedback feedback = round.getFeedbackList().get(0);

        // Then
        assertNotEquals(expectedMarks, feedback.getMarkList());
    }

    static List<Arguments> guessWrongExamples() {

        return List.of(
                Arguments.of("groep", "groep", List.of(CORRECT, ABSENT, CORRECT, PRESENT, CORRECT)),
                Arguments.of("groep", "gaten", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT))
        );
    }

    @Test
    @DisplayName("check if the first hint is being created")
    void FirstHintIsCreated(){
        String word = "heden";

        Round round = new Round(word);

        assertEquals("h....",round.getHint());

    }

    @Test
    @DisplayName("check if the first hint is not being created")
    void FirstHintIsNotCreated(){
        String word = "heden";

        Round round = new Round();

        assertNull(round.getHint());
    }

    @Test
    @DisplayName("Check if the attempts are increased after 1 or 2 guesses")
    void AtemptsAreIncreased(){

        String word = "heden";

        String word2 = "halen";

        Round round = new Round(word);

        round.guess(word2);

        assertEquals(1,round.getAttempts());

        round.guess(word2);

        assertEquals(2,round.getAttempts());
    }

    @Test
    @DisplayName("Check if the attempts are not increased if there is no guess")
    void AtemptsAreNotIncreased(){

        String word = "heden";

        Round round = new Round(word);

        assertEquals(0,round.getAttempts());
    }

    @Test
    @DisplayName("Check if player has no attempts ")
    void PlayerHasNoAttempts(){
        String word = "heden";

        String word2 = "halen";

        Round round = new Round(word);

        round.guess(word2);
        round.guess(word2);
        round.guess(word2);
        round.guess(word2);
        round.guess(word2);

        assertTrue(round.hasNoAttempts());
    }

    @Test
    @DisplayName("Check if player has no attempts ")
    void PlayerHasAttempts(){
        String word = "heden";

        String word2 = "halen";

        Round round = new Round(word);

        round.guess(word2);
        round.guess(word2);
        round.guess(word2);
        round.guess(word2);

        assertFalse(round.hasNoAttempts());
    }

    @Test
    @DisplayName("Check if word is guessed ")
    void WordIsGuessed(){
        String word = "heden";

        String word2 = "halen";

        Round round = new Round(word);

        round.guess(word2);
        round.guess(word2);
        round.guess(word2);
        round.guess(word2);
        round.guess(word);

        assertTrue(round.isWordGuessed());
    }

    @Test
    @DisplayName("Check if word is not guessed ")
    void WordIsNotGuessed(){
        String word = "heden";

        String word2 = "halen";

        Round round = new Round(word);

        round.guess(word2);
        round.guess(word2);
        round.guess(word2);
        round.guess(word2);
        round.guess(word2);

        assertFalse(round.isWordGuessed());
    }


}