package com.prondzyn.fifadrawer.entities.domain;

import java.io.Serializable;

public class Participant implements Serializable {

  private final String username;
  private final String email;
  private final boolean active;

  public Participant(String username, String email, boolean isActive) {
    this.username = username;
    this.email = email;
    this.active = isActive;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public boolean isActive() {
    return active;
  }

}
