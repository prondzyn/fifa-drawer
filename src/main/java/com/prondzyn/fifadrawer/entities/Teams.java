package com.prondzyn.fifadrawer.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Teams implements Serializable {

  private final Map<BigDecimal, List<Team>> clubs = new LinkedHashMap<>();
  private final Map<BigDecimal, List<Team>> nations = new LinkedHashMap<>();

  public void add(Team team) {
    switch (team.getType()) {
      case CLUB:
        add(clubs, team);
        break;
      case NATIONAL:
        add(nations, team);
        break;
    }
  }
  
  private void add(Map<BigDecimal, List<Team>> collection, Team team) {
    BigDecimal rank = team.getRank();
    List<Team> teams = collection.get(rank);
    if (teams == null) {
      teams = new ArrayList<>();
      collection.put(rank, teams);
    }
    teams.add(team);
  }

  public Map<TeamType, Map<BigDecimal, List<Team>>> get() {
    Map<TeamType, Map<BigDecimal, List<Team>>> result = new LinkedHashMap<>();
    if (!clubs.isEmpty()) {
      result.put(TeamType.CLUB, clubs);
    }
    if (!nations.isEmpty()) {
      result.put(TeamType.NATIONAL, nations);
    }
    return result;
  }
}
