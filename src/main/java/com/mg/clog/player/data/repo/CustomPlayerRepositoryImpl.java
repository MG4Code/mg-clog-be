package com.mg.clog.player.data.repo;

import com.mg.clog.player.data.model.Player;
import com.mg.clog.player.data.model.Position;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomPlayerRepositoryImpl implements CustomPlayerRepository {

  private final ReactiveMongoTemplate mongoTemplate;

  public CustomPlayerRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Mono<Player> getFirstGoalKeeper() {
    var query = new Query(Criteria.where("position").is(Position.GOAL_KEEPER));
    return mongoTemplate.find(query, Player.class).next();
  }
}
