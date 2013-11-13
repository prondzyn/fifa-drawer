package com.prondzyn.fifadrawer.entities;

public enum TeamType {

  CLUB, NATIONAL;

  public static TeamType parse(String code) {
    try {
      return valueOf(code);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Unknown team type '" + code + "' found.");
    }
  }
}
