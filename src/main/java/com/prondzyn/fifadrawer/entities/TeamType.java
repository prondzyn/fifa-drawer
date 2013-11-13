package com.prondzyn.fifadrawer.entities;

import com.prondzyn.fifadrawer.lang.InvalidTeamTypeException;

public enum TeamType {

  CLUB, NATIONAL;

  public static TeamType parse(String code) {
    try {
      return valueOf(code);
    } catch (IllegalArgumentException ex) {
      throw new InvalidTeamTypeException("Unknown team type '" + code + "' found.");
    }
  }
}
