package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.TeamType;
import com.prondzyn.fifadrawer.entities.domain.Team;
import com.prondzyn.fifadrawer.entities.holders.TeamsHolder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

public class TeamsLoaderTest extends AbstractLoaderTest {

  @Test
  public void emptyTeamsFile() {
    Properties properties = PropertiesLoader.loadFrom(getFilepath("empty-teams-config.cfg"));
    TeamsHolder teams = new TeamsLoader(properties).load();
    assertTrue(teams.isEmpty());
  }

  @Test
  public void skippedNationsFromNationsOnlyFile() {
    Properties properties = PropertiesLoader.loadFrom(getFilepath("skipped-nations-config.cfg"));
    TeamsHolder teams = new TeamsLoader(properties).load();
    assertTrue(teams.isEmpty());
  }

  @Test
  public void isSizeOfLoadedTeamsCorrect() {
    Properties properties = PropertiesLoader.loadFrom(getFilepath("rest-of-the-world-rank-4.cfg"));
    TeamsHolder teams = new TeamsLoader(properties).load();
    assertEquals(2, teams.get().get(TeamType.CLUB).get(Rank.FOUR).size());
  }

  @Test
  public void areLoadedTeamsCorrect() {
    Properties properties = PropertiesLoader.loadFrom(getFilepath("rest-of-the-world-rank-4.cfg"));
    TeamsHolder teams = new TeamsLoader(properties).load();
    Team first = teams.get().get(TeamType.CLUB).get(Rank.FOUR).get(0);
    Team second = teams.get().get(TeamType.CLUB).get(Rank.FOUR).get(1);
    assertArrayEquals(new String[]{"Galatasaray S.K.", "Olympiacos F.C."}, new String[]{first.getName(), second.getName()});
  }

  @Test
  public void areLoadedTeamsEquallyPowerful() {
    Properties properties = PropertiesLoader.loadFrom(getFilepath("rest-of-the-world-rank-4.cfg"));
    TeamsHolder teams = new TeamsLoader(properties).load();
    Team first = teams.get().get(TeamType.CLUB).get(Rank.FOUR).get(0);
    Team second = teams.get().get(TeamType.CLUB).get(Rank.FOUR).get(1);
    assertTrue(first.asPowerfulAs(second));
  }

  @Test
  public void isLoadedTeamCorrect() {
    Properties properties = PropertiesLoader.loadFrom(getFilepath("only-one-team.cfg"));
    TeamsHolder teams = new TeamsLoader(properties).load();
    Team theOnlyTeam = null;
    Map<TeamType, Map<Rank, List<Team>>> teamsByType = teams.get();
    for (TeamType type : teamsByType.keySet()) {
      Map<Rank, List<Team>> teamsByRank = teamsByType.get(type);
      for (Rank rank : teamsByRank.keySet()) {
        for (Team team : teamsByRank.get(rank)) {
          theOnlyTeam = team;
        }
      }
    }
    assertNotNull(theOnlyTeam);
    assertEquals("P.A.O.K. F.C.", theOnlyTeam.getName());
    assertEquals("Reszta Świata", theOnlyTeam.getLeague());
    assertEquals("Grecja", theOnlyTeam.getCountry());
    assertEquals(Rank.THREE_HALF, theOnlyTeam.getRank());
    assertEquals(TeamType.CLUB, theOnlyTeam.getType());
  }

}
