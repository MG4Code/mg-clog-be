package com.mg.clog.player;

import com.mg.clog.player.data.model.Player;
import com.mg.clog.player.data.repo.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class PlayerService {

  private Logger logger = LoggerFactory.getLogger(PlayerService.class);
  private final PlayerRepository repo;

  public PlayerService(PlayerRepository repo) {
    this.repo = repo;
  }

  public Flux<Player> list() {
    return repo.findAll()
      .doOnNext(e -> logger.info(e.toString()));
  }

  public Mono<Player> add(Player player) {
    return repo.save(player);
  }

  public Mono<Player> put(String id, Player player) {
    return repo.findById(id)
      .switchIfEmpty(Mono.error(new IllegalArgumentException("No player found with id '" + id + "'")))
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

  public Mono<Void> delete(String id) {
    return repo.deleteById(id);
  }

  public Flux<Player> findAllForClub(String query) {
    return repo.findByClub(query);
  }

  public Mono<Player> findPlayerById(String id) {
    return repo.findById(id);
  }

  public Mono<Player> getFirstGoalKeeper() {
    return repo.getFirstGoalKeeper();
  }
}
