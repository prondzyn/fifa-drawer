package com.prondzyn.fifadrawer.loaders.teams;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.domain.Team;
import com.prondzyn.fifadrawer.entities.holders.TeamsHolder;
import com.prondzyn.fifadrawer.loaders.TeamsLoader;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FIFA14TeamsLoaderTest extends AbstractTeamsLoaderTest {

  private Properties properties;

  @Before
  public void init() {
    properties = new Properties("fake.cfg");
    String filepath = getFilepath("../../fifa14-teams.csv");
    properties.setProperty("file.path.teams", filepath);
    properties.setProperty("teams.rank.comparison", "GE");
    properties.setProperty("teams.rank.threshold", "0.5");
  }

  @Test
  public void testContentOfValidSampleFile() {
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    Map<Rank, List<Team>> teams = holder.get();
    assertEquals(635, holder.size());
    assertEquals(13, teams.get(Rank.ZERO_HALF).size());
    assertEquals(39, teams.get(Rank.ONE).size());
    assertEquals(59, teams.get(Rank.ONE_HALF).size());
    assertEquals(76, teams.get(Rank.TWO).size());
    assertEquals(86, teams.get(Rank.TWO_HALF).size());
    assertEquals(154, teams.get(Rank.THREE).size());
    assertEquals(99, teams.get(Rank.THREE_HALF).size());
    assertEquals(59, teams.get(Rank.FOUR).size());
    assertEquals(30, teams.get(Rank.FOUR_HALF).size());
    assertEquals(20, teams.get(Rank.FIVE).size());
  }

  @Test
  public void testContentOfValidSampleFileClubsOnly() {
    properties.setProperty("teams.skipped.types", "NATIONAL");
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    Map<Rank, List<Team>> teams = holder.get();
    assertEquals(588, holder.size());
    assertEquals(13, teams.get(Rank.ZERO_HALF).size());
    assertEquals(38, teams.get(Rank.ONE).size());
    assertEquals(59, teams.get(Rank.ONE_HALF).size());
    assertEquals(76, teams.get(Rank.TWO).size());
    assertEquals(85, teams.get(Rank.TWO_HALF).size());
    assertEquals(149, teams.get(Rank.THREE).size());
    assertEquals(86, teams.get(Rank.THREE_HALF).size());
    assertEquals(46, teams.get(Rank.FOUR).size());
    assertEquals(23, teams.get(Rank.FOUR_HALF).size());
    assertEquals(13, teams.get(Rank.FIVE).size());
  }

  @Test
  public void testContentOfValidSampleFileNationalTeamsOnly() {
    properties.setProperty("teams.skipped.types", "CLUB");
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    Map<Rank, List<Team>> teams = holder.get();
    assertEquals(47, holder.size());
    assertNull(teams.get(Rank.ZERO_HALF));
    assertEquals(1, teams.get(Rank.ONE).size());
    assertNull(teams.get(Rank.ONE_HALF));
    assertNull(teams.get(Rank.TWO));
    assertEquals(1, teams.get(Rank.TWO_HALF).size());
    assertEquals(5, teams.get(Rank.THREE).size());
    assertEquals(13, teams.get(Rank.THREE_HALF).size());
    assertEquals(13, teams.get(Rank.FOUR).size());
    assertEquals(7, teams.get(Rank.FOUR_HALF).size());
    assertEquals(7, teams.get(Rank.FIVE).size());
  }
}
