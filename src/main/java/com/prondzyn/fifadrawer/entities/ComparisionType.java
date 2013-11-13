package com.prondzyn.fifadrawer.entities;

import com.prondzyn.fifadrawer.utils.StringUtils;
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

  public static ComparisionType parse(String code) {
    String lower = StringUtils.lower(code);
    for (ComparisionType type : values()) {
      if (type.codes.contains(lower)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown comparision type '" + code + "' found.");
  }
}
