package com.prondzyn.fifadrawer.utils;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Date;

public class RandomUtils {

  static {
    random = new Random(new Date().getTime());
  }

  public static final int FAKE_INDEX = -1;
  public static Random random;


  public static <T> T getRandomItem(List<T> list) {
    shuffle(list, 5);
    int index = getRandomIndex(list);
    return (index != FAKE_INDEX) ? list.get(index) : null;
  }

  public static <T> T removeRandomItem(List<T> list) {
    shuffle(list, 5);
    int index = getRandomIndex(list);
    return (index != FAKE_INDEX) ? list.remove(index) : null;
  }

  private static void shuffle(List<?> list, int numberOfTimes) {
    for (int i = 0; i < numberOfTimes; i++) {
      Collections.shuffle(list, randomizer());
    }
  }

  @SuppressWarnings("rawtypes")
  private static int getRandomIndex(List list) {
    int count = list.size();
    if (count == 0) {
      return FAKE_INDEX;
    }
    return randomizer().nextInt(count);
  }

  private static Random randomizer() {
    return random;
  }
}
