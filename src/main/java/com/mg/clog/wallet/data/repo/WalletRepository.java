package com.mg.clog.wallet.data.repo;

import com.mg.clog.wallet.data.model.Wallet;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletRepository extends ReactiveSortingRepository<Wallet, String> {

  Flux<Wallet> findAllByOwner(String userId);

  Mono<Wallet> findByIdAndOwner(String id, String owner);

  Mono<Void> deleteByIdAndOwner(String id, String owner);

}
