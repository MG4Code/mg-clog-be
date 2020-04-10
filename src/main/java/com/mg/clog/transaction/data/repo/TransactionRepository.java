package com.mg.clog.transaction.data.repo;

import com.mg.clog.transaction.data.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveSortingRepository<Transaction, String>, CustomTransactionRepository {

  Flux<Transaction> findAllByWalletOrderByDateTimeAsc(String walletId, Pageable page);

}
