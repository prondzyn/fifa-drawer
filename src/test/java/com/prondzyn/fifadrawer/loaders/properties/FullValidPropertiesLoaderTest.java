package com.prondzyn.fifadrawer.loaders.properties;

import static org.junit.Assert.*;
import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.ComparisonType;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.TeamType;
import com.prondzyn.fifadrawer.loaders.PropertiesLoader;
import java.io.File;
import java.util.Set;
import javax.mail.internet.InternetAddress;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;

public class FullValidPropertiesLoaderTest extends AbstractPropertiesLoaderTest {

  private Properties properties;

  @Before
  public void init() {
    String filepath = getFilepath("full-valid-config-file.cfg");
    properties = PropertiesLoader.loadFrom(filepath);
  }

  @Test
  public void testPrintConsole() {
    assertTrue(properties.printDrawResultToConsole());
  }

  @Test
  public void testPrintEmail() {
    assertTrue(properties.sendDrawResultByEmail());
  }

  @Test
  public void testMailSMTP() {
    Properties.MailSMTP mailSMTP = properties.new MailSMTP();
    assertEquals("localhost", mailSMTP.getHost());
    assertEquals(587, mailSMTP.getPort());
    assertEquals("superuser", mailSMTP.getUsername());
    assertEquals("Pa55WoRd!", mailSMTP.getPassword());
  }

  @Test
  public void testEmailSubject() {
    assertEquals("FIFA Drawer", properties.getEmailSubject());
  }

  @Test
  public void testSender() {
    InternetAddress sender = properties.getSender();
    assertEquals("example@example.com", sender.getAddress());
    String personel = sender.getPersonal();
    assertTrue("Cristiano Ronaldo".equals(personel) || "Lionel Messi".equals(personel));
  }

  @Test
  public void testAdminEmail() {
    assertEquals("admin.email@example.com", properties.getAdminEmailAddress());
  }

  @Test
  public void testDrawParticipants() {
    assertTrue(properties.shouldDrawParticipants());
  }

  @Test
  public void testDrawTeams() {
    assertTrue(properties.shouldDrawTeams());
  }

  @Test
  public void testParticipantsFileName() {
    File file = properties.getParticipantsFile();
    assertEquals("participants.csv", file.getName());
  }

  @Test
  public void testTeamsFileName() {
    File file = properties.getTeamsFile();
    assertEquals("fifa12-teams.csv", file.getName());
  }

  @Test
  public void testParticipantsPerMatchCount() {
    assertEquals(4, properties.getParticipantsPerMatchCount());
  }

  @Test
  public void testTimeDisplay() {
    assertTrue(properties.shouldDisplayTime());
  }

  @Test
  public void testMatchesStartTime() {
    assertEquals(DateTime.parse("10:30", DateTimeFormat.forPattern(Properties.DEFAULT_TIME_FORMAT)), properties.getMatchesStartTime());
  }

  @Test
  public void testSingleMatchDuration() {
    assertEquals(15, properties.getSingleMatchDuration());
  }

  @Test
  public void testTeamsRankComparison() {
    assertEquals(ComparisonType.EQ, properties.getTeamsRankComparison());
  }

  @Test
  public void testTeamsRankThreshold() {
    assertEquals(Rank.FIVE, properties.getTeamsRankThreshold());
  }

  @Test
  public void testSkippedTeamTypes() {
    TeamType[] expected = new TeamType[]{TeamType.NATIONAL};
    Set<TeamType> skipped = properties.getTeamTypesToSkip();
    TeamType[] actuals = skipped.toArray(new TeamType[skipped.size()]);
    assertArrayEquals(expected, actuals);
  }

  @Test
  public void testSkippedCountries() {
    String[] expected = new String[]{"Anglia", "Belgia"};
    Set<String> skipped = properties.getCountriesToSkip();
    String[] actuals = skipped.toArray(new String[skipped.size()]);
    assertArrayEquals(expected, actuals);
  }

  @Test
  public void testSkippedLeagues() {
    String[] expected = new String[]{"Ekstraklasa", "Russian Premier League"};
    Set<String> skipped = properties.getLeaguesToSkip();
    String[] actuals = skipped.toArray(new String[skipped.size()]);
    assertArrayEquals(expected, actuals);
  }

  @Test
  public void testSkippedNames() {
    String[] expected = new String[]{"Nowa Zelandia", "Egipt"};
    Set<String> skipped = properties.getNamesToSkip();
    String[] actuals = skipped.toArray(new String[skipped.size()]);
    assertArrayEquals(expected, actuals);
  }

  @Test
  public void testAllowMixedMatches() {
    assertFalse(properties.shouldAllowMixedMatches());
  }
}
