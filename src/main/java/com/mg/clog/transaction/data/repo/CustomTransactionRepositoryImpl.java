package com.mg.clog.transaction.data.repo;

import com.mg.clog.transaction.data.model.Transaction;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomTransactionRepositoryImpl implements CustomTransactionRepository {


  private final ReactiveMongoTemplate mongoTemplate;

  public CustomTransactionRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Mono<Transaction> updateChecked(String id, boolean checked) {
    var query = new Query().addCriteria(Criteria.where("id").is(id));
    var update = new Update().set("checked", checked);
    return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Transaction.class);
  }
}
