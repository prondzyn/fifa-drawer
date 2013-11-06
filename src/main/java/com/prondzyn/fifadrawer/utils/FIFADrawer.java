package com.prondzyn.fifadrawer.utils;

import java.util.ArrayList;
import java.util.List;

public class FIFADrawer {

  private Pair incompleteTeam;

  public String draw(List<String> participants) {
    List<String> participantsCopy = CopyUtils.copy(participants);
    List<Pair> teams = drawTeams(participantsCopy);
    List<String> matches = drawMatches(teams);
    String message = "";
    for (int i = 0; i < matches.size(); i++) {
      message += getMatchTime(i) + ": " + matches.get(i) + "\n";
    }
    return message;
  }

  private List<Pair> drawTeams(List<String> participants) {
    List<Pair> teams = new ArrayList<Pair>();
    while (participants.size() > 0) {
      String first = RandomUtils.removeRandomItem(participants);
      String second = RandomUtils.removeRandomItem(participants);
      Pair team = new Pair(first, second);
      teams.add(team);
      if (first == null || second == null) {
        incompleteTeam = team;
      }
    }
    return teams;
  }

  private List<String> drawMatches(List<Pair> teams) {
    boolean firstMatch = (incompleteTeam != null);
    List<String> matches = new ArrayList<String>();
    while (teams.size() > 0) {
      Pair first = null;
      Pair second = null;
      if (firstMatch) {
        first = RandomUtils.getRandomItem(teams);
        if (first != incompleteTeam) {
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

  private class Pair {

    private String first;
    private String second;

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
