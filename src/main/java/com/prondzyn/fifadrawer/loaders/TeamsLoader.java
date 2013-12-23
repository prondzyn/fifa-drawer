package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import static com.prondzyn.fifadrawer.Properties.DEFAULT_CHARSET;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.domain.Team;
import com.prondzyn.fifadrawer.entities.holders.TeamsHolder;
import com.prondzyn.fifadrawer.io.CSVReader;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.lang.LoadingException;
import com.prondzyn.fifadrawer.utils.BooleanUtils;
import com.prondzyn.fifadrawer.utils.IOUtils;
import com.prondzyn.fifadrawer.loaders.filters.TeamsFilter;
import static com.prondzyn.fifadrawer.utils.StringUtils.msg;
import com.prondzyn.fifadrawer.validators.TeamsFileLineValidator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class TeamsLoader {

  private final Properties properties;
  private final TeamsFilter filter;
  private final TeamsFileLineValidator validator;

  public TeamsLoader(Properties properties) {
    this.properties = properties;
    this.filter = new TeamsFilter(properties);
    this.validator = new TeamsFileLineValidator();
  }

  public TeamsHolder load() {

    TeamsHolder loaded = new TeamsHolder();
    FileInputStream fis = null;
    InputStreamReader ioReader = null;
    CSVReader reader = null;
    File file = properties.getTeamsFile();

    try {

      fis = new FileInputStream(file);
      ioReader = new InputStreamReader(fis, DEFAULT_CHARSET);
      reader = new CSVReader(ioReader);
      List<String> line;

      while ((line = reader.readNext()) != null) {

        validator.validate(line, reader.getLineNumber());

        String name = line.get(0);
        String league = line.get(1);
        String country = line.get(2);
        boolean isNationalTeam = BooleanUtils.parse(line.get(3));
        Rank rank = Rank.parse(line.get(4));

        Team team = new Team(name, rank, country, league, isNationalTeam);

        if (filter.isAcceptable(team)) {
          loaded.add(team);
        }
      }

    } catch (FileNotFoundException | UnsupportedEncodingException ex) {
      throw new ApplicationException(ex);
    } catch (IOException ex) {
      throw new LoadingException(msg("There was a problem with reading file '%s'.", file), ex);
    } finally {
      IOUtils.closeQuietly(fis);
      IOUtils.closeQuietly(ioReader);
      IOUtils.closeQuietly(reader);
    }
    return loaded;
  }
}
