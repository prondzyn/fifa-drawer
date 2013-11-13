package com.prondzyn.fifadrawer.validators;

import com.prondzyn.fifadrawer.entities.Properties;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.Team;
import com.prondzyn.fifadrawer.entities.TeamType;
import com.prondzyn.fifadrawer.utils.StringUtils;
import java.util.Set;

public class TeamValidator {

  private final Properties properties;

  public TeamValidator(Properties properties) {
    this.properties = properties;
  }

  public boolean isValid(Team team) {
    if (team == null) {
      throw new IllegalArgumentException("Team cannot be null.");
    }
    return isValidRank(team.getRank()) && isValidType(team.getType()) && isValidCountry(team.getCountry()) && isValidLeague(team.getLeague());
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

  private boolean isValidType(TeamType type) {
    Set<TeamType> skipped = properties.getTeamTypesToSkip();
    return !skipped.contains(type);
  }

  private boolean isValidCountry(String country) {
    Set<String> skipped = properties.getCountriesToSkip();
    return !skipped.contains(country);
  }

  private boolean isValidLeague(String league) {
    Set<String> skipped = properties.getLeaguesToSkip();
    return !skipped.contains(league);
  }
}
