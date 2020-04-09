package com.mg.clog.transaction;

import com.mg.clog.transaction.data.model.Transaction;
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

@RestController
@RequestMapping("clog/v1/transaction")
public class TransactionController {

  private final TransactionService service;

  public TransactionController(TransactionService service) {
    this.service = service;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public Flux<Transaction> getAll() {
    return service.list();
  }

  @GetMapping("/wallet/{walletId}")
  public Flux<Transaction> findByWallet(@PathVariable("walletId") String walletId) {
    return service.findByWallet(walletId);
  }

  @GetMapping("/{id}")
  public Mono<Transaction> findTransactionById(@PathVariable("id") String id) {
    return service.findById(id);
  }

  @PostMapping
  public Mono<Transaction> add(@RequestBody Transaction transaction, Authentication authentication) {
    var userId = authentication.getDetails().toString();
    transaction.setOwner(userId);
    return service.add(transaction);
  }

  @PutMapping("/{id}")
  public Mono<Transaction> put(@PathVariable("id") String id, @RequestBody Transaction transaction) {
    return service.put(id, transaction);
  }

  @PutMapping("/{id}/check")
  public Mono<Transaction> check(@PathVariable("id") String id) {
    return service.check(id);
  }

  @PutMapping("/{id}/uncheck")
  public Mono<Transaction> uncheck(@PathVariable("id") String id) {
    return service.uncheck(id);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> delete(@PathVariable("id") String id) {
    return service.delete(id);
  }

}
