package com.mg.clog.wallet;

import com.mg.clog.wallet.data.model.Wallet;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/wallet")
public class WalletController {

  private final WalletService service;

  @Autowired
  public WalletController(WalletService service) {
    this.service = service;
  }

  @GetMapping
  public Flowable<Wallet> list() {
    return service.list();
  }

  @GetMapping("id")
  public Maybe<Wallet> findPlayer(@RequestParam("q") String id) {
    return service.findWalletById(id);
  }

  @PostMapping
  public Single<Wallet> add(@RequestBody Wallet player) {
    return service.add(player);
  }

  @PutMapping("/{id}")
  public Single<Wallet> put(@PathVariable("id") String id, @RequestBody Wallet player) {
    return service.put(id, player);
  }

  @DeleteMapping("/{id}")
  public Completable delete(@PathVariable("id") String id) {
    return service.delete(id);
  }

}
