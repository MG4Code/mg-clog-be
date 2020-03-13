package com.mg.clog.wallet.data.repo;

import com.mg.clog.wallet.data.model.Wallet;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface WalletRepository extends ReactiveSortingRepository<Wallet, String> {


}
