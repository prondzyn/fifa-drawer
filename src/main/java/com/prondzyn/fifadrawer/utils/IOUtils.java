package com.prondzyn.fifadrawer.utils;

import java.io.Closeable;
import java.io.IOException;

public abstract class IOUtils {

  public static void closeQuietly(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (IOException ex) {
        // ignore
      }
    }
  }
}
