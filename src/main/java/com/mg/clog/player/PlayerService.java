package com.mg.clog.player;

import com.mg.clog.player.data.model.Player;
import com.mg.clog.player.data.repo.PlayerRepository;
import com.mg.clog.player.data.repo.PlayerRepository;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;

@Service
public class PlayerService {

  private Logger logger = LoggerFactory.getLogger(PlayerService.class);
  private final PlayerRepository repo;

  @Autowired
  public PlayerService(PlayerRepository repo) {
    this.repo = repo;
  }

  public Flowable<Player> list() {
    return repo.findAll()
      .doOnNext(e -> logger.info(e.toString()));
  }

  public Single<Player> add(Player player) {
    return repo.save(player);
  }

  public Single<Player> put(String id, Player player) {
    return repo.findById(id)
      .switchIfEmpty(Single.error(new WebApplicationException("No player found with id '" + id + "'", 404)))
      .map(byId -> {
        if (player.getFirstName() != null) {
          byId.setFirstName(player.getFirstName());
        }
        if (player.getLastName() != null) {
          byId.setLastName(player.getLastName());
        }
        if (player.getClub() != null) {
          byId.setClub(player.getClub());
        }
        if (player.getPosition() != null) {
          byId.setPosition(player.getPosition());
        }
        if (player.getShirtNumber() != null) {
          byId.setShirtNumber(player.getShirtNumber());
        }
        return byId;
      }).flatMap(repo::save);
  }

  public Completable delete(String id) {
    return repo.deleteById(id);
  }

  public Flowable<Player> findAllForClub(String query) {
    return repo.findByClub(query);
  }

  public Maybe<Player> findPlayerById(String id) {
    return repo.findById(id);
  }

  public Maybe<Player> getFirstGoalKeeper() {
    return repo.getFirstGoalKeeper();
  }
}
