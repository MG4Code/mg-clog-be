package com.mg.clog.player.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "player")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Player {

  @Id
  private String id;
  private String firstName;
  private String lastName;
  private String club;
  private Integer shirtNumber;
  private Position position;

  public Player() {

  }

  public String getId() {
    return id;
  }

  public Player setId(String id) {
    this.id = id;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public Player setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public Player setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getClub() {
    return club;
  }

  public Player setClub(String club) {
    this.club = club;
    return this;
  }

  public Integer getShirtNumber() {
    return shirtNumber;
  }

  public Player setShirtNumber(Integer shirtNumber) {
    this.shirtNumber = shirtNumber;
    return this;
  }

  public Position getPosition() {
    return position;
  }

  public Player setPosition(Position position) {
    this.position = position;
    return this;
  }

  @Override
  public String toString() {
    return "Player{" +
      "id='" + id + '\'' +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", club='" + club + '\'' +
      ", shirtNumber=" + shirtNumber +
      ", position=" + position +
      '}';
  }
}
