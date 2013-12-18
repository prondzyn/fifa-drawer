package com.prondzyn.fifadrawer.engine.drawer;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.TeamType;
import com.prondzyn.fifadrawer.entities.domain.Team;
import com.prondzyn.fifadrawer.utils.CollectionUtils;
import com.prondzyn.fifadrawer.utils.CopyUtils;
import com.prondzyn.fifadrawer.utils.RandomUtils;
import com.prondzyn.fifadrawer.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Drawer {

  private final Properties properties;

  public Drawer(Properties properties) {
    this.properties = properties;
  }

  public String drawMatches(List<String> participants) {
    List<String> participantsCopy = CopyUtils.copy(participants);
    List<List<String>> matches = drawParticipantToMatches(participantsCopy);
    if (couldDoNotLeaveAnyoneAlone(matches)) {
      doNotLeaveAnyoneAlone(matches);
    }
    List<Opponents> opponents = drawOpponents(matches);
    return printMatchesDrawResult(opponents);
  }

  private List<List<String>> drawParticipantToMatches(List<String> participants) {
    int requiredMatchesCount = getMatchesCount(participants);
    List<List<String>> result = initMatchesBuffer(requiredMatchesCount);
    while (participants.size() > 0) {
      String participant = RandomUtils.removeRandomItem(participants);
      int matchNumber = getCurrentMatchNumber(participants.size(), properties.getParticipantsPerMatchCount());
      result.get(matchNumber).add(participant);
    }
    return result;
  }

  private int getMatchesCount(List<String> participants) {
    int matchesCount = participants.size() / properties.getParticipantsPerMatchCount();
    int mod = participants.size() % properties.getParticipantsPerMatchCount();
    return mod == 0 ? matchesCount : matchesCount + 1;
  }

  private List<List<String>> initMatchesBuffer(int allMatchesCount) {
    List<List<String>> buffer = new ArrayList<>();
    for (int i = 0; i < allMatchesCount; i++) {
      buffer.add(new ArrayList<String>());
    }
    return buffer;
  }

  private int getCurrentMatchNumber(int currantParticipantsCount, int participantsPerMatchCount) {
    return currantParticipantsCount / participantsPerMatchCount;
  }

  private boolean couldDoNotLeaveAnyoneAlone(List<List<String>> matches) {
    return matches.size() > 1 && properties.getParticipantsPerMatchCount() > 2;
  }

  private void doNotLeaveAnyoneAlone(List<List<String>> matches) {
    int allMatchesCount = matches.size();
    List<String> lastMatch = matches.get(allMatchesCount - 1);
    List<String> lastButOneMatch = matches.get(allMatchesCount - 2);
    if (lastMatch.size() == 1) {
      String participant = RandomUtils.removeRandomItem(lastButOneMatch);
      lastMatch.add(participant);
    }
  }

  private List<Opponents> drawOpponents(List<List<String>> matches) {
    List<Opponents> opponents = new ArrayList<>();
    for (List<String> matchParticipants : matches) {
      List<String> firstTeam = new ArrayList<>();
      List<String> secondTeam = new ArrayList<>();
      int initialMatchParticipantsSize = matchParticipants.size();
      while (matchParticipants.size() > 0) {
        String participant = RandomUtils.removeRandomItem(matchParticipants);
        if ((initialMatchParticipantsSize / 2) <= matchParticipants.size()) {
          firstTeam.add(participant);
        } else {
          secondTeam.add(participant);
        }
      }
      opponents.add(new Opponents(firstTeam, secondTeam));
    }
    return opponents;
  }

  private String printMatchesDrawResult(List<Opponents> opponents) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < opponents.size(); i++) {
      String first = StringUtils.join(opponents.get(i).getFirst(), " + ");
      String second = StringUtils.join(opponents.get(i).getSecond(), " + ");
      if (StringUtils.isNotBlank(second)) {
        if (properties.shouldDisplayTime()) {
          builder.append(getMatchTime(i)).append(": ");
        }
        builder.append(first).append(" vs. ").append(second);
      } else {
        builder.append(first).append(" is alone. It is sad :(");
      }
      if (i < opponents.size() - 1) {
        builder.append("\n");
      }
    }
    return builder.toString();
  }

  private String getMatchTime(int currentMatchNumber) {
    DateTime startTime = properties.getMatchesStartTime();
    int duration = properties.getSingleMatchDuration();
    DateTime matchTime = startTime.plusMinutes(duration * currentMatchNumber);
    return matchTime.toString(DateTimeFormat.shortTime());
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
        Team visitor = (Team) RandomUtils.removeRandomItem(allowedTeams);
        builder.append("Rank: ").append(home.getRank()).append("\n");
        builder.append("\n");
        builder.append("1st TEAM: ").append(home).append("\n");
        builder.append("2nd TEAM: ").append(visitor);
        builder.toString();
      }
    }
    return builder.toString();
  }

  private class Opponents {

    private final List<String> first;
    private final List<String> second;

    public Opponents(List<String> first, List<String> second) {
      this.first = first;
      this.second = second;
    }

    public List<String> getFirst() {
      return first;
    }

    public List<String> getSecond() {
      return second;
    }
  }
}
