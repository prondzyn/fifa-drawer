package com.prondzyn.fifadrawer.loaders.properties;

import static org.junit.Assert.*;
import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.lang.InvalidPropertyException;
import com.prondzyn.fifadrawer.lang.MissingPropertyException;
import com.prondzyn.fifadrawer.loaders.PropertiesLoader;
import org.junit.Test;

public class VariousPropertiesLoaderTest extends AbstractPropertiesLoaderTest {

  @Test(expected = MissingPropertyException.class)
  public void testEmptyConfigFile() {
    String filepath = getFilepath("empty-config-file.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidPrintConsoleValue() {
    String filepath = getFilepath("invalid-print-console-value.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidPrintEmailValue() {
    String filepath = getFilepath("invalid-print-email-value.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidDrawParticipantsValue() {
    String filepath = getFilepath("invalid-draw-participants-value.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidDrawTeamsValue() {
    String filepath = getFilepath("invalid-draw-teams-value.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testMinimalRequiredConfigFileForParticipantsDrawOnly() {
    String filepath = getFilepath("minimal-required-config-file-for-participants-draw-only.cfg");
    Properties properties = PropertiesLoader.loadFrom(filepath);
    assertNotNull(properties);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidParticipantsFilePathForParticipantsDraw() {
    String filepath = getFilepath("invalid-participants-file-path-for-participants-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testMinimalRequiredConfigFileForDisplayTimeInParticipantsDraw() {
    String filepath = getFilepath("minimal-required-config-file-for-display-time-in-participants-draw.cfg");
    Properties properties = PropertiesLoader.loadFrom(filepath);
    assertNotNull(properties);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidDisplayTimeValueForParticipantsDraw() {
    String filepath = getFilepath("invalid-display-time-in-participants-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidMatchesStartTimeValueForParticipantsDraw() {
    String filepath = getFilepath("invalid-matches-start-time-value-for-participants-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidSingleMatchDurationValueForParticipantsDraw() {
    String filepath = getFilepath("invalid-single-match-duration-value-for-participants-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testMinimalRequiredConfigFileForSendingDrawResultByEmail() {
    String filepath = getFilepath("minimal-required-config-file-for-sending-draw-result-by-email.cfg");
    Properties properties = PropertiesLoader.loadFrom(filepath);
    assertNotNull(properties);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testEmptyMailSubjectForSendingDrawResultByEmail() {
    String filepath = getFilepath("empty-mail-subject-for-sending-draw-result-by-email.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidMailSmtpPortForSendingDrawResultByEmail() {
    String filepath = getFilepath("invalid-mail-smtp-port-for-sending-draw-result-by-email.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidMailSenderEmailAddressForSendingDrawResultByEmail() {
    String filepath = getFilepath("invalid-mail-sender-email-address-for-sending-draw-result-by-email.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidAdminEmailAddressForSendingDrawResultByEmail() {
    String filepath = getFilepath("invalid-admin-email-address-for-sending-draw-result-by-email.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidParticipantsPerMatchCountForParticipantsDraw() {
    String filepath = getFilepath("invalid-participants-per-match-count-for-participants-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testMinimalRequiredConfigFileForTeamsDrawOnly() {
    String filepath = getFilepath("minimal-required-config-file-for-teams-draw-only.cfg");
    Properties properties = PropertiesLoader.loadFrom(filepath);
    assertNotNull(properties);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidTeamsFilePathForTeamsDraw() {
    String filepath = getFilepath("invalid-teams-file-path-for-teams-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidRankComparisonForTeamsDraw() {
    String filepath = getFilepath("invalid-rank-comparison-for-teams-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testInvalidRankThresholdForTeamsDraw() {
    String filepath = getFilepath("invalid-rank-threshold-for-teams-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testSkippedTypesForTeamsDraw() {
    String filepath = getFilepath("invalid-skipped-types-for-teams-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test(expected = InvalidPropertyException.class)
  public void testAllowMixedMatchesForTeamsDraw() {
    String filepath = getFilepath("invalid-allow-mixed-matches-for-teams-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }
}
