package com.prondzyn.fifadrawer.loaders.teams;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.lang.TeamsFileException;
import com.prondzyn.fifadrawer.loaders.TeamsLoader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class InvalidFilesTeamsLoaderTest extends AbstractTeamsLoaderTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  private Properties properties;

  @Before
  public void init() {
    properties = new Properties("fake.cfg");
    properties.setProperty("teams.rank.comparison", "GE");
    properties.setProperty("teams.rank.threshold", "0.5");
  }

  @Test
  public void testLoadingFileWithEmptyTeamName() {
    // expect
    exception.expect(TeamsFileException.class);
    exception.expectMessage("Name cannot be blank. Line #8. Please check the teams file.");
    // test
    String filepath = getFilepath("empty-team-name.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingFileWithEmptyLeague() {
    // expect
    exception.expect(TeamsFileException.class);
    exception.expectMessage("League cannot be blank. Line #7. Please check the teams file.");
    // test
    String filepath = getFilepath("empty-league.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingFileWithEmptyCountry() {
    // expect
    exception.expect(TeamsFileException.class);
    exception.expectMessage("Country cannot be blank. Line #2. Please check the teams file.");
    // test
    String filepath = getFilepath("empty-country.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingFileWithEmptyNationalTeamIndicator() {
    // expect
    exception.expect(TeamsFileException.class);
    exception.expectMessage("Is national team cannot be blank. Line #7. Please check the teams file.");
    // test
    String filepath = getFilepath("empty-national-team-indicator.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingFileWithEmptyRank() {
    // expect
    exception.expect(TeamsFileException.class);
    exception.expectMessage("Rank cannot be blank. Line #6. Please check the teams file.");
    // test
    String filepath = getFilepath("empty-rank.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingFileWithInvalidNationalTeamIndicator() {
    // expect
    exception.expect(TeamsFileException.class);
    exception.expectMessage("Unknown boolean value 'nie' found. Allowed positive values: [Y, YES, T, TRUE]. Allowed negative values: [N, NO, FALSE]. Line #9. Please check the teams file.");
    // test
    String filepath = getFilepath("invalid-national-team-indicator.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingFileWithInvalidRank() {
    // expect
    exception.expect(TeamsFileException.class);
    exception.expectMessage("Unknown rank '6' found. Allowed values: [0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0]. Line #12. Please check the teams file.");
    // test
    String filepath = getFilepath("invalid-rank.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingTeamsFileWithTooManyColumns() {
    // expect
    exception.expect(TeamsFileException.class);
    exception.expectMessage("Incorrect columns number. Line #6. Please check the teams file.");
    // test
    String filepath = getFilepath("too-many-columns.csv");
    properties.setProperty("file.path.teams", filepath);
    TeamsLoader loader = new TeamsLoader(properties);
    loader.load();
  }
}
