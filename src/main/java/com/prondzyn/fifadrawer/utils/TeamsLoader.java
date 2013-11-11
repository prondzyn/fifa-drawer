package com.prondzyn.fifadrawer.utils;

import com.prondzyn.fifadrawer.Constants;
import com.prondzyn.fifadrawer.entities.Club;
import com.prondzyn.fifadrawer.entities.NationalTeam;
import com.prondzyn.fifadrawer.entities.Teams;
import com.prondzyn.fifadrawer.lang.TeamsFileException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class TeamsLoader {
  
  private static final String NATIONAL_TEAM_INDICATOR = "Reprezentacje";

  public static Teams load(String filepath) throws IOException, TeamsFileException {
    Teams loaded = new Teams();
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
        if (splitted.length != 4) {
          throw new TeamsFileException("Invalid line!");
        }
        String name = splitted[0];
        String league = splitted[1];
        String country = splitted[2];
        BigDecimal rank = new BigDecimal(splitted[3]);
        rank.setScale(1);
        if (NATIONAL_TEAM_INDICATOR.equalsIgnoreCase(country) || NATIONAL_TEAM_INDICATOR.equalsIgnoreCase(league)) {
          loaded.add(new NationalTeam(name, rank));
        } else {
          loaded.add(new Club(name, rank, country, league));
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
