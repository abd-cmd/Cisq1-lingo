package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidMoveException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class TrainerServiceTest {

    @Test
    @DisplayName("starting new game starts a new round")
    void StartNewGame() throws InvalidMoveException {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("groep");

        GameRepository gameRepository = mock(GameRepository.class);

        TrainerService trainerService = new TrainerService(gameRepository,wordService);

        GameData gameData = trainerService.startGame();

        gameData.setAttempts(5);

        assertEquals("g....",gameData.getHint());
        assertEquals(GameStatus.PLAYING,gameData.getGameStatus());
        assertEquals(0,gameData.getScore());
        assertEquals(5,gameData.getAttempts());
        assertEquals(0,gameData.getFeedbackList().size());
    }

    @Test
    @DisplayName("the player guess a word with length 5")
    void GuessWordWithLength5() throws InvalidMoveException {

        Game game = new Game();

        game.start("groep");

        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("groep");

        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.findById(1L)).thenReturn(java.util.Optional.of(game));

        TrainerService trainerService = new TrainerService(gameRepository,wordService);

        when(wordService.provideRandomWord(6)).thenReturn("Lambda");

        GameData gameData2 = trainerService.GuessWord("groep",1L);

        assertEquals("L.....",gameData2.getHint());
        assertEquals(GameStatus.PLAYING,gameData2.getGameStatus());
        assertEquals(1,gameData2.getScore());
        assertEquals(0,gameData2.getAttempts());
        assertEquals(0,gameData2.getFeedbackList().size());

    }


    @Test
    @DisplayName("the player loses after wrong five attempts")
    void PlayerLosesAfter5Attempts() throws InvalidMoveException {

        Game game = new Game();

        game.start("groep");

        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("groep");

        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.findById(1L)).thenReturn(java.util.Optional.of(game));

        TrainerService trainerService = new TrainerService(gameRepository,wordService);

        when(wordService.provideRandomWord(6)).thenReturn("Lambda");

        trainerService.GuessWord("gedoe",1L);
        trainerService.GuessWord("gedoe",1L);
        trainerService.GuessWord("gedoe",1L);
        trainerService.GuessWord("gedoe",1L);

        GameData gameData2 = trainerService.GuessWord("gedoe",1L);


        assertEquals("g....",gameData2.getHint());
        assertEquals(GameStatus.LOSE,gameData2.getGameStatus());
        assertEquals(0,gameData2.getScore());
        assertEquals(5,gameData2.getAttempts());
        assertEquals(5,gameData2.getFeedbackList().size());

    }

}