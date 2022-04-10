package nl.hu.cisq1.lingo.trainer.presentation.controller;

import nl.hu.cisq1.lingo.trainer.application.GameData;
import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class TrainerControllerIntegrationTest {
        @Autowired
        private GameRepository gameRepository;

        @MockBean
        private WordService wordService;

        @Autowired
        private MockMvc mockMvc;

        @Test
        @DisplayName("starting new game starts a new round")
        void StartGame() throws Exception {
            when(wordService.provideRandomWord(5)).thenReturn("groep");

            RequestBuilder newGameRequest = MockMvcRequestBuilders.post("/lingo/game")
                    .contentType(MediaType.APPLICATION_JSON);

            when(wordService.provideRandomWord(6)).thenReturn("lambda");

            mockMvc.perform(newGameRequest)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id",greaterThanOrEqualTo(0)))
                    .andExpect( jsonPath("$.gameStatus",is("PLAYING")))
                    .andExpect(jsonPath("$.hint",is("g....")))
                    .andExpect(jsonPath("$.feedbackList",hasSize(0)))
                    .andExpect( jsonPath("$.attempts",is(0)))
                    .andExpect( jsonPath("$.score",is(0)));
        }

        @Test
        @DisplayName("guess a word after starting a game")
        void GuessWord() throws Exception {

            when(wordService.provideRandomWord(5)).thenReturn("groep");

            Game game = new Game();

            game.start("groep");

            when(gameRepository.findById(1L)).thenReturn(java.util.Optional.of(game));

            TrainerService trainerService = new TrainerService(gameRepository,wordService);

            GameData gameData =  trainerService.startGame();

            RequestBuilder guessReq = MockMvcRequestBuilders.
                    post("/lingo/game/"+ gameData.getId()+"/guess");

            mockMvc.perform(guessReq)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id",greaterThanOrEqualTo(0)))
                    .andExpect(jsonPath("$.gameStatus",is("PLAYING")))
                    .andExpect(jsonPath("$.hint",is("g....")))
                    .andExpect(jsonPath("$.feedbackList",hasSize(0)))
                    .andExpect(jsonPath("$.attempts",is(0)))
                    .andExpect(jsonPath("$.score",is(0)));
        }
}
