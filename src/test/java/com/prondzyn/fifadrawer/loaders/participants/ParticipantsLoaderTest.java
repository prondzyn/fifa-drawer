package com.prondzyn.fifadrawer.loaders.participants;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.holders.ParticipantsHolder;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.lang.ParticipantsFileException;
import com.prondzyn.fifadrawer.loaders.ParticipantsLoader;
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
  public void testLoadingEmptyParticipantsFile() {
    String filepath = getFilepath("participants-empty.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingParticipantsAlphabetUsers() {
    String filepath = getFilepath("participants-alphabet.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    ParticipantsHolder participants = loader.load();
    assertEquals(25, participants.size());
  }

  @Test
  public void testLoadingParticipantsAlphabetEmails() {
    String filepath = getFilepath("participants-alphabet.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    ParticipantsHolder participants = loader.load();
    assertEquals(1, participants.getEmails().size());
  }

  @Test(expected = ParticipantsFileException.class)
  public void testLoadingParticipantsFileWithTooManyColumns() {
    String filepath = getFilepath("participants-too-many-columns.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test(expected = ApplicationException.class)
  public void testLoadingFileWhichDoesNotExists() {
    properties.setProperty("file.path.participants", "participants-file-does-not-exist.csv");
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    loader.load();
  }

  @Test
  public void testLoadingParticipantsAlphabet10from25() {
    String filepath = getFilepath("participants-alphabet-10-from-25.csv");
    properties.setProperty("file.path.participants", filepath);
    ParticipantsLoader loader = new ParticipantsLoader(properties);
    ParticipantsHolder participants = loader.load();
    assertEquals(10, participants.size());
  }
}
