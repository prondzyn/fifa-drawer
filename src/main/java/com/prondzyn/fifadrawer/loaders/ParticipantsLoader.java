package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import static com.prondzyn.fifadrawer.Properties.DEFAULT_CHARSET;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.prondzyn.fifadrawer.entities.domain.Participant;
import com.prondzyn.fifadrawer.entities.holders.ParticipantsHolder;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.lang.LoadingException;
import com.prondzyn.fifadrawer.lang.ParticipantsFileException;
import com.prondzyn.fifadrawer.utils.BooleanUtils;
import com.prondzyn.fifadrawer.io.CSVReader;
import com.prondzyn.fifadrawer.utils.IOUtils;
import com.prondzyn.fifadrawer.utils.StringUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ParticipantsLoader {

  private final Properties properties;

  public ParticipantsLoader(Properties properties) {
    this.properties = properties;
  }

  public ParticipantsHolder load() {

    ParticipantsHolder loaded = new ParticipantsHolder();
    FileInputStream fis = null;
    InputStreamReader ioReader = null;
    CSVReader reader = null;
    File file = properties.getParticipantsFile();

    try {

      fis = new FileInputStream(file);
      ioReader = new InputStreamReader(fis, DEFAULT_CHARSET);
      reader = new CSVReader(ioReader);

      List<String> line;

      while ((line = reader.readNext()) != null) {

        if (line.size() != 3) {
          throw new ParticipantsFileException("Incorrect columns number in line #" + reader.getLineNumber() + " in '" + file + "'.");
        }

        String name = line.get(0);
        validateName(reader.getLineNumber(), name);
        boolean active = BooleanUtils.parse(line.get(1));
        String email = line.get(2);
        validateEmail(reader.getLineNumber(), email);

        if (active) {
          loaded.add(new Participant(name, email));
        }

      }

    } catch (FileNotFoundException | UnsupportedEncodingException ex) {
      throw new ApplicationException(ex);
    } catch (IOException ex) {
      throw new LoadingException("There was a problem with reading file '" + file + "'.", ex);
    } finally {
      IOUtils.closeQuietly(fis);
      IOUtils.closeQuietly(ioReader);
      IOUtils.closeQuietly(reader);
    }

    validate(loaded);

    return loaded;
  }

  private void validateName(int lineNumber, String name) {
    if (StringUtils.isBlank(name)) {
      throw new ParticipantsFileException("Participant username cannot be blank. Line #" + lineNumber + ". Please check the participants file.");
    }
  }

  private void validateEmail(int lineNumber, String email) {
    if (StringUtils.isBlank(email)) {
      throw new ParticipantsFileException("Participant email cannot be blank. Line #" + lineNumber + ". Please check the participants file.");
    }
    try {
      new InternetAddress(email).validate();
    } catch (AddressException ex) {
      throw new ParticipantsFileException("Invalid email address '" + email + "' found in line #" + lineNumber + ". Please check the participants file.");
    }
  }

  private void validate(ParticipantsHolder participants) {
    if (participants.isEmpty()) {
      throw new ParticipantsFileException("Not enough active participants in the participants file.");
    }
  }
}
