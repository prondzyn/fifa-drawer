package com.prondzyn.fifadrawer.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Teams implements Serializable {

  private final List<Club> clubs = new ArrayList<>();
  private final List<NationalTeam> nations = new ArrayList<>();

  public void add(Club club) {
    clubs.add(club);
  }

  public void add(NationalTeam nationalTeam) {
    nations.add(nationalTeam);
  }

  public Map<TeamType, List> get() {
    Map<TeamType, List> result = new LinkedHashMap<>();
    if (!clubs.isEmpty()) {
      result.put(TeamType.CLUB, clubs);
    }
    if (!nations.isEmpty()) {
      result.put(TeamType.NATIONAL, nations);
    }
    return result;
  }
}
