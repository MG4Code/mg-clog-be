package com.mg.clog.transaction.data.repo;

import com.mg.clog.transaction.data.model.Transaction;
import reactor.core.publisher.Mono;

public interface CustomTransactionRepository {

  Mono<Transaction> updateChecked(String id, boolean checked);

}
