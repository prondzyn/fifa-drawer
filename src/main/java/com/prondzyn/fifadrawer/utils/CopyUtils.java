package com.prondzyn.fifadrawer.utils;

import java.util.ArrayList;
import java.util.List;

public class CopyUtils {

  public static List<String> copy(List<String> original) {
    List<String> copy = new ArrayList<String>();
    for (String string : original) {
      copy.add(string);
    }
    return copy;
  }
}
