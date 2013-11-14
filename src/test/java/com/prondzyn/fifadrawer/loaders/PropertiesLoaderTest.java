package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.lang.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

public class PropertiesLoaderTest {

  private static final String URL_PREFIX = "../../resources/test/com/prondzyn/fifadrawer/loaders/";

  @Test
  public void minimalValidConfigForPrintToConsole() {
    Properties properties = PropertiesLoader.loadFrom(URL_PREFIX + "print-console-min-valid.cfg");
    assertNotNull(properties);
  }
  
  @Test
  public void minimalValidConfigForSendEmail() {
    Properties properties = PropertiesLoader.loadFrom(URL_PREFIX + "send-email-min-valid.cfg");
    assertNotNull(properties);
  }
  
  @Test(expected = ParseException.class)
  public void invalidTeamRankThreshold() {
    PropertiesLoader.loadFrom(URL_PREFIX + "invalid-teams-rank-threshold.cfg");
  }
  
  @Test(expected = ParseException.class)
  public void invalidTeamRankComparision() {
    PropertiesLoader.loadFrom(URL_PREFIX + "invalid-teams-rank-comparision.cfg");
  }
}
