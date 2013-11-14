package com.prondzyn.fifadrawer.entities;

import com.prondzyn.fifadrawer.lang.ParseException;
import com.prondzyn.fifadrawer.utils.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ComparisionType {

  EQ("eq", "==", "="),
  LT("lt", "<"),
  GT("gt", ">"),
  LE("le", "<="),
  GE("ge", ">=");

  private final List<String> codes;

  private ComparisionType(String... codes) {
    this.codes = Arrays.asList(codes);
  }

  public static ComparisionType parse(String value) {
    String lower = StringUtils.lower(value);
    for (ComparisionType type : values()) {
      if (type.codes.contains(lower)) {
        return type;
      }
    }
    throw new ParseException("Unknown comparision type '" + value + "' found. Allowed values: " + allowedValues() + ".");
  }

  private static List<String> allowedValues() {
    List<String> allowed = new ArrayList<>();
    for (ComparisionType type : values()) {
      allowed.add(type.name());
    }
    return allowed;
  }
}
