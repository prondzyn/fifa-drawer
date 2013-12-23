package com.prondzyn.fifadrawer.loaders.participants;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.holders.ParticipantsHolder;
import com.prondzyn.fifadrawer.lang.LoadingException;
import com.prondzyn.fifadrawer.lang.ParticipantsFileException;
import com.prondzyn.fifadrawer.loaders.ParticipantsLoader;
import static com.prondzyn.fifadrawer.utils.StringUtils.msg;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParticipantsLoaderTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  private Properties properties;

  @Before
  public void init() {
    properties = new Properties("fake.cfg");
  }

  @Test
  public void testLoadingEmptyFile() {
    // expect
    exception.expect(ParticipantsFileException.class);
    exception.expectMessage("Not enough active participants in the participants file.");
    // test
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

  @Test
  public void testLoadingFileWhichDoesNotExists() {
    // expect
    exception.expect(LoadingException.class);
    exception.expectMessage("File 'file-does-not-exist.csv' not found.");
    // test
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

  @Test
  public void testLoadingFileContainingBlankUsername() {
    // expect
    exception.expect(ParticipantsFileException.class);
    exception.expectMessage("Invalid email address. Line #9. Please check the participants file.");
    // test
    String filepath = getFilepath("blank-username.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingFileContainingBlankActiveIndicator() {
    // expect
    exception.expect(ParticipantsFileException.class);
    exception.expectMessage("Is active indicator cannot be blank. Line #14. Please check the participants file.");
    // test
    String filepath = getFilepath("blank-active-indicator.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingFileContainingBlankEmailAddres() {
    // expect
    exception.expect(ParticipantsFileException.class);
    exception.expectMessage("Participant email address cannot be blank. Line #21. Please check the participants file.");
    // test
    String filepath = getFilepath("blank-email.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingFileContainingInvalidActiveIndicator() {
    // expect
    exception.expect(ParticipantsFileException.class);
    exception.expectMessage("Unknown boolean value 'out of office' found. Allowed positive values: [Y, YES, T, TRUE]. Allowed negative values: [N, NO, FALSE]. Line #18. Please check the participants file.");
    // test
    String filepath = getFilepath("invalid-active-indicator.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingFileContainingInvalidEmailAddres() {
    // expect
    exception.expect(ParticipantsFileException.class);
    exception.expectMessage("Invalid email address. Line #9. Please check the participants file.");
    // test
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

  private String getFilepath(String filename) {
    return getClass().getResource(msg("/com/prondzyn/fifadrawer/loaders/participants/%s", filename)).getPath();
  }
}
