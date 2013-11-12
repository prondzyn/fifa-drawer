package com.prondzyn.fifadrawer.utils;

import com.prondzyn.fifadrawer.Constants;
import com.prondzyn.fifadrawer.entities.Properties;
import com.prondzyn.fifadrawer.entities.Team;
import com.prondzyn.fifadrawer.entities.Teams;
import com.prondzyn.fifadrawer.lang.TeamsFileException;
import com.prondzyn.fifadrawer.validators.TeamValidator;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class TeamsLoader {

  private final Properties properties;
  private final TeamValidator validator;

  public TeamsLoader(Properties properties) {
    this.properties = properties;
    this.validator = new TeamValidator(properties);
  }

  public Teams load() throws IOException, TeamsFileException {
    Teams loaded = new Teams();
    FileInputStream fis = null;
    InputStreamReader ioReader = null;
    BufferedReader reader = null;
    try {
      fis = new FileInputStream(properties.getTeamsFilePath());
      ioReader = new InputStreamReader(fis, Constants.DEFAULT_CHARSET);
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
        BigDecimal rank = new BigDecimal(splitted[3]).setScale(1);

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
