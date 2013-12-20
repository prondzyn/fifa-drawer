package com.prondzyn.fifadrawer.entities.holders;

import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.domain.Team;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TeamsHolder implements Serializable {

  private final Map<Rank, List<Team>> buffer = new LinkedHashMap<>();

  public void add(Team team) {
    add(buffer, team);
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

  public Map<Rank, List<Team>> get() {
    return buffer;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public int size() {
    int size = 0;
    for (Rank rank : buffer.keySet()) {
      List<Team> list = buffer.get(rank);
      size += list.size();
    }
    return size;
  }
}
