package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class PropertiesLoader {
  
  public static Properties loadFrom(String filepath) throws IOException {

    Properties properties = new Properties();
    try (FileInputStream fis = new FileInputStream(filepath)) {
      properties.load(fis);
    }
    return properties;
  }
}
