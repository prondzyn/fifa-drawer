package com.prondzyn.fifadrawer.utils;

import com.prondzyn.fifadrawer.lang.ParseException;
import static com.prondzyn.fifadrawer.utils.StringUtils.msg;
import java.util.ArrayList;
import java.util.List;

public abstract class BooleanUtils {

  private static final List<String> TRUE_VALUES;
  private static final List<String> FALSE_VALUES;

  static {
    TRUE_VALUES = new ArrayList<>();
    TRUE_VALUES.add("Y");
    TRUE_VALUES.add("YES");
    TRUE_VALUES.add("T");
    TRUE_VALUES.add("TRUE");
    FALSE_VALUES = new ArrayList<>();
    FALSE_VALUES.add("N");
    FALSE_VALUES.add("NO");
    FALSE_VALUES.add("FALSE");
  }

  public static boolean parse(String value) {
    if (TRUE_VALUES.contains(StringUtils.upper(value))) {
      return true;
    }
    if (FALSE_VALUES.contains(StringUtils.upper(value))) {
      return false;
    }
    throw new ParseException(msg("Unknown boolean value '%s' found. Allowed positive values: %s. Allowed negative values: %s.", value, TRUE_VALUES, FALSE_VALUES));
  }
}
