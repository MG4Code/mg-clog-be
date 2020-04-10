package com.mg.clog.user.data.repo;


import com.mg.clog.user.data.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
  Mono<User> findByUsername(String username);
  Mono<User> findById(String id);

  Mono<Boolean> existsByUsername(String username);

  Mono<Boolean> existsByEmail(String email);

  Mono<User> findByEmail(String email);
}
