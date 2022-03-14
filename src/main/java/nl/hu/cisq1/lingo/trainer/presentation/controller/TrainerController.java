package nl.hu.cisq1.lingo.trainer.presentation.controller;


import nl.hu.cisq1.lingo.trainer.application.GameData;
import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidMoveException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/lingo")
public class TrainerController {

    private TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping("/game")
    public GameData startGame() {
        try {
            return this.trainerService.startGame();
        } catch (InvalidMoveException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/game/{id}/Guess")
    public GameData GuessWord(String Word,@PathVariable Long id) {

        try {
            return this.trainerService.GuessWord(Word,id);
        } catch (InvalidMoveException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
