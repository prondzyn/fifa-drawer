package com.prondzyn.fifadrawer.utils;

import com.prondzyn.fifadrawer.entities.Properties;
import com.prondzyn.fifadrawer.entities.Team;
import com.prondzyn.fifadrawer.entities.TeamType;
import com.prondzyn.fifadrawer.validators.TeamValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FIFADrawer {

  private Pair incompletePair;

  private final Properties properties;

  public FIFADrawer(Properties properties) {
    this.properties = properties;
  }

  public String draw(List<String> participants, Map<TeamType, List> teams) {
    List<String> participantsCopy = CopyUtils.copy(participants);
    List<Pair> pairs = drawPairs(participantsCopy);
    List<String> matches = drawMatches(pairs);
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < matches.size(); i++) {
      builder.append(getMatchTime(i)).append(": ").append(matches.get(i)).append("\n");
    }
    String drawnTeams = drawTeams(teams);
    builder.append("\n").append(drawnTeams);
    return builder.toString();
  }

  private List<Pair> drawPairs(List<String> participants) {
    List<Pair> pairs = new ArrayList<>();
    while (participants.size() > 0) {
      String first = RandomUtils.removeRandomItem(participants);
      String second = RandomUtils.removeRandomItem(participants);
      Pair pair = new Pair(first, second);
      pairs.add(pair);
      if (first == null || second == null) {
        incompletePair = pair;
      }
    }
    return pairs;
  }

  private List<String> drawMatches(List<Pair> teams) {
    boolean firstMatch = (incompletePair != null);
    List<String> matches = new ArrayList<>();
    while (teams.size() > 0) {
      Pair first;
      Pair second;
      if (firstMatch) {
        first = RandomUtils.getRandomItem(teams);
        if (first != incompletePair) {
          continue;
        }
        teams.remove(first);
        second = RandomUtils.removeRandomItem(teams);
        firstMatch = false;
      } else {
        first = RandomUtils.removeRandomItem(teams);
        second = RandomUtils.removeRandomItem(teams);
      }
      if (first != null && second != null) {
        matches.add(first + " vs. " + second);
      } else if (first != null) {
        String secondParticipant = first.getSecond();
        if (secondParticipant != null) {
          matches.add(first.getFirst() + " vs. " + secondParticipant);
        } else {
          matches.add(first.getFirst());
        }
      }
    }
    return matches;
  }

  private String getMatchTime(int i) {
    int startingHour = 10;
    String[] minutes = {"30", "45", "00", "15"};
    int hoursToAdd = (i + 2) / 4;
    int minutesIndex = i % 4;
    int hour = startingHour + hoursToAdd;
    String minute = minutes[minutesIndex];
    return hour + ":" + minute;
  }

  private String drawTeams(Map<TeamType, List> teams) {
    TeamValidator validator = new TeamValidator(properties);
    List<TeamType> availableTeamTypes = new ArrayList<>(teams.keySet());
    TeamType type = RandomUtils.getRandomItem(availableTeamTypes);
    List teamsList = teams.get(type);
    if (CollectionUtils.isNotEmpty(teamsList)) {
      Team home;
      do {
        home = (Team) RandomUtils.removeRandomItem(teamsList);
      } while (validator.isNotValid(home));
      if (home == null) {
        return "";
      }
      Team visitor;
      do {
        visitor = (Team) RandomUtils.removeRandomItem(teamsList);
        if (visitor == null) {
          visitor = home;
        }
      } while (home.notAsPowerfulAs(visitor) && validator.isNotValid(visitor));
      StringBuilder builder = new StringBuilder();
      builder.append("Rank: ").append(home.getRank()).append("\n\n");
      builder.append("1st TEAM: ").append(home).append("\n2nd TEAM: ").append(visitor);
      return builder.toString();
    } else {
      return "";
    }
  }

  private class Pair {

    private final String first;
    private final String second;

    public Pair(String first, String second) {
      this.first = first;
      this.second = second;
    }

    public String getFirst() {
      return first;
    }

    public String getSecond() {
      return second;
    }

    @Override
    public String toString() {
      if (first == null && second == null) {
        return "[EMPTY]";
      }
      if (first == null) {
        return second;
      }
      if (second == null) {
        return first;
      }
      return first + " + " + second;
    }
  }
}
