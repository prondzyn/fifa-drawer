package com.prondzyn.fifadrawer.utils;

import com.prondzyn.fifadrawer.Constants;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.prondzyn.fifadrawer.entities.Participant;
import com.prondzyn.fifadrawer.entities.Participants;
import com.prondzyn.fifadrawer.lang.ParticipantsFileException;

public class ParticipantsLoader {

  public static Participants load(String filepath) throws IOException, ParticipantsFileException {
    Participants loaded = new Participants();
    FileInputStream fis = null;
    InputStreamReader ioReader = null;
    BufferedReader reader = null;
    try {
      fis = new FileInputStream(filepath);
      ioReader = new InputStreamReader(fis, Constants.DEFAULT_CHARSET);
      reader = new BufferedReader(ioReader);
      String line;
      while ((line = reader.readLine()) != null) {
        String[] splitted = line.split(",");
        if (splitted.length != 3) {
          throw new ParticipantsFileException("Invalid line!");
        }
        String name = splitted[0];
        boolean active = parseBoolean(splitted[1]);
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
    return loaded;
  }

  private static boolean parseBoolean(String string) {
    return "Y".equals(string);
  }
}
