package com.prondzyn.fifadrawer.validators;

import com.prondzyn.fifadrawer.entities.ComparisionType;
import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.domain.Team;
import com.prondzyn.fifadrawer.entities.TeamType;
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
    ComparisionType comparision = properties.getTeamsRankComparision();
    switch (comparision) {
      case EQ:
        return rank.equalsTo(threshold);
      case GT:
        return rank.greaterThan(threshold);
      case LT:
        return rank.lessThan(threshold);
      case GE:
        return rank.greaterThanEqualsTo(threshold);
      case LE:
        return rank.lessThanEqualsTo(threshold);
      default:
        throw new IllegalStateException("This should never happen but who knows.");
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
