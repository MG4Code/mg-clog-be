package com.mg.clog.wallet;

import com.mg.clog.wallet.data.model.Wallet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

import java.util.ArrayList;

@RestController
@RequestMapping("clog/v1/wallet/my")
public class MyWalletController {

  private final WalletService service;

  public MyWalletController(WalletService service) {
    this.service = service;
  }

  @GetMapping
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public Flux<Wallet> get(Authentication authentication) {
    return service.findByOwner(authentication.getDetails().toString());
  }

  @GetMapping("/{id}")
  public Mono<Wallet> findWalletById(@PathVariable("id") String id, Authentication authentication) {
    var userId = authentication.getDetails().toString();
    return service.findByIdAndOwner(id, userId);
  }

  @PostMapping
  public Mono<Wallet> add(@RequestBody Wallet wallet, Authentication authentication) {
    var userId = authentication.getDetails().toString();
    if (wallet.getOwner() == null) {
      wallet.setOwner(new ArrayList<>());
    }
    wallet.getOwner().add(userId);
    return service.add(wallet);
  }

  @PutMapping("/{id}")
  public Mono<Wallet> put(@PathVariable("id") String id, @RequestBody Wallet wallet, Authentication authentication) {
    var userId = authentication.getDetails().toString();
    return service.put(id, userId, wallet);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> delete(@PathVariable("id") String id, Authentication authentication) {
    var userId = authentication.getDetails().toString();
    return service.delete(id, userId);
  }

}
