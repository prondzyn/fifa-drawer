package com.prondzyn.fifadrawer.entities;

import com.prondzyn.fifadrawer.utils.StringUtils;
import java.math.BigDecimal;

public class Club extends Team {

  private final String country;
  private final String league;

  public Club(String name, BigDecimal rank, String country, String league) {
    this.name = name;
    this.rank = rank;
    this.country = country;
    this.league = league;
  }

  public String getCountry() {
    return country;
  }

  public String getLeague() {
    return league;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(name);
    if (StringUtils.isNotBlank(country) || StringUtils.isNotBlank(league)) {
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
