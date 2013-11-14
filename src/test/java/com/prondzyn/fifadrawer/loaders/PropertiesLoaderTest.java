package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.lang.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

public class PropertiesLoaderTest {

  @Test
  public void minimalValidConfigForPrintToConsole() {
    Properties properties = PropertiesLoader.loadFrom(getFilepath("print-console-min-valid.cfg"));
    assertNotNull(properties);
  }

  @Test
  public void minimalValidConfigForSendEmail() {
    Properties properties = PropertiesLoader.loadFrom(getFilepath("send-email-min-valid.cfg"));
    assertNotNull(properties);
  }

  @Test(expected = ParseException.class)
  public void invalidTeamRankThreshold() {
    PropertiesLoader.loadFrom(getFilepath("invalid-teams-rank-threshold.cfg"));
  }

  @Test(expected = ParseException.class)
  public void invalidTeamRankComparision() {
    PropertiesLoader.loadFrom(getFilepath("invalid-teams-rank-comparision.cfg"));
  }

  private String getFilepath(String filename) {
    return getClass().getResource("/com/prondzyn/fifadrawer/loaders/" + filename).getPath();
  }
}
