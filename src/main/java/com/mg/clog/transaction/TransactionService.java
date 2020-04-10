package com.mg.clog.transaction;

import com.mg.clog.transaction.data.model.Transaction;
import com.mg.clog.transaction.data.repo.TransactionRepository;
import com.mg.clog.util.OffsetBasedPageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TransactionService {

  private Logger logger = LoggerFactory.getLogger(TransactionService.class);
  private final TransactionRepository repo;

  public TransactionService(TransactionRepository repo) {
    this.repo = repo;
  }

  public Flux<Transaction> list() {
    return repo.findAll()
      .doOnNext(e -> logger.info(e.toString()));
  }

  public Flux<Transaction> findByWallet(String walletId, int skip, int limit) {
    return repo.findAllByWalletOrderByDateTimeDesc(walletId, new OffsetBasedPageRequest(skip, limit))
      .doOnNext(e -> logger.info(e.toString()));
  }

  public Mono<Transaction> add(Transaction transaction) {
    return repo.save(transaction);
  }

  public Mono<Transaction> put(String id, Transaction transaction) {
    return repo.findById(id)
      .switchIfEmpty(Mono.error(new IllegalArgumentException("No transaction found with id '" + id + "'")))
      .map(byId -> {
        if (transaction.getAmount() != null) {
          byId.setAmount(transaction.getAmount());
        }
        if (transaction.getDateTime() != null) {
          byId.setDateTime(transaction.getDateTime());
        }
        if (transaction.getNote() != null) {
          byId.setNote(transaction.getNote());
        }
        if (transaction.getOwner() != null) {
          byId.setOwner(transaction.getOwner());
        }
        if (transaction.getWallet() != null) {
          byId.setWallet(transaction.getWallet());
        }
        return byId;
      }).flatMap(repo::save);
  }

  public Mono<Void> delete(String id) {
    return repo.deleteById(id);
  }

  public Mono<Transaction> findById(String id) {
    return repo.findById(id);
  }

  public Mono<Transaction> check(String id) {
    return repo.updateChecked(id, true);
  }

  public Mono<Transaction> uncheck(String id) {
    return repo.updateChecked(id, false);
  }
}
