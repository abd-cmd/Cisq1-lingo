package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    // als het spel met die id niet gevonden kan worden --> null (null pointer exception voorkomen)
    // if (game != null) {
    //  throw new GameNotFoundException;
    // }
    // als wel gevonden --> game

    Game findGameById(Long id);

    // als het spel niet gevonden wordt --> optional.empty()
        // optional.orElse(() -> throw new GameNotFoundException())
    // als wel gevonden --> optional.from(game)
//    Optional<Game> findById(Long id);
}
