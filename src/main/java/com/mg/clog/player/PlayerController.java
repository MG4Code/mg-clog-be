package com.mg.clog.player;

import com.mg.clog.player.data.model.Player;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/player")
public class PlayerController {

  private final PlayerService service;

  public PlayerController(PlayerService service) {
    this.service = service;
  }

  @GetMapping
  public Flux<Player> list() {
    return service.list();
  }

  @GetMapping("id")
  public Mono<Player> findPlayer(@RequestParam("q") String id) {
    return service.findPlayerById(id);
  }

  @GetMapping("club")
  public Flux<Player> findClub(@RequestParam("q") String query) {
    return service.findAllForClub(query);
  }

  @PostMapping
  public Mono<Player> add(@RequestBody Player player) {
    return service.add(player);
  }

  @PutMapping("/{id}")
  public Mono<Player> put(@PathVariable("id") String id, @RequestBody Player player) {
    return service.put(id, player);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> delete(@PathVariable("id") String id) {
    return service.delete(id);
  }

  @GetMapping("goalkeeper")
  public Mono<Player> getFirstGoalKeeper() {
    return service.getFirstGoalKeeper();
  }

}
