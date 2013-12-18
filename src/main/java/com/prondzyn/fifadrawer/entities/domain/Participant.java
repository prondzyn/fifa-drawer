package com.prondzyn.fifadrawer.entities.domain;

import java.io.Serializable;

public class Participant implements Serializable {

  private final String username;
  private final String email;

  public Participant(String username, String email) {
    this.username = username;
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }
}
