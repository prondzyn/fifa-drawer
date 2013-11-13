package com.prondzyn.fifadrawer.entities;

import com.prondzyn.fifadrawer.utils.StringUtils;
import java.io.Serializable;

public class Team implements Serializable {

  private static final String NATIONAL_TEAM_INDICATOR = "Reprezentacje";

  private final String name;
  private final Rank rank;
  private final String country;
  private final String league;

  public Team(String name, Rank rank, String country, String league) {
    this.name = name;
    this.rank = rank;
    this.country = country;
    this.league = league;
  }

  public String getName() {
    return name;
  }

  public Rank getRank() {
    return rank;
  }

  public String getCountry() {
    return country;
  }

  public String getLeague() {
    return league;
  }

  public TeamType getType() {
    return isNationalTeam() ? TeamType.NATIONAL : TeamType.CLUB;
  }

  private boolean isNationalTeam() {
    return StringUtils.equalIgnoreCase(NATIONAL_TEAM_INDICATOR, country) || StringUtils.equalIgnoreCase(NATIONAL_TEAM_INDICATOR, league);
  }

  public boolean asPowerfulAs(Team team) {
    return rank.equals(team.rank);
  }

  public boolean notAsPowerfulAs(Team team) {
    return !asPowerfulAs(team);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(name);
    if (!isNationalTeam()) {
      builder.append(" (");
      boolean countryExists = false;
      if (StringUtils.isNotBlank(country)) {
        builder.append(country);
        countryExists = true;
      }
      if (StringUtils.isNotBlank(league) && StringUtils.notEqualIgnoreCase(country, league)) {
        if (countryExists) {
          builder.append(", ");
        }
        builder.append(league);
      }
      builder.append(")");
    }
    return builder.toString();
  }
}
