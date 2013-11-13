package com.prondzyn.fifadrawer.utils;

import java.util.HashSet;
import java.util.Set;

public abstract class BooleanUtils {

  private static final Set<String> TRUE_VALUES;

  static {
    TRUE_VALUES = new HashSet<>();
    TRUE_VALUES.add("Y");
    TRUE_VALUES.add("YES");
    TRUE_VALUES.add("T");
    TRUE_VALUES.add("TAK");
    TRUE_VALUES.add("TRUE");
  }

  public static boolean parse(String value) {
    return TRUE_VALUES.contains(StringUtils.upper(value));
  }
}
