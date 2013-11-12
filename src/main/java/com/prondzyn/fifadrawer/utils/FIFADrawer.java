package com.prondzyn.fifadrawer.utils;

import com.prondzyn.fifadrawer.entities.Properties;
import com.prondzyn.fifadrawer.entities.Team;
import com.prondzyn.fifadrawer.entities.TeamType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FIFADrawer {

  private final Properties properties;
  private final StringBuilder drawResult;

  private Pair incompletePair;

  public FIFADrawer(Properties properties) {
    this.properties = properties;
    this.drawResult = new StringBuilder();
  }

  public String draw(List<String> participants, Map<TeamType, Map<BigDecimal, List<Team>>> teams) {

    List<String> participantsCopy = CopyUtils.copy(participants);
    List<Pair> pairs = drawPairs(participantsCopy);
    drawMatches(pairs);

    drawTeams(teams);

    return drawResult.toString();
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

  private void drawMatches(List<Pair> teams) {
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
    for (int i = 0; i < matches.size(); i++) {
      drawResult.append(getMatchTime(i)).append(": ").append(matches.get(i)).append("\n");
    }
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

  private void drawTeams(Map<TeamType, Map<BigDecimal, List<Team>>> teams) {
    List<TeamType> availableTeamTypes = new ArrayList<>(teams.keySet());
    TeamType type = RandomUtils.getRandomItem(availableTeamTypes);
    Map<BigDecimal, List<Team>> rankedTeams = teams.get(type);
    List<BigDecimal> allowedRanks = new ArrayList<>(rankedTeams.keySet());
    BigDecimal rank = RandomUtils.getRandomItem(allowedRanks);
    List<Team> allowedTeams = rankedTeams.get(rank);
    if (CollectionUtils.hasMinSize(allowedTeams, 2)) {
      Team home = (Team) RandomUtils.removeRandomItem(allowedTeams);
      Team visitor = (Team) RandomUtils.getRandomItem(allowedTeams);
      drawResult.append("\nRank: ").append(home.getRank()).append("\n\n");
      drawResult.append("1st TEAM: ").append(home).append("\n2nd TEAM: ").append(visitor);
      drawResult.toString();
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
