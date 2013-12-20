package com.prondzyn.fifadrawer.loaders.teams;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.holders.TeamsHolder;
import com.prondzyn.fifadrawer.loaders.TeamsLoader;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SkipFiltersTeamsLoaderTest extends AbstractTeamsLoaderTest {

  private Properties properties;

  @Before
  public void init() {
    properties = new Properties("fake.cfg");
    String filepath = getFilepath("rest-of-the-world.csv");
    properties.setProperty("file.path.teams", filepath);
    properties.setProperty("teams.rank.comparison", "GE");
    properties.setProperty("teams.rank.threshold", "0.5");
  }

  @Test
  public void testLoadingRestOfTheWorldTeams() {
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    assertEquals(12, holder.size());
  }

  @Test
  public void testTeamTypesSkipFilterOnRestOfTheWorldTeamsFile() {
    properties.setProperty("teams.skipped.types", "CLUB");
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    assertTrue(holder.isEmpty());
  }

  @Test
  public void testLeaguesSkipFilterOnRestOfTheWorldTeamsFile() {
    properties.setProperty("teams.skipped.leagues", "Reszta Świata");
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    assertTrue(holder.isEmpty());
  }

  @Test
  public void testCountriesSkipFilterOnRestOfTheWorldTeamsFile() {
    properties.setProperty("teams.skipped.countries", "Grecja,RPA");
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    assertEquals(6, holder.size());
  }

  @Test
  public void testTeamNamesSkipFilterOnRestOfTheWorldTeamsFile() {
    properties.setProperty("teams.skipped.names", "Racing Club de Avellaneda,Światowa XI");
    TeamsLoader loader = new TeamsLoader(properties);
    TeamsHolder holder = loader.load();
    assertEquals(10, holder.size());
  }
}
