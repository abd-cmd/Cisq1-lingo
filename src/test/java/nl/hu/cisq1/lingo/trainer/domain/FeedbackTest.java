package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;


import java.util.List;
import java.util.stream.Stream;


class FeedbackTest {

        private static List<Arguments> HintExamples() {
            return List.of(
                    Arguments.of("g....","groep","genen",List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.ABSENT)),
                    Arguments.of("g....","groep","gegroet",List.of(Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID)),
                    Arguments.of("g..e.","groep","gedoe",List.of(Mark.CORRECT,Mark.PRESENT,Mark.ABSENT,Mark.PRESENT,Mark.ABSENT) )
            );
        }

        @Test
        @DisplayName("word is guessed if all letters are correct")
        void wordIsGuessed(){
            //Given
            String attempt = "woord";

            List<Mark> marks = List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT);

            //when
            Feedback feedback = new Feedback(attempt,marks);

            //then
            assertTrue(feedback.isWordGuessed());
        }

        @Test
        @DisplayName("word is not guessed if all letters are not correct")
        void wordIsNotGuessed(){
            String attempt = "woord";
            List<Mark> marks = List.of(Mark.CORRECT,Mark.PRESENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT);

            Feedback feedback = new Feedback(attempt,marks);

            assertFalse(feedback.isWordGuessed());
        }

        @Test
        @DisplayName("attempt is not invalid if no letters are marked invalid")
        void attemptIsNotValid(){
            String attempt = "woord";
            List<Mark> marks = List.of(Mark.CORRECT,Mark.PRESENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT);

            Feedback feedback = new Feedback(attempt,marks);

            assertFalse(feedback.isAttemptInvalid());
        }

        @Test
        @DisplayName("attempt is not invalid if no letters are marked invalid")
        void attemptIsValid(){
            String attempt = "woorden";
            String wordToGuess = "woord";

            Feedback feedback = Feedback.invalid(attempt,wordToGuess);

            assertTrue(feedback.isAttemptInvalid());
        }

        @Test
        @DisplayName("equal to another feedback")
        void equals(){
            String attempt = "woord";
            List<Mark> marksA = List.of(Mark.CORRECT,Mark.PRESENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT);
            List<Mark> marksB = List.of(Mark.CORRECT,Mark.PRESENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT);

            Feedback feedbackA = new Feedback(attempt,marksA);
            Feedback feedbackB = new Feedback(attempt,marksB);

            assertEquals(feedbackA,feedbackB);
            assertEquals(feedbackA.hashCode(),feedbackB.hashCode());
        }

        @Test
        @DisplayName("Not equal to another feedback")
        void Notequals(){
            String attempt = "woord";
            List<Mark> marksA = List.of(Mark.CORRECT,Mark.PRESENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT);
            List<Mark> marksB = List.of(Mark.PRESENT,Mark.PRESENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT);

            Feedback feedbackA = new Feedback(attempt,marksA);
            Feedback feedbackB = new Feedback(attempt,marksB);

            assertNotEquals(feedbackA,feedbackB);
            assertNotEquals(feedbackA.hashCode(),feedbackB.hashCode());
        }

        @ParameterizedTest
        @MethodSource("HintExamples")
        @DisplayName("give hint based on previous hint and word to guess")
        void giveHint(String previousHint,String wordToGuess,String attempt, List<Mark> marks,String expectedHint){
            Feedback feedback = new Feedback(attempt,marks);
            assertEquals(expectedHint, feedback.giveHint(previousHint,wordToGuess) );
        }


}



//        @Test
//        @DisplayName("attempt is not invalid if no letters are marked invalid")
//        void attemptIsValid(){
//            //Given
//            String attempt = "woord";
//
//            List<Mark> marks = List.of(Mark.CORRECT,Mark.PRESENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT);
//
//            //when
//            Feedback feedback = new Feedback(attempt,marks);
//
//            //then
//            assertFalse(feedback.isAttemptInvalid());
//        }