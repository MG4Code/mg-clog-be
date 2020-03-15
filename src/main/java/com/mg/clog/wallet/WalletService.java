package com.mg.clog.wallet;

import com.mg.clog.wallet.data.model.Wallet;
import com.mg.clog.wallet.data.repo.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class WalletService {

  private Logger logger = LoggerFactory.getLogger(WalletService.class);
  private final WalletRepository repo;

  public WalletService(WalletRepository repo) {
    this.repo = repo;
  }

  public Flux<Wallet> list() {
    return repo.findAll()
      .doOnNext(e -> logger.info(e.toString()));
  }

  public Flux<Wallet> findByOwner(String userId) {
    return repo.findAllByOwner(userId)
      .doOnNext(e -> logger.info(e.toString()));
  }

  public Mono<Wallet> add(Wallet wallet) {
    return repo.save(wallet);
  }

  public Mono<Wallet> put(String id, Wallet wallet) {
    return repo.findById(id)
      .switchIfEmpty(Mono.error(new IllegalArgumentException("No wallet found with id '" + id + "'")))
      .map(byId -> {
        if (wallet.getName() != null) {
          byId.setName(wallet.getName());
        }
        if (wallet.getNumber() != null) {
          byId.setNumber(wallet.getNumber());
        }
        return byId;
      }).flatMap(repo::save);
  }

  public Mono<Void> delete(String id) {
    return repo.deleteById(id);
  }

  public Mono<Wallet> findById(String id) {
    return repo.findById(id);
  }

}
