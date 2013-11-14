package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.ComparisionType;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.lang.InvalidPropertyException;
import com.prondzyn.fifadrawer.lang.MissingPropertyException;
import com.prondzyn.fifadrawer.lang.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

public class PropertiesLoaderTest extends AbstractLoaderTest {

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

  @Test(expected = ParseException.class)
  public void invalidTeamsSkippedTypes() {
    PropertiesLoader.loadFrom(getFilepath("invalid-teams-skipped-type.cfg"));
  }

  @Test(expected = MissingPropertyException.class)
  public void emptyConfigFile() {
    PropertiesLoader.loadFrom(getFilepath("empty.cfg"));
  }

  @Test(expected = ApplicationException.class)
  public void noPrintSet() {
    PropertiesLoader.loadFrom(getFilepath("no-print.cfg"));
  }

  @Test(expected = InvalidPropertyException.class)
  public void missingTeamsFile() {
    PropertiesLoader.loadFrom(getFilepath("missing-teams-file.cfg"));
  }

  @Test(expected = InvalidPropertyException.class)
  public void missingParticipantsFile() {
    PropertiesLoader.loadFrom(getFilepath("missing-participants-file.cfg"));
  }

  @Test(expected = ParseException.class)
  public void emptyPrintProperties() {
    PropertiesLoader.loadFrom(getFilepath("empty-prints.cfg"));
  }

  @Test(expected = MissingPropertyException.class)
  public void missingPropertyForSendEmail() {
    PropertiesLoader.loadFrom(getFilepath("missing-property-for-send-email.cfg"));
  }

  @Test
  public void checkTeamRankThresholdLoading() {
    Properties properties = PropertiesLoader.loadFrom(getFilepath("print-console-min-valid.cfg"));
    assertEquals(Rank.TWO_HALF, properties.getTeamsRankThreshold());
  }

  @Test
  public void checkTeamsRankComparisionLoading() {
    Properties properties = PropertiesLoader.loadFrom(getFilepath("print-console-min-valid.cfg"));
    assertEquals(ComparisionType.GT, properties.getTeamsRankComparision());
  }

  @Test
  public void checkIfSenderIsNullWhenNotRequired() {
    Properties properties = PropertiesLoader.loadFrom(getFilepath("print-console-min-valid.cfg"));
    assertNull(properties.getSender());
  }
}
