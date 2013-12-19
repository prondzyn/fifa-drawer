package com.prondzyn.fifadrawer.entities.domain;

import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.TeamType;
import com.prondzyn.fifadrawer.utils.StringUtils;
import java.io.Serializable;

public class Team implements Serializable {

  private final String name;
  private final Rank rank;
  private final String country;
  private final String league;
  private final boolean nationalTeam;

  public Team(String name, Rank rank, String country, String league, boolean isNationalTeam) {
    this.name = name;
    this.rank = rank;
    this.country = country;
    this.league = league;
    this.nationalTeam = isNationalTeam;
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
    return nationalTeam ? TeamType.NATIONAL : TeamType.CLUB;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(name);
    if (!nationalTeam) {
      builder.append(" (");
      boolean countryExists = false;
      if (StringUtils.isNotBlank(country)) {
        builder.append(country);
        countryExists = true;
      }
      if (StringUtils.isNotBlank(league) && StringUtils.areNotEqualIgnoreCase(country, league)) {
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
