package com.prondzyn.fifadrawer.entities.domain;

import java.io.Serializable;


public class Participant implements Serializable {

  private final String username;
  private final boolean active;
  private final String email;

  public Participant(String username, boolean active, String email) {
    this.username = username;
    this.active = active;
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public boolean isActive() {
    return active;
  }

  public String getEmail() {
    return email;
  }

}
