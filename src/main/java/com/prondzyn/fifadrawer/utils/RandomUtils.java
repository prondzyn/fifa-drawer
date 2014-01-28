package com.prondzyn.fifadrawer.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {

  public static final int FAKE_INDEX = -1;

  public static <T> T getRandomItem(List<T> list) {
    int index = getRandomIndex(list);
    return (index != FAKE_INDEX) ? list.get(index) : null;
  }

  public static <T> T removeRandomItem(List<T> list) {
    int index = getRandomIndex(list);
    return (index != FAKE_INDEX) ? list.remove(index) : null;
  }

  @SuppressWarnings("rawtypes")
  private static int getRandomIndex(List list) {
    int count = list.size();
    if (count == 0) {
      return FAKE_INDEX;
    }
    Random random = new Random(System.currentTimeMillis());
    return random.nextInt(count);
  }
}
