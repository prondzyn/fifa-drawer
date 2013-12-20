package com.prondzyn.fifadrawer.loaders.teams;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.lang.TeamsFileException;
import com.prondzyn.fifadrawer.loaders.TeamsLoader;
import org.junit.Before;
import org.junit.Test;

public class InvalidFilesTeamsLoaderTest extends AbstractTeamsLoaderTest {

  private Properties properties;

  @Before
  public void init() {
    properties = new Properties("fake.cfg");
    properties.setProperty("teams.rank.comparison", "GE");
    properties.setProperty("teams.rank.threshold", "0.5");
  }

  @Test(expected = TeamsFileException.class)
  public void testLoadingFileWithEmptyTeamName() {
    String filepath = getFilepath("empty-team-name.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test(expected = TeamsFileException.class)
  public void testLoadingFileWithEmptyLeague() {
    String filepath = getFilepath("empty-league.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test(expected = TeamsFileException.class)
  public void testLoadingFileWithEmptyCountry() {
    String filepath = getFilepath("empty-country.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test(expected = TeamsFileException.class)
  public void testLoadingFileWithEmptyNationalTeamIndicator() {
    String filepath = getFilepath("empty-national-team-indicator.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test(expected = TeamsFileException.class)
  public void testLoadingFileWithEmptyRank() {
    String filepath = getFilepath("empty-rank.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test(expected = TeamsFileException.class)
  public void testLoadingFileWithInvalidNationalTeamIndicator() {
    String filepath = getFilepath("invalid-national-team-indicator.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test(expected = TeamsFileException.class)
  public void testLoadingFileWithInvalidRank() {
    String filepath = getFilepath("invalid-rank.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test(expected = TeamsFileException.class)
  public void testLoadingTeamsFileWithTooManyColumns() {
    String filepath = getFilepath("too-many-columns.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }
}
