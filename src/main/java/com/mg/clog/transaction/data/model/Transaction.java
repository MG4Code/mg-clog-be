package com.mg.clog.transaction.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Document(collection = "transaction")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {

  @Id
  private String id;
  private String note;
  private String wallet;
  private String owner;
  private Long amount;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date dateTime;
  private boolean checked;

  public Transaction() {

  }

  public String getId() {
    return id;
  }

  public Transaction setId(String id) {
    this.id = id;
    return this;
  }

  public String getNote() {
    return note;
  }

  public Transaction setNote(String note) {
    this.note = note;
    return this;
  }

  public String getWallet() {
    return wallet;
  }

  public Transaction setWallet(String wallet) {
    this.wallet = wallet;
    return this;
  }

  public String getOwner() {
    return owner;
  }

  public Transaction setOwner(String owner) {
    this.owner = owner;
    return this;
  }

  public Long getAmount() {
    return amount;
  }

  public Transaction setAmount(Long amount) {
    this.amount = amount;
    return this;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public Transaction setDateTime(Date dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  public boolean isChecked() {
    return checked;
  }

  public Transaction setChecked(boolean checked) {
    this.checked = checked;
    return this;
  }

  @Override
  public String toString() {
    return "Transaction{" +
      "id='" + id + '\'' +
      ", note='" + note + '\'' +
      ", wallet='" + wallet + '\'' +
      ", owner='" + owner + '\'' +
      ", amount=" + amount +
      ", dateTime=" + dateTime +
      ", checked=" + checked +
      '}';
  }
}
