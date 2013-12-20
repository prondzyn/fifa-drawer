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

public class VariousTeamsLoaderTest extends AbstractTeamsLoaderTest {

  private Properties properties;

  @Before
  public void init() {
    properties = new Properties("fake.cfg");
    String filepath = getFilepath("../../fifa12-teams.csv");
    properties.setProperty("file.path.teams", filepath);
    properties.setProperty("teams.rank.comparison", "GE");
    properties.setProperty("teams.rank.threshold", "0.5");
  }

  @Test
  public void testLoadingEmptyFile() {
    String filepath = getFilepath("empty.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder teams = loader.load();
    assertTrue(teams.isEmpty());
    assertEquals(0, teams.size());
  }

  @Test
  public void testLoadingValidSampleFile() {
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder teams = loader.load();
    assertFalse(teams.isEmpty());
  }

  @Test
  public void testContentOfValidSampleFile() {
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    Map<Rank, List<Team>> teams = holder.get();
    assertEquals(556, holder.size());
    assertEquals(6, teams.get(Rank.ZERO_HALF).size());
    assertEquals(32, teams.get(Rank.ONE).size());
    assertEquals(52, teams.get(Rank.ONE_HALF).size());
    assertEquals(64, teams.get(Rank.TWO).size());
    assertEquals(86, teams.get(Rank.TWO_HALF).size());
    assertEquals(123, teams.get(Rank.THREE).size());
    assertEquals(80, teams.get(Rank.THREE_HALF).size());
    assertEquals(59, teams.get(Rank.FOUR).size());
    assertEquals(33, teams.get(Rank.FOUR_HALF).size());
    assertEquals(21, teams.get(Rank.FIVE).size());
  }

  @Test
  public void testContentOfValidSampleFileClubsOnly() {
    properties.setProperty("teams.skipped.types", "NATIONAL");
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    Map<Rank, List<Team>> teams = holder.get();
    assertEquals(514, holder.size());
    assertEquals(6, teams.get(Rank.ZERO_HALF).size());
    assertEquals(32, teams.get(Rank.ONE).size());
    assertEquals(52, teams.get(Rank.ONE_HALF).size());
    assertEquals(64, teams.get(Rank.TWO).size());
    assertEquals(86, teams.get(Rank.TWO_HALF).size());
    assertEquals(119, teams.get(Rank.THREE).size());
    assertEquals(72, teams.get(Rank.THREE_HALF).size());
    assertEquals(46, teams.get(Rank.FOUR).size());
    assertEquals(25, teams.get(Rank.FOUR_HALF).size());
    assertEquals(12, teams.get(Rank.FIVE).size());
  }

  @Test
  public void testContentOfValidSampleFileNationalTeamsOnly() {
    properties.setProperty("teams.skipped.types", "CLUB");
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    Map<Rank, List<Team>> teams = holder.get();
    assertEquals(42, holder.size());
    assertNull(teams.get(Rank.ZERO_HALF));
    assertNull(teams.get(Rank.ONE));
    assertNull(teams.get(Rank.ONE_HALF));
    assertNull(teams.get(Rank.TWO));
    assertNull(teams.get(Rank.TWO_HALF));
    assertEquals(4, teams.get(Rank.THREE).size());
    assertEquals(8, teams.get(Rank.THREE_HALF).size());
    assertEquals(13, teams.get(Rank.FOUR).size());
    assertEquals(8, teams.get(Rank.FOUR_HALF).size());
    assertEquals(9, teams.get(Rank.FIVE).size());
  }

  @Test
  public void testLoadingThreeHalfRankTeamsOnly() {
    properties.setProperty("teams.rank.comparison", "EQ");
    properties.setProperty("teams.rank.threshold", "3.5");
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    assertEquals(80, holder.size());
  }

  @Test
  public void testLoadingLessThanTwoHalfRankTeamsOnly() {
    properties.setProperty("teams.rank.comparison", "LT");
    properties.setProperty("teams.rank.threshold", "2.5");
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    assertEquals(154, holder.size());
  }

  @Test
  public void testLoadingConcreteTeams() {
    String filepath = getFilepath("rest-of-the-world.csv");
    properties.setProperty("file.path.teams", filepath);
    properties.setProperty("teams.rank.comparison", "EQ");
    properties.setProperty("teams.rank.threshold", "5");
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    Map<Rank, List<Team>> teams = holder.get();
    assertEquals(2, holder.size());
    String[] expecteds = new String[]{"Klasyczna XI", "Światowa XI"};
    String[] actuals = new String[]{teams.get(Rank.FIVE).get(0).getName(), teams.get(Rank.FIVE).get(1).getName()};
    assertArrayEquals(expecteds, actuals);
  }
}
