package com.mg.clog.player.data.repo;

import com.mg.clog.player.data.model.Player;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

public interface PlayerRepository extends ReactiveSortingRepository<Player, String>, CustomPlayerRepository {

  @Query("{ 'club': {'$regex': ?0, '$options': 'i'}}")
  Flux<Player> findByClub(String club);

}
