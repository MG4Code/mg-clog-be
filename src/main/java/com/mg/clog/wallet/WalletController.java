package com.mg.clog.wallet;

import com.mg.clog.wallet.data.model.Wallet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("clog/v1/wallet")
public class WalletController {

  private final WalletService service;

  public WalletController(WalletService service) {
    this.service = service;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public Flux<Wallet> getAll() {
    return service.list();
  }

  @RequestMapping("/my")
  @GetMapping
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public Flux<Wallet> get(Authentication authentication) {
    return service.findByOwner(authentication.getDetails().toString());
  }

  @GetMapping("id")
  public Mono<Wallet> findPlayer(@RequestParam("q") String id) {
    return service.findWalletById(id);
  }

  @PostMapping
  public Mono<Wallet> add(@RequestBody Wallet wallet) {
    return service.add(wallet);
  }

  @PutMapping("/{id}")
  public Mono<Wallet> put(@PathVariable("id") String id, @RequestBody Wallet wallet) {
    return service.put(id, wallet);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> delete(@PathVariable("id") String id) {
    return service.delete(id);
  }

}
