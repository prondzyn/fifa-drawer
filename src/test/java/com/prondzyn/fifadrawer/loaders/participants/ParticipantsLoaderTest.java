package com.prondzyn.fifadrawer.loaders.participants;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.holders.ParticipantsHolder;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.lang.ParseException;
import com.prondzyn.fifadrawer.lang.ParticipantsFileException;
import com.prondzyn.fifadrawer.loaders.ParticipantsLoader;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ParticipantsLoaderTest extends AbstractParticipantsLoaderTest {

  private Properties properties;

  @Before
  public void init() {
    properties = new Properties("fake.cfg");
  }

  @Test(expected = ParticipantsFileException.class)
  public void testLoadingEmptyFile() {
    String filepath = getFilepath("empty.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingUsersFromValidFile() {
    String filepath = getFilepath("valid.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    ParticipantsHolder participants = loader.load();
    assertEquals(25, participants.size());
  }

  @Test
  public void testLoadingEmailsFromValidFile() {
    String filepath = getFilepath("valid.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    ParticipantsHolder participants = loader.load();
    assertEquals(1, participants.getEmails().size());
  }

  @Test(expected = ParticipantsFileException.class)
  public void testLoadingParticipantsFromFileWithTooManyColumns() {
    String filepath = getFilepath("too-many-columns.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test(expected = ApplicationException.class)
  public void testLoadingFileWhichDoesNotExists() {
    properties.setProperty("file.path.participants", "file-does-not-exist.csv");
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoading10from25ActiveParticipantsFromValidFile() {
    String filepath = getFilepath("valid-10-from-25-active.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    ParticipantsHolder participants = loader.load();
    assertEquals(10, participants.size());
  }

  @Test(expected = ParticipantsFileException.class)
  public void testLoadingFileContainingBlankUsername() {
    String filepath = getFilepath("blank-username.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test(expected = ParseException.class)
  public void testLoadingFileContainingBlankActiveIndicator() {
    String filepath = getFilepath("blank-active-indicator.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test(expected = ParticipantsFileException.class)
  public void testLoadingFileContainingBlankEmailAddres() {
    String filepath = getFilepath("blank-email.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test(expected = ParseException.class)
  public void testLoadingFileContainingInvalidActiveIndicator() {
    String filepath = getFilepath("invalid-active-indicator.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test(expected = ParticipantsFileException.class)
  public void testLoadingFileContainingInvalidEmailAddres() {
    String filepath = getFilepath("invalid-email.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test
  public void testEmptyLinesAreNotAProblem() {
    String filepath = getFilepath("valid-with-blank-lines.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    ParticipantsHolder holder = loader.load();
    assertEquals(25, holder.size());
  }

  @Test
  public void testLoadingConcreteParticipants() {
    String filepath = getFilepath("three-active-participants.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    ParticipantsHolder holder = loader.load();
    List<String> usernames = holder.getNames();
    assertEquals(3, holder.size());
    String[] expecteds = new String[]{"Beata", "Jakub", "Lidia"};
    String[] actuals = usernames.toArray(new String[usernames.size()]);
    assertArrayEquals(expecteds, actuals);
  }
}
