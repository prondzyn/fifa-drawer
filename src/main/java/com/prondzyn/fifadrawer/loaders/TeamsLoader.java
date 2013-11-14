package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import static com.prondzyn.fifadrawer.Properties.DEFAULT_CHARSET;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.domain.Team;
import com.prondzyn.fifadrawer.entities.holders.TeamsHolder;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.lang.LoadingException;
import com.prondzyn.fifadrawer.lang.ParseException;
import com.prondzyn.fifadrawer.lang.TeamsFileException;
import com.prondzyn.fifadrawer.utils.IOUtils;
import com.prondzyn.fifadrawer.validators.TeamValidator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TeamsLoader {

  private final Properties properties;
  private final TeamValidator validator;

  public TeamsLoader(Properties properties) {
    this.properties = properties;
    this.validator = new TeamValidator(properties);
  }

  public TeamsHolder load() {

    TeamsHolder loaded = new TeamsHolder();
    FileInputStream fis = null;
    InputStreamReader ioReader = null;
    BufferedReader reader = null;
    File file = properties.getTeamsFile();

    try {

      fis = new FileInputStream(file);
      ioReader = new InputStreamReader(fis, DEFAULT_CHARSET);
      reader = new BufferedReader(ioReader);
      int i = 0;
      String line;

      while ((line = reader.readLine()) != null) {

        i += 1;

        String[] splitted = line.split(",");

        if (splitted.length != 4) {
          throw new TeamsFileException("Incorrect columns number in line #" + i + " in the '" + file + "'.");
        }

        String name = splitted[0];
        String league = splitted[1];
        String country = splitted[2];
        Rank rank = parseRank(file, i, splitted[3]);

        Team team = new Team(name, rank, country, league);

        if (validator.isValid(team)) {
          loaded.add(team);
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
    return loaded;
  }

  private Rank parseRank(File file, int lineNumber, String value) {
    try {
      return Rank.parse(value);
    } catch (ParseException ex) {
      throw new ParseException(ex.getMessage() + " Please check line #" + lineNumber + " in the '" + file + "'.");
    }
  }
}
