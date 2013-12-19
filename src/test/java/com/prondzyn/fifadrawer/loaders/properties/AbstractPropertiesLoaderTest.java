package com.prondzyn.fifadrawer.loaders.properties;

public abstract class AbstractPropertiesLoaderTest {

  protected String getFilepath(String filename) {
    return getClass().getResource("/com/prondzyn/fifadrawer/loaders/properties/" + filename).getPath();
  }
}
