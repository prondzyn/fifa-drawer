package com.prondzyn.fifadrawer.engine.drawer;

import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.domain.Team;
import com.prondzyn.fifadrawer.entities.TeamType;
import com.prondzyn.fifadrawer.utils.CollectionUtils;
import com.prondzyn.fifadrawer.utils.RandomUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FIFADrawer {

  private Pair incompletePair;

  public String drawMatches(List<String> participants) {
    List<Pair> pairs = drawPairs(participants);
    boolean firstMatch = (incompletePair != null);
    List<String> matches = new ArrayList<>();
    while (pairs.size() > 0) {
      Pair first;
      Pair second;
      if (firstMatch) {
        first = RandomUtils.getRandomItem(pairs);
        if (first != incompletePair) {
          continue;
        }
        pairs.remove(first);
        second = RandomUtils.removeRandomItem(pairs);
        firstMatch = false;
      } else {
        first = RandomUtils.removeRandomItem(pairs);
        second = RandomUtils.removeRandomItem(pairs);
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
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < matches.size(); i++) {
      builder.append(getMatchTime(i)).append(": ").append(matches.get(i));
      if (i < matches.size() - 1) {
        builder.append("\n");
      }
    }
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

  private String getMatchTime(int i) {
    int startingHour = 10;
    String[] minutes = {"30", "45", "00", "15"};
    int hoursToAdd = (i + 2) / 4;
    int minutesIndex = i % 4;
    int hour = startingHour + hoursToAdd;
    String minute = minutes[minutesIndex];
    return hour + ":" + minute;
  }

  public String drawTeams(Map<TeamType, Map<Rank, List<Team>>> teams) {
    StringBuilder builder = new StringBuilder();
    List<TeamType> availableTeamTypes = new ArrayList<>(teams.keySet());
    TeamType type = RandomUtils.getRandomItem(availableTeamTypes);
    Map<Rank, List<Team>> rankedTeams = teams.get(type);
    if (rankedTeams != null) {
      List<Rank> allowedRanks = new ArrayList<>(rankedTeams.keySet());
      Rank rank = RandomUtils.getRandomItem(allowedRanks);
      List<Team> allowedTeams = rankedTeams.get(rank);
      if (CollectionUtils.hasMinSize(allowedTeams, 2)) {
        Team home = (Team) RandomUtils.removeRandomItem(allowedTeams);
        Team visitor = (Team) RandomUtils.getRandomItem(allowedTeams);
        builder.append("Rank: ").append(home.getRank()).append("\n\n");
        builder.append("1st TEAM: ").append(home).append("\n2nd TEAM: ").append(visitor);
        builder.toString();
      }
    }
    return builder.toString();
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
