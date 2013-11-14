package com.prondzyn.fifadrawer.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CopyUtils {

  public static List<String> copy(List<String> original) {
    List<String> copy = new ArrayList<>();
    for (String string : original) {
      copy.add(string);
    }
    return copy;
  }

  public static Set<String> copy(Set<String> original) {
    Set<String> copy = new HashSet<>();
    for (String string : original) {
      copy.add(string);
    }
    return copy;
  }
}
