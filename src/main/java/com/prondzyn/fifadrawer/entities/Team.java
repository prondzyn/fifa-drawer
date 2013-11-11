package com.prondzyn.fifadrawer.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class Team implements Serializable {

  protected String name;
  protected BigDecimal rank;

  public String getName() {
    return name;
  }

  public BigDecimal getRank() {
    return rank;
  }

  public boolean asPowerfulAs(Team team) {
    return rank.equals(team.rank);
  }
  
  public boolean notAsPowerfulAs(Team team) {
    return !asPowerfulAs(team);
  }
  
  @Override
  public String toString() {
    return name;
  }

}
