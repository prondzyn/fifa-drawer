package com.prondzyn.fifadrawer.utils;

public abstract class StringUtils {

  public static boolean isBlank(String string) {
    return string == null || string.trim().isEmpty();
  }

  public static boolean isNotBlank(String string) {
    return !isBlank(string);
  }

  public static boolean equal(String string1, String string2) {
    return equal(string1, string2, false);
  }

  public static boolean equalIgnoreCase(String string1, String string2) {
    return equal(string1, string2, true);
  }

  public static boolean notEqual(String string1, String string2) {
    return !equal(string1, string2);
  }

  public static boolean notEqualIgnoreCase(String string1, String string2) {
    return !equalIgnoreCase(string1, string2);
  }

  private static boolean equal(String string1, String string2, boolean caseInsensitive) {
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
  
  public static String upper(String value) {
    return value == null ? null : value.toUpperCase();
  }
  
  public static String lower(String value) {
    return value == null ? null : value.toLowerCase();
  }
}
