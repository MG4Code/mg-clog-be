package com.mg.clog.player.data.repo;

import com.mg.clog.player.data.model.Player;
import com.mg.clog.player.data.model.Position;
import io.reactivex.Maybe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.adapter.rxjava.RxJava2Adapter;

@Component
public class CustomPlayerRepositoryImpl implements CustomPlayerRepository {

  private final ReactiveMongoTemplate mongoTemplate;

  @Autowired
  public CustomPlayerRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Maybe<Player> getFirstGoalKeeper() {
    var query = new Query(Criteria.where("position").is(Position.GOAL_KEEPER));
    return RxJava2Adapter.fluxToFlowable(mongoTemplate.find(query, Player.class))
      .firstElement();

  }
}
