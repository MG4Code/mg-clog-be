package com.mg.clog.wallet.data.repo;

import com.mg.clog.wallet.data.model.Wallet;
import org.springframework.data.repository.reactive.RxJava2SortingRepository;

public interface WalletRepository extends RxJava2SortingRepository<Wallet, String> {


}
