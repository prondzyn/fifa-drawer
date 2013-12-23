package com.prondzyn.fifadrawer.entities;

import com.prondzyn.fifadrawer.lang.ParseException;
import com.prondzyn.fifadrawer.utils.StringUtils;
import static com.prondzyn.fifadrawer.utils.StringUtils.msg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ComparisonType {

  EQ("eq", "==", "="),
  LT("lt", "<"),
  GT("gt", ">"),
  LE("le", "<="),
  GE("ge", ">=");

  private final List<String> codes;

  private ComparisonType(String... codes) {
    this.codes = Arrays.asList(codes);
  }

  public static ComparisonType parse(String value) {
    String lower = StringUtils.lower(value);
    for (ComparisonType type : values()) {
      if (type.codes.contains(lower)) {
        return type;
      }
    }
    throw new ParseException(msg("Unknown comparison type '%s' found. Allowed values: %s.", value, allowedValues()));
  }

  private static List<String> allowedValues() {
    List<String> allowed = new ArrayList<>();
    for (ComparisonType type : values()) {
      allowed.add(type.name());
    }
    return allowed;
  }
}
