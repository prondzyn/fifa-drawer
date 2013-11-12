package com.prondzyn.fifadrawer.validators;

import com.prondzyn.fifadrawer.entities.Properties;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.Team;
import com.prondzyn.fifadrawer.utils.StringUtils;

public class TeamValidator {

  private final Properties properties;

  public TeamValidator(Properties properties) {
    this.properties = properties;
  }

  public boolean isValid(Team team) {
    if (team == null) {
      throw new IllegalArgumentException("Team cannot be null.");
    }
    return isValidRank(team.getRank());
  }

  private boolean isValidRank(Rank rank) {
    Rank threshold = properties.getTeamsRankThreshold();
    String comparision = properties.getTeamsRankComparision();
    if (threshold == null || StringUtils.isBlank(comparision)) {
      throw new IllegalStateException("Teams rank threshold and comparision cannot be empty. Please check the application properties.");
    }
    switch (comparision) {
      case "eq":
        return rank.equalsTo(threshold);
      case "gt":
        return rank.greaterThan(threshold);
      case "lt":
        return rank.lessThan(threshold);
      case "ge":
        return rank.greaterThanEqualsTo(threshold);
      case "le":
        return rank.lessThanEqualsTo(threshold);
      default:
        throw new IllegalStateException("Invalid teams rank comparision = '" + comparision + "'. Please check the application properties.");
    }
  }

  public boolean isNotValid(Team team) {
    return !isValid(team);
  }
}
