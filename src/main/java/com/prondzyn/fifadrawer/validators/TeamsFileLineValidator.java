package com.prondzyn.fifadrawer.validators;

import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.lang.ParseException;
import com.prondzyn.fifadrawer.lang.TeamsFileException;
import com.prondzyn.fifadrawer.utils.BooleanUtils;
import com.prondzyn.fifadrawer.utils.StringUtils;
import java.util.List;

public abstract class TeamsFileLineValidator {

  public static void validate(List<String> line, int lineNumber) {

    if (line.size() != 5) {
      throw new TeamsFileException(errorMessage(lineNumber, "Incorrect columns number."));
    }

    String name = line.get(0);
    required(lineNumber, "Name", name);

    String league = line.get(1);
    required(lineNumber, "League", league);

    String country = line.get(2);
    required(lineNumber, "Country", country);

    String rawIsNationalTeam = line.get(3);
    required(lineNumber, "Is national team", rawIsNationalTeam);
    tryToParseBoolean(lineNumber, rawIsNationalTeam);

    String rawRank = line.get(4);
    required(lineNumber, "Rank", rawRank);
    tryToParseRank(lineNumber, rawRank);
  }

  private static void required(int lineNumber, String columnName, String value) {
    if (StringUtils.isBlank(value)) {
      throw new TeamsFileException(errorMessage(lineNumber, String.format("%s cannot be blank.", columnName)));
    }
  }

  private static void tryToParseBoolean(int lineNumber, String value) {
    try {
      BooleanUtils.parse(value);
    } catch (ParseException ex) {
      throw new TeamsFileException(errorMessage(lineNumber, ex.getMessage()));
    }
  }

  private static void tryToParseRank(int lineNumber, String value) {
    try {
      Rank.parse(value);
    } catch (ParseException ex) {
      throw new TeamsFileException(errorMessage(lineNumber, ex.getMessage()));
    }
  }

  private static String errorMessage(int lineNumber, String prefix) {
    return String.format("%s Line #%s. Please check the teams file.", prefix, lineNumber);
  }
}
