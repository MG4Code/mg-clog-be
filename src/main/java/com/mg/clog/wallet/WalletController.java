package com.mg.clog.wallet;

import com.mg.clog.wallet.data.model.Wallet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

  @GetMapping("/{id}")
  public Mono<Wallet> findWalletById(@PathVariable("id") String id) {
    return service.findById(id);
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
