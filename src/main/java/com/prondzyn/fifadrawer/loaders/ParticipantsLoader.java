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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ParticipantsLoader {

  private final static int MAX_PARTICIPANTS_NUMBER = 128;

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
        boolean active = BooleanUtils.parse(line.get(1));
        String email = line.get(2);

        loaded.add(new Participant(name, active, email));

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

  private void validate(ParticipantsHolder participants) {
    if (participants.isEmpty()) {
      throw new ParticipantsFileException("Participants file is empty.");
    }
    if (participants.size() > MAX_PARTICIPANTS_NUMBER) {
      throw new ParticipantsFileException("Too much active participants.");
    }
  }
}
