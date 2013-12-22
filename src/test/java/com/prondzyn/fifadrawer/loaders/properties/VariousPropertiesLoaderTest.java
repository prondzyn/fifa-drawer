package com.prondzyn.fifadrawer.loaders.properties;

import static org.junit.Assert.*;
import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.lang.InvalidPropertyException;
import com.prondzyn.fifadrawer.lang.MissingPropertyException;
import com.prondzyn.fifadrawer.loaders.PropertiesLoader;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class VariousPropertiesLoaderTest extends AbstractPropertiesLoaderTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testEmptyConfigFile() {
    // expect
    exception.expect(MissingPropertyException.class);
    exception.expectMessage("Property 'print.console' not found. Please check the application config file.");
    // test
    String filepath = getFilepath("empty-config-file.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testInvalidPrintConsoleValue() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("Unknown boolean value 'some invalid value' found. Allowed positive values: [Y, YES, T, TRUE]. Allowed negative values: [N, NO, FALSE]. Please check the 'print.console' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-print-console-value.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testInvalidPrintEmailValue() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("Unknown boolean value 'some invalid value' found. Allowed positive values: [Y, YES, T, TRUE]. Allowed negative values: [N, NO, FALSE]. Please check the 'print.email' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-print-email-value.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testInvalidDrawParticipantsValue() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("Unknown boolean value 'some invalid value' found. Allowed positive values: [Y, YES, T, TRUE]. Allowed negative values: [N, NO, FALSE]. Please check the 'draw.participants' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-draw-participants-value.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testInvalidDrawTeamsValue() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("Unknown boolean value 'some invalid value' found. Allowed positive values: [Y, YES, T, TRUE]. Allowed negative values: [N, NO, FALSE]. Please check the 'draw.teams' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-draw-teams-value.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testMinimalRequiredConfigFileForParticipantsDrawOnly() {
    String filepath = getFilepath("minimal-required-config-file-for-participants-draw-only.cfg");
    Properties properties = PropertiesLoader.loadFrom(filepath);
    assertNotNull(properties);
  }

  @Test
  public void testInvalidParticipantsFilePathForParticipantsDraw() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("File 'C:\\workspaces\\github\\fifa-drawer\\build\\resources\\test\\com\\prondzyn\\fifadrawer\\loaders\\properties\\..\\there\\is\\no\\such\\path.csv' not found. Please check the 'file.path.participants' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-participants-file-path-for-participants-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testMinimalRequiredConfigFileForDisplayTimeInParticipantsDraw() {
    String filepath = getFilepath("minimal-required-config-file-for-display-time-in-participants-draw.cfg");
    Properties properties = PropertiesLoader.loadFrom(filepath);
    assertNotNull(properties);
  }

  @Test
  public void testInvalidDisplayTimeValueForParticipantsDraw() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("Unknown boolean value 'invalid value' found. Allowed positive values: [Y, YES, T, TRUE]. Allowed negative values: [N, NO, FALSE]. Please check the 'time.display' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-display-time-in-participants-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testInvalidMatchesStartTimeValueForParticipantsDraw() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("Invalid time format. HH:MM is a valid format. Please check the 'matches.start.time' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-matches-start-time-value-for-participants-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testInvalidSingleMatchDurationValueForParticipantsDraw() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("The 'single.match.duration' property must not be negative.");
    // test
    String filepath = getFilepath("invalid-single-match-duration-value-for-participants-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testMinimalRequiredConfigFileForSendingDrawResultByEmail() {
    String filepath = getFilepath("minimal-required-config-file-for-sending-draw-result-by-email.cfg");
    Properties properties = PropertiesLoader.loadFrom(filepath);
    assertNotNull(properties);
  }

  @Test
  public void testEmptyMailSubjectForSendingDrawResultByEmail() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("An email subject cannot be empty. Please check the 'mail.subject' property in the application config file.");
    // test
    String filepath = getFilepath("empty-mail-subject-for-sending-draw-result-by-email.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testInvalidMailSmtpPortForSendingDrawResultByEmail() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("The 'single.match.duration' property must not be negative.");
    // test
    String filepath = getFilepath("invalid-mail-smtp-port-for-sending-draw-result-by-email.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testInvalidMailSenderEmailAddressForSendingDrawResultByEmail() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("Invalid email address. Please check the 'mail.sender.email' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-mail-sender-email-address-for-sending-draw-result-by-email.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testInvalidAdminEmailAddressForSendingDrawResultByEmail() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("Invalid email address. Please check the 'admin.email' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-admin-email-address-for-sending-draw-result-by-email.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testInvalidParticipantsPerMatchCountForParticipantsDraw() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("The 'participants.per.match.count' property is out of range. It must be between 2 and 20.");
    // test
    String filepath = getFilepath("invalid-participants-per-match-count-for-participants-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testMinimalRequiredConfigFileForTeamsDrawOnly() {
    String filepath = getFilepath("minimal-required-config-file-for-teams-draw-only.cfg");
    Properties properties = PropertiesLoader.loadFrom(filepath);
    assertNotNull(properties);
  }

  @Test
  public void testInvalidTeamsFilePathForTeamsDraw() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("File 'C:\\workspaces\\github\\fifa-drawer\\build\\resources\\test\\com\\prondzyn\\fifadrawer\\loaders\\properties\\..\\path\\not\\exists.csv' not found. Please check the 'file.path.teams' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-teams-file-path-for-teams-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testInvalidRankComparisonForTeamsDraw() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("Unknown comparison type 'invalid comparison value' found. Allowed values: [EQ, LT, GT, LE, GE]. Please check the 'teams.rank.comparison' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-rank-comparison-for-teams-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testInvalidRankThresholdForTeamsDraw() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("Invalid rank 'invalid threshold value' found. Allowed values: [0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0]. Please check the 'teams.rank.threshold' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-rank-threshold-for-teams-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testSkippedTypesForTeamsDraw() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("Unknown team type 'NATIONAL TEAM' found. Allowed values: [CLUB, NATIONAL]. Please check the 'teams.skipped.types' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-skipped-types-for-teams-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testAllowMixedMatchesForTeamsDraw() {
    // expect
    exception.expect(InvalidPropertyException.class);
    exception.expectMessage("Unknown boolean value 'invalid value' found. Allowed positive values: [Y, YES, T, TRUE]. Allowed negative values: [N, NO, FALSE]. Please check the 'teams.allow.mixed.matches' property in the application config file.");
    // test
    String filepath = getFilepath("invalid-allow-mixed-matches-for-teams-draw.cfg");
    PropertiesLoader.loadFrom(filepath);
  }
}
