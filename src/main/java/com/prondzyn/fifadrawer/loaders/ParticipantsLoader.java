package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import static com.prondzyn.fifadrawer.Properties.DEFAULT_CHARSET;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.prondzyn.fifadrawer.entities.domain.Participant;
import com.prondzyn.fifadrawer.entities.holders.ParticipantsHolder;
import com.prondzyn.fifadrawer.lang.ParticipantsFileException;
import com.prondzyn.fifadrawer.utils.BooleanUtils;

public class ParticipantsLoader {

  private final Properties properties;

  public ParticipantsLoader(Properties properties) {
    this.properties = properties;
  }

  public ParticipantsHolder load() throws IOException, ParticipantsFileException {
    ParticipantsHolder loaded = new ParticipantsHolder();
    FileInputStream fis = null;
    InputStreamReader ioReader = null;
    BufferedReader reader = null;
    try {
      fis = new FileInputStream(properties.getParticipantsFilePath());
      ioReader = new InputStreamReader(fis, DEFAULT_CHARSET);
      reader = new BufferedReader(ioReader);
      String line;
      while ((line = reader.readLine()) != null) {
        String[] splitted = line.split(",");
        if (splitted.length != 3) {
          throw new ParticipantsFileException("Invalid line!");
        }
        String name = splitted[0];
        boolean active = BooleanUtils.parse(splitted[1]);
        String email = splitted[2];
        loaded.add(new Participant(name, active, email));
      }
    } finally {
      if (fis != null) {
        fis.close();
      }
      if (ioReader != null) {
        ioReader.close();
      }
      if (reader != null) {
        reader.close();
      }
    }
    validate(loaded);
    return loaded;
  }

  private void validate(ParticipantsHolder participants) {
    if (participants.isEmpty()) {
      throw new ParticipantsFileException("Participants file is empty.");
    }
  }
}
