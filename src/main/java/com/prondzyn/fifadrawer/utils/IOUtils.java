package com.prondzyn.fifadrawer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public abstract class IOUtils {

  public static void closeQuietly(InputStream inputStream) {
    if (inputStream != null) {
      try {
        inputStream.close();
      } catch (IOException ex) {
        // ignore
      }
    }
  }

  public static void closeQuietly(Reader reader) {
    if (reader != null) {
      try {
        reader.close();
      } catch (IOException ex) {
        // ignore
      }
    }
  }
}
