package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import static com.prondzyn.fifadrawer.Properties.DEFAULT_CHARSET;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.domain.Team;
import com.prondzyn.fifadrawer.entities.holders.TeamsHolder;
import com.prondzyn.fifadrawer.lang.TeamsFileException;
import com.prondzyn.fifadrawer.validators.TeamValidator;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TeamsLoader {

  private final Properties properties;
  private final TeamValidator validator;

  public TeamsLoader(Properties properties) {
    this.properties = properties;
    this.validator = new TeamValidator(properties);
  }

  public TeamsHolder load() throws IOException, TeamsFileException {
    TeamsHolder loaded = new TeamsHolder();
    FileInputStream fis = null;
    InputStreamReader ioReader = null;
    BufferedReader reader = null;
    try {
      fis = new FileInputStream(properties.getTeamsFilePath());
      ioReader = new InputStreamReader(fis, DEFAULT_CHARSET);
      reader = new BufferedReader(ioReader);
      String line;
      while ((line = reader.readLine()) != null) {

        String[] splitted = line.split(",");
        if (splitted.length != 4) {
          throw new TeamsFileException("Invalid line!");
        }
        String name = splitted[0];
        String league = splitted[1];
        String country = splitted[2];
        Rank rank = Rank.parse(splitted[3]);

        Team team = new Team(name, rank, country, league);

        if (validator.isValid(team)) {
          loaded.add(team);
        }
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
}
