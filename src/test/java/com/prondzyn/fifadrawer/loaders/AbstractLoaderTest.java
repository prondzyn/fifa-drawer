package com.prondzyn.fifadrawer.loaders;

public abstract class AbstractLoaderTest {

  protected String getFilepath(String filename) {
    return getClass().getResource("/com/prondzyn/fifadrawer/loaders/" + filename).getPath();
  }
}
