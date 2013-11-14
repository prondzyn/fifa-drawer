package com.prondzyn.fifadrawer.entities.holders;

import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.domain.Team;
import com.prondzyn.fifadrawer.entities.TeamType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TeamsHolder implements Serializable {

  private final Map<Rank, List<Team>> clubs = new LinkedHashMap<>();
  private final Map<Rank, List<Team>> nations = new LinkedHashMap<>();

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

  private void add(Map<Rank, List<Team>> collection, Team team) {
    Rank rank = team.getRank();
    List<Team> teams = collection.get(rank);
    if (teams == null) {
      teams = new ArrayList<>();
      collection.put(rank, teams);
    }
    teams.add(team);
  }

  public Map<TeamType, Map<Rank, List<Team>>> get() {
    Map<TeamType, Map<Rank, List<Team>>> result = new LinkedHashMap<>();
    if (!clubs.isEmpty()) {
      result.put(TeamType.CLUB, clubs);
    }
    if (!nations.isEmpty()) {
      result.put(TeamType.NATIONAL, nations);
    }
    return result;
  }

  public boolean isEmpty() {
    return get().isEmpty();
  }
}
