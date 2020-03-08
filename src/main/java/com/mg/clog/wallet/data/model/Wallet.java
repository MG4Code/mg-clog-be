package com.mg.clog.wallet.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wallet")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Wallet {

  @Id
  private String id;
  private String number;
  private String name;

  public Wallet() {

  }

  public String getId() {
    return id;
  }

  public Wallet setId(String id) {
    this.id = id;
    return this;
  }

  public String getNumber() {
    return number;
  }

  public Wallet setNumber(String number) {
    this.number = number;
    return this;
  }

  public String getName() {
    return name;
  }

  public Wallet setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String toString() {
    return "Wallet{" +
      "id='" + id + '\'' +
      ", number='" + number + '\'' +
      ", name='" + name + '\'' +
      '}';
  }
}
