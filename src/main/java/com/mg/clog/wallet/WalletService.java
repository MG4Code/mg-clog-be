package com.mg.clog.wallet;

import com.mg.clog.wallet.data.model.Wallet;
import com.mg.clog.wallet.data.repo.WalletRepository;
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
public class WalletService {

  private Logger logger = LoggerFactory.getLogger(WalletService.class);
  private final WalletRepository repo;

  @Autowired
  public WalletService(WalletRepository repo) {
    this.repo = repo;
  }

  public Flowable<Wallet> list() {
    return repo.findAll()
      .doOnNext(e -> logger.info(e.toString()));
  }

  public Single<Wallet> add(Wallet player) {
    return repo.save(player);
  }

  public Single<Wallet> put(String id, Wallet wallet) {
    return repo.findById(id)
      .switchIfEmpty(Single.error(new WebApplicationException("No wallet found with id '" + id + "'", 404)))
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

  public Completable delete(String id) {
    return repo.deleteById(id);
  }

  public Maybe<Wallet> findWalletById(String id) {
    return repo.findById(id);
  }

}
