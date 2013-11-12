package com.prondzyn.fifadrawer.utils;

import java.util.List;

public abstract class CollectionUtils {
  
  public static boolean isEmpty(List list) {
    return list == null || list.isEmpty();
  }
  
  public static boolean isNotEmpty(List list) {
    return !isEmpty(list);
  }
  
  public static boolean hasMinSize(List list, int minSize){
    return isNotEmpty(list) && list.size() >= minSize;
  }
}
