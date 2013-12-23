package com.prondzyn.fifadrawer.validators;

import com.prondzyn.fifadrawer.lang.ParseException;
import com.prondzyn.fifadrawer.utils.BooleanUtils;
import com.prondzyn.fifadrawer.utils.StringUtils;
import static com.prondzyn.fifadrawer.utils.StringUtils.msg;
import java.util.List;

public abstract class AbstractFileLineValidator {

  public abstract void validate(List<String> line, int lineNumber);

  protected abstract String errorMessage(int lineNumber, String prefix);

  protected void required(int lineNumber, String columnName, String value) {
    if (StringUtils.isBlank(value)) {
      String errorMessage = requiredErrorMessage(lineNumber, columnName);
      requiredInternal(errorMessage);
    }
  }

  private String requiredErrorMessage(int lineNumber, String columnName) {
    return errorMessage(lineNumber, msg("%s cannot be blank.", columnName));
  }

  protected abstract void requiredInternal(String message);

  protected void tryToParseBoolean(int lineNumber, String value) {
    try {
      BooleanUtils.parse(value);
    } catch (ParseException ex) {
      String errorMessage = errorMessage(lineNumber, ex.getMessage());
      tryToParseBooleanInternal(errorMessage);
    }
  }

  protected abstract void tryToParseBooleanInternal(String message);
}
