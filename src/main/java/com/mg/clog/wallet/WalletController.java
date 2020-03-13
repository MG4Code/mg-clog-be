package com.mg.clog.wallet;

import com.mg.clog.wallet.data.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/wallet")
public class WalletController {

  private final WalletService service;

  @Autowired
  public WalletController(WalletService service) {
    this.service = service;
  }

  @GetMapping
  public Flux<Wallet> list() {
    return service.list();
  }

  @GetMapping("id")
  public Mono<Wallet> findPlayer(@RequestParam("q") String id) {
    return service.findWalletById(id);
  }

  @PostMapping
  public Mono<Wallet> add(@RequestBody Wallet player) {
    return service.add(player);
  }

  @PutMapping("/{id}")
  public Mono<Wallet> put(@PathVariable("id") String id, @RequestBody Wallet player) {
    return service.put(id, player);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> delete(@PathVariable("id") String id) {
    return service.delete(id);
  }

}
