package com.prondzyn.fifadrawer.entities;

import com.prondzyn.fifadrawer.lang.ParseException;
import java.util.ArrayList;
import java.util.List;

public enum TeamType {

  CLUB, NATIONAL;

  public static TeamType parse(String value) {
    try {
      return valueOf(value);
    } catch (IllegalArgumentException ex) {
      throw new ParseException("Unknown team type '" + value + "' found. Allowed values: " + allowedValues() + ".");
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
