package com.prondzyn.fifadrawer.entities;

import com.prondzyn.fifadrawer.lang.ParseException;
import static com.prondzyn.fifadrawer.utils.StringUtils.msg;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

  public static Rank parse(String value) {
    try {
      BigDecimal internalCode = new BigDecimal(value).setScale(1);
      for (Rank rank : values()) {
        if (rank.code.equals(internalCode)) {
          return rank;
        }
      }
    } catch (NumberFormatException ex) {
      throw new ParseException(msg("Invalid rank '%s' found. Allowed values: %s.", value, allowedValues()));
    }
    throw new ParseException(msg("Unknown rank '%s' found. Allowed values: %s.", value, allowedValues()));
  }

  private static List<String> allowedValues() {
    List<String> allowed = new ArrayList<>();
    for (Rank rank : values()) {
      allowed.add(rank.toString());
    }
    return allowed;
  }

  @Override
  public String toString() {
    return code.toString();
  }
}
