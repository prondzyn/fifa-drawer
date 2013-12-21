package com.prondzyn.fifadrawer.loaders.filters;

import com.prondzyn.fifadrawer.entities.ComparisonType;
import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.domain.Team;
import com.prondzyn.fifadrawer.entities.TeamType;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.utils.CollectionUtils;

public class TeamsFilter {

  private final Properties properties;

  public TeamsFilter(Properties properties) {
    this.properties = properties;
  }

  public boolean isAcceptable(Team team) {
    if (team == null) {
      throw new IllegalArgumentException("Team cannot be null.");
    }
    return isAcceptableRank(team.getRank()) && isAcceptableType(team.getType()) && isAcceptableCountry(team.getCountry()) && isAcceptableLeague(team.getLeague()) && isAcceptableName(team.getName());
  }

  private boolean isAcceptableRank(Rank rank) {
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

  private boolean isAcceptableType(TeamType type) {
    return CollectionUtils.notContains(properties.getTeamTypesToSkip(), type);
  }

  private boolean isAcceptableCountry(String country) {
    return CollectionUtils.notContains(properties.getCountriesToSkip(), country);
  }

  private boolean isAcceptableLeague(String league) {
    return CollectionUtils.notContains(properties.getLeaguesToSkip(), league);
  }

  private boolean isAcceptableName(String name) {
    return CollectionUtils.notContains(properties.getNamesToSkip(), name);
  }
}
