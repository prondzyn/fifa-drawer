package com.prondzyn.fifadrawer.utils;

import java.util.Arrays;
import java.util.List;

public abstract class StringUtils {

  public static boolean isBlank(String string) {
    return string == null || string.trim().isEmpty();
  }

  public static boolean isNotBlank(String string) {
    return !isBlank(string);
  }

  public static boolean areEqualIgnoreCase(String string1, String string2) {
    return areEqual(string1, string2, true);
  }

  public static boolean areNotEqualIgnoreCase(String string1, String string2) {
    return !areEqualIgnoreCase(string1, string2);
  }

  private static boolean areEqual(String string1, String string2, boolean caseInsensitive) {
    if (string1 == null && string2 == null) {
      return true;
    }
    if (string1 == null || string2 == null) {
      return false;
    }
    if (caseInsensitive) {
      return string1.equalsIgnoreCase(string2);
    }
    return string1.equals(string2);
  }

  public static String upper(String string) {
    return string == null ? null : string.toUpperCase();
  }

  public static String lower(String string) {
    return string == null ? null : string.toLowerCase();
  }

  public static List<String> split(String string) {
    String[] result = string.split(",");
    for (int i = 0; i < result.length; i++) {
      result[i] = result[i].trim();
    }
    return Arrays.asList(result);
  }

  public static String trim(String string) {
    return string != null ? string.trim() : null;
  }

  public static String trimToNull(String string) {
    String trimmed = trim(string);
    return trimmed.length() > 0 ? trimmed : null;
  }
}
