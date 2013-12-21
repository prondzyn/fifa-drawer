package com.prondzyn.fifadrawer.validators;

import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.lang.ParseException;
import com.prondzyn.fifadrawer.lang.TeamsFileException;
import java.util.List;

public class TeamsFileLineValidator extends AbstractFileLineValidator {

  @Override
  public void validate(List<String> line, int lineNumber) {

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

  private void tryToParseRank(int lineNumber, String value) {
    try {
      Rank.parse(value);
    } catch (ParseException ex) {
      throw new TeamsFileException(errorMessage(lineNumber, ex.getMessage()));
    }
  }

  @Override
  protected String errorMessage(int lineNumber, String prefix) {
    return String.format("%s Line #%s. Please check the teams file.", prefix, lineNumber);
  }

  @Override
  protected void requiredInternal(String message) {
    throw new TeamsFileException(message);
  }

  @Override
  protected void tryToParseBooleanInternal(String message) {
    throw new TeamsFileException(message);
  }
}
