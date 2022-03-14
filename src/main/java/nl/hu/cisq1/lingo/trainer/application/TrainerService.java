package nl.hu.cisq1.lingo.trainer.application;


import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidMoveException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Transactional
@Service
public class TrainerService {
    private GameRepository gameRepository;
    private WordService wordService;

    public TrainerService(GameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    public GameData startGame() throws InvalidMoveException {
        Game game = new Game();

        String word = wordService.provideRandomWord(5);

        game.start(word);

        this.gameRepository.save(game);

        return createGameData(game);
    }

    public GameData GuessWord(String word,Long Id) throws InvalidMoveException {

        Game game = this.gameRepository.findGameById(Id);

        if (game == null) {
            throw new GameNotFoundException();
        }

        String wordToGuess = wordService.provideRandomWord(5);

        game.GuessWord(word,wordToGuess);

        return createGameData(game);
    }


    private GameData createGameData(Game game) {

        Round round = new Round();

        return new GameData(game.getId(), game.getGameStatus(),
                round.getWord_To_Guess(), Round.getMaxAttempts(),
                game.getScore());
    }


}
