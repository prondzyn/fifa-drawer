package com.prondzyn.fifadrawer.entities;

import com.prondzyn.fifadrawer.lang.ParseException;
import static com.prondzyn.fifadrawer.utils.StringUtils.msg;
import java.util.ArrayList;
import java.util.List;

public enum TeamType {

  CLUB, NATIONAL;

  public static TeamType parse(String value) {
    try {
      return valueOf(value);
    } catch (IllegalArgumentException ex) {
      throw new ParseException(msg("Unknown team type '%s' found. Allowed values: %s.", value, allowedValues()));
    }
  }

  private static List<String> allowedValues() {
    List<String> allowed = new ArrayList<>();
    for (TeamType type : values()) {
      allowed.add(type.name());
    }
    return allowed;
  }
}
