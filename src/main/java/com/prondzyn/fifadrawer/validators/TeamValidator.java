package com.prondzyn.fifadrawer.validators;

import com.prondzyn.fifadrawer.entities.ComparisonType;
import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.domain.Team;
import com.prondzyn.fifadrawer.entities.TeamType;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.utils.CollectionUtils;

public class TeamValidator {

  private final Properties properties;

  public TeamValidator(Properties properties) {
    this.properties = properties;
  }

  public boolean isValid(Team team) {
    if (team == null) {
      throw new IllegalArgumentException("Team cannot be null.");
    }
    return isValidRank(team.getRank()) && isValidType(team.getType()) && isValidCountry(team.getCountry()) && isValidLeague(team.getLeague()) && isValidName(team.getName());
  }

  private boolean isValidRank(Rank rank) {
    Rank threshold = properties.getTeamsRankThreshold();
    ComparisonType comparison = properties.getTeamsRankComparison();
    switch (comparison) {
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
        throw new ApplicationException();
    }
  }

  private boolean isValidType(TeamType type) {
    return CollectionUtils.notContains(properties.getTeamTypesToSkip(), type);
  }

  private boolean isValidCountry(String country) {
    return CollectionUtils.notContains(properties.getCountriesToSkip(), country);
  }

  private boolean isValidLeague(String league) {
    return CollectionUtils.notContains(properties.getLeaguesToSkip(), league);
  }

  private boolean isValidName(String name) {
    return CollectionUtils.notContains(properties.getNamesToSkip(), name);
  }
}
