package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
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

        System.out.println(word);

        game.start(word);

        this.gameRepository.save(game);

        return createGameData(game);
    }

    public GameData GuessWord(String guess, Long id) throws InvalidMoveException {

        Game game = this.gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException());

        if (game == null) {
            throw new GameNotFoundException();
        }

        String nextword = wordService.provideRandomWord(game.getNextLength());

        System.out.println(nextword);

        game.guessWord(nextword, guess);

        return createGameData(game);
    }

    private GameData createGameData(Game game) {
        return new GameData(
                game.getId(),
                game.getGameStatus(),
                game.getCurrentHint(),
                game.getCurrentFeedback(),
                game.getCurrentAttempts(),
                game.getScore()
        );
    }
}
