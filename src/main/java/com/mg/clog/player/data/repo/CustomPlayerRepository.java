package com.mg.clog.player.data.repo;

import com.mg.clog.player.data.model.Player;
import reactor.core.publisher.Mono;

public interface CustomPlayerRepository {

  Mono<Player> getFirstGoalKeeper();

}
