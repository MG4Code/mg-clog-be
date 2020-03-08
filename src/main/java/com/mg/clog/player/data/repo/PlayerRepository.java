package com.mg.clog.player.data.repo;

import com.mg.clog.player.data.model.Player;
import io.reactivex.Flowable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.RxJava2SortingRepository;

public interface PlayerRepository extends RxJava2SortingRepository<Player, String>, CustomPlayerRepository {

  @Query("{ 'club': {'$regex': ?0, '$options': 'i'}}")
  Flowable<Player> findByClub(String club);

}
