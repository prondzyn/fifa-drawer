package com.prondzyn.fifadrawer.entities;

import java.math.BigDecimal;

public enum Rank {

  ZERO_HALF("0.5"),
  ONE("1.0"),
  ONE_HALF("1.5"),
  TWO("2.0"),
  TWO_HALF("2.5"),
  THREE("3.0"),
  THREE_HALF("3.5"),
  FOUR("4.0"),
  FOUR_HALF("4.5"),
  FIVE("5.0");

  private final BigDecimal code;

  private Rank(String code) {
    this.code = new BigDecimal(code).setScale(1);
  }

  public boolean equalsTo(Rank other) {
    return this.code.compareTo(other.code) == 0;
  }

  public boolean lessThan(Rank other) {
    return this.code.compareTo(other.code) < 0;
  }

  public boolean greaterThan(Rank other) {
    return this.code.compareTo(other.code) > 0;
  }

  public boolean lessThanEqualsTo(Rank other) {
    return this.code.compareTo(other.code) <= 0;
  }

  public boolean greaterThanEqualsTo(Rank other) {
    return this.code.compareTo(other.code) >= 0;
  }

  public static Rank parse(String code) {
    try {
      BigDecimal internalCode = new BigDecimal(code).setScale(1);
      for (Rank rank : values()) {
        if (rank.code.equals(internalCode)) {
          return rank;
        }
      }
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("Invalid rank '" + code + "' found.");
    }
    throw new IllegalArgumentException("Unknown rank '" + code + "' found.");
  }

  @Override
  public String toString() {
    return code.toString();
  }
}
