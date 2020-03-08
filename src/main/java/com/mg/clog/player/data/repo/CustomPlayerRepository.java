package com.mg.clog.player.data.repo;

import com.mg.clog.player.data.model.Player;
import io.reactivex.Maybe;

public interface CustomPlayerRepository {

  Maybe<Player> getFirstGoalKeeper();

}
