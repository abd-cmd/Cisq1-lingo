package nl.hu.cisq1.lingo.trainer.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.presentation.dto.Guess;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TrainerControllerTest {

    @MockBean
    private WordService wordService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("start new game")
    void StartNewGame() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/lingo/game");

        when(wordService.provideRandomWord(5)).thenReturn("groep");

        mockMvc.perform(requestBuilder)
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
    void guessWord() throws Exception {

        Game game = new Game();

        game.start("groep");

        RequestBuilder newGameRequest = MockMvcRequestBuilders.post("/lingo/game");

        MockHttpServletResponse response = mockMvc.perform(newGameRequest).andReturn().getResponse();

        Integer gameId = JsonPath.read(response.getContentAsString(), "$.id");

        when(wordService.provideRandomWord(5)).thenReturn("groep");

        when(wordService.provideRandomWord(6)).thenReturn("lambda");

        Guess guess = new Guess();

        guess.Word = "groep";

        String guessBody = new ObjectMapper().writeValueAsString(guess);

        RequestBuilder guessReq = MockMvcRequestBuilders.
                post("/lingo/game" + gameId + "/guess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(guessBody);

        mockMvc.perform(guessReq)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",greaterThanOrEqualTo(0)))
                .andExpect( jsonPath("$.gameStatus",is("PLAYING")))
                .andExpect(jsonPath("$.hint",is("g....")))
                .andExpect(jsonPath("$.feedbackList",hasSize(5)))
                .andExpect( jsonPath("$.attempts",is(0)))
                .andExpect( jsonPath("$.score",is(1)));

    }
}