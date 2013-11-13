package com.prondzyn.fifadrawer.entities;


public class Participant {

  private String username;
  private boolean active;
  private String email;

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
