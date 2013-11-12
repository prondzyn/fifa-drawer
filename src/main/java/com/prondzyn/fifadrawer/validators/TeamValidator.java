package com.prondzyn.fifadrawer.validators;

import com.prondzyn.fifadrawer.entities.Properties;
import com.prondzyn.fifadrawer.entities.Team;
import com.prondzyn.fifadrawer.utils.StringUtils;
import java.math.BigDecimal;

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

  private boolean isValidRank(BigDecimal rank) {
    BigDecimal threshold = properties.getTeamsRankThreshold();
    String comparision = properties.getTeamsRankComparision();
    if (threshold == null || StringUtils.isBlank(comparision)) {
      throw new IllegalStateException("Teams rank threshold and comparision cannot be empty. Please check the application properties.");
    }
    switch (comparision) {
      case "eq":
        return threshold.compareTo(rank) == 0;
      case "gt":
        return threshold.compareTo(rank) < 0;
      case "lt":
        return threshold.compareTo(rank) > 0;
      case "ge":
        return threshold.compareTo(rank) <= 0;
      case "le":
        return threshold.compareTo(rank) >= 0;
      default:
        throw new IllegalStateException("Invalid teams rank comparision = '" + comparision + "'. Please check the application properties.");
    }
  }

  public boolean isNotValid(Team team) {
    return !isValid(team);
  }
}
