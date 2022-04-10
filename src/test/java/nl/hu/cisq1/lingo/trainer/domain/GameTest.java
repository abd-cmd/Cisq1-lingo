package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidMoveException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    @DisplayName("cannot guess word because gameStatus is not playing")
    void CannotGuessWord() throws InvalidMoveException {

        Game game = new Game();

        String Word = "chris";

        game.start(Word);

        GameStatus gameStatus = GameStatus.WAITING_FOR_ROUND;

        game.setGameStatus(gameStatus);

        assertNotEquals(game.getGameStatus(),GameStatus.PLAYING);
    }

    @Test
    @DisplayName("can guess word because gameStatus is playing")
    void CanGuessWord() throws InvalidMoveException {

        Game game = new Game();

        String Word = "chris";

        game.start(Word);

        assertEquals(game.getGameStatus(),GameStatus.PLAYING);
    }

    @Test
    @DisplayName("The gamestatus is lose because the player has lost")
    void PlayerLost() throws InvalidMoveException {

        Game game = new Game();

        String Word = "chris";
        String nextWord = "chamea" ;

        Round round = new Round(Word);

        game.start(round.getWordToGuess());

        game.guessWord(nextWord,"chari");
        game.guessWord(nextWord,"chari");
        game.guessWord(nextWord,"chari");
        game.guessWord(nextWord,"chari");
        game.guessWord(nextWord,"chari");

        assertEquals(game.getGameStatus(),GameStatus.LOSE);
    }


    @Test
    @DisplayName("The gamestatus is playing because the player has not lost")
    void PlayerHasNotLost() throws InvalidMoveException {

        Game game = new Game();

        String Word = "chris";
        String nextWord = "chamea" ;

        Round round = new Round(Word);

        game.start(round.getWordToGuess());

        game.guessWord(nextWord,Word);

        assertEquals(game.getGameStatus(),GameStatus.PLAYING);
    }

    @Test
    @DisplayName("The current attempts is four after four guess")
    void CurrentAttempts() throws InvalidMoveException {
        Game game = new Game();

        String Word = "chris";
        String nextWord = "chamea" ;

        Round round = new Round(Word);

        game.start(round.getWordToGuess());

        game.guessWord(nextWord,"chaea");
        game.guessWord(nextWord,"chaea");
        game.guessWord(nextWord,"chaea");
        game.guessWord(nextWord,"chaea");

        assertEquals(4,game.getCurrentAttempts());
    }

    @Test
    @DisplayName("The next length is 6 after 5")
    void NextLengthIs6() throws InvalidMoveException {
        Game game = new Game();

        String Word = "chris";

        Round round = new Round(Word);

        game.start(round.getWordToGuess());

        assertEquals(6,game.getNextLength());
    }

    @Test
    @DisplayName("The next length is 7 after 6")
    void NextLengthIs7() throws InvalidMoveException {
        Game game = new Game();

        String Word = "chris";
        String NextWord = "chriss";

        Round round = new Round(Word);

        game.start(round.getWordToGuess());

        game.guessWord(NextWord,round.getWordToGuess());

        assertEquals(7,game.getNextLength());
    }

    @Test
    @DisplayName("The next length is 5 after 7")
    void NextLengthIs5() throws InvalidMoveException {
        Game game = new Game();

        String Word = "chriss";
        String NextWord = "chrisse";

        Round round = new Round(Word);

        game.start(round.getWordToGuess());

        game.guessWord(NextWord,round.getWordToGuess());

        assertEquals(5,game.getNextLength());
    }

    @Test
    @DisplayName("the current feedback is not known because it is the beginning of the game")
    void CurrentFeedbackBeforeGuessWord() throws InvalidMoveException {
        Game game = new Game();

        String Word = "chriss";

        Round round = new Round(Word);

        game.start(round.getWordToGuess());

        assertTrue(game.getCurrentFeedback().isEmpty());

    }

    @Test
    @DisplayName("the current feedback is not empty")
    void CurrentFeedbackAfterGuessWord() throws InvalidMoveException {
        Game game = new Game();

        String Word = "chriss";
        String NextWord = "chrisse";

        Round round = new Round(Word);

        game.start(round.getWordToGuess());

        game.guessWord(NextWord,"cheam");

        assertFalse(game.getCurrentFeedback().isEmpty());

    }

    @Test
    @DisplayName("The score is increased if the word is guessed")
    void ScoreIsIncreased() throws InvalidMoveException {
        Game game = new Game();

        String Word = "chriss";
        String NextWord = "chrisse";

        Round round = new Round(Word);


        game.start(round.getWordToGuess());

        game.guessWord(NextWord,Word);

        assertEquals(1,game.getScore());

    }

    @Test
    @DisplayName("The score is ScoreIsDecreased if the word is not guessed")
    void ScoreIsDecreased() throws InvalidMoveException {
        Game game = new Game();

        String Word = "chriss";
        String NextWord = "chrisse";

        Round round = new Round(Word);

        game.start(round.getWordToGuess());

        game.setScore(10);

        game.guessWord(NextWord,"crame");
        game.guessWord(NextWord,"crame");
        game.guessWord(NextWord,"crame");
        game.guessWord(NextWord,"crame");
        game.guessWord(NextWord,"crame");

        assertEquals(9,game.getScore());
    }


    @Test
    @DisplayName("The score is zero even if the player lost the first round")
    void ScoreIs0() throws InvalidMoveException {
        Game game = new Game();

        String Word = "chriss";
        String NextWord = "chrisse";

        Round round = new Round(Word);

        game.start(round.getWordToGuess());

        game.guessWord(NextWord,"crame");
        game.guessWord(NextWord,"crame");
        game.guessWord(NextWord,"crame");
        game.guessWord(NextWord,"crame");
        game.guessWord(NextWord,"crame");

        assertEquals(0,game.getScore());
    }



}