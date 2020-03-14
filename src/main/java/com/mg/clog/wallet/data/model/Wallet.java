package com.mg.clog.wallet.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "wallet")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Wallet {

  @Id
  private String id;
  @Indexed(unique=true)
  private String number;
  private String name;
  private List<String> owner;

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

  public List<String> getOwner() {
    return owner;
  }

  public Wallet setOwner(List<String> owner) {
    this.owner = owner;
    return this;
  }

  @Override
  public String toString() {
    return "Wallet{" +
      "id='" + id + '\'' +
      ", number='" + number + '\'' +
      ", name='" + name + '\'' +
      ", owner=" + owner +
      '}';
  }
}
