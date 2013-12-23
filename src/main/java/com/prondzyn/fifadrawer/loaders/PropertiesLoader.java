package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.lang.LoadingException;
import static com.prondzyn.fifadrawer.utils.StringUtils.msg;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PropertiesLoader {

  public static Properties loadFrom(String filepath) {

    Properties properties = new Properties(filepath);
    try (FileInputStream fis = new FileInputStream(filepath)) {
      properties.load(fis);
    } catch (FileNotFoundException ex) {
      throw new LoadingException(msg("File '%s' not found.", filepath), ex);
    } catch (IOException ex) {
      throw new LoadingException(msg("There was a problem with reading file '%s'.", filepath), ex);
    }
    return properties;
  }
}
