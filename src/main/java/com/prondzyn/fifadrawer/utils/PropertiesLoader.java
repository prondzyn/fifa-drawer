package com.prondzyn.fifadrawer.utils;

import com.prondzyn.fifadrawer.entities.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class PropertiesLoader {

  public static Properties load(String filepath) throws IOException {

    Properties props = new Properties();
    try (FileInputStream fis = new FileInputStream(filepath)) {
      props.load(fis);
    }
    return props;
  }
}
