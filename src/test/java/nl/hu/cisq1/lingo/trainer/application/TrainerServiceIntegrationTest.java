package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidMoveException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
public class TrainerServiceIntegrationTest {

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private WordService wordService;

    @Autowired
    private TrainerService trainerService;

    @Test
    @DisplayName("starting new game starts a new round")
    void StartNewGame() throws InvalidMoveException {

        when(wordService.provideRandomWord(5)).thenReturn("groep");

        GameData gameData = trainerService.startGame();

        assertEquals(5,gameData.getHint().length());
        assertEquals(GameStatus.PLAYING,gameData.getGameStatus());
        assertEquals(0,gameData.getScore());
        assertEquals(0,gameData.getAttempts());
        assertEquals(0,gameData.getFeedbackList().size());
    }

    @Test
    @DisplayName("the player guess a word with length 5")
    void GuessWordWithLength5() throws InvalidMoveException {

        Game game = new Game();

        game.start("groep");

        when(wordService.provideRandomWord(5)).thenReturn("groep");

        when(gameRepository.findById(1L)).thenReturn(java.util.Optional.of(game));

        when(wordService.provideRandomWord(6)).thenReturn("Lambda");

        GameData gameData = trainerService.GuessWord("groep",1L);

        assertEquals(6,gameData.getHint().length());
        assertEquals(GameStatus.PLAYING,gameData.getGameStatus());
        assertEquals(1,gameData.getScore());
        assertEquals(0,gameData.getAttempts());
        assertEquals(0,gameData.getFeedbackList().size());

    }

    @Test
    @DisplayName("the player loses after wrong five attempts")
    void PlayerLosesAfter5Attempts() throws InvalidMoveException {

        Game game = new Game();

        game.start("groep");

        when(wordService.provideRandomWord(5)).thenReturn("groep");

        when(gameRepository.findById(1L)).thenReturn(java.util.Optional.of(game));

        trainerService.GuessWord("gedoe",1L);
        trainerService.GuessWord("gedoe",1L);
        trainerService.GuessWord("gedoe",1L);
        trainerService.GuessWord("gedoe",1L);

        GameData gameData = trainerService.GuessWord("gedoe",1L);

        assertEquals("g....",gameData.getHint());
        assertEquals(GameStatus.LOSE,gameData.getGameStatus());
        assertEquals(0,gameData.getScore());
        assertEquals(5,gameData.getAttempts());
        assertEquals(5,gameData.getFeedbackList().size());

    }


}
