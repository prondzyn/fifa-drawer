package com.prondzyn.fifadrawer.loaders.teams;

public abstract class AbstractTeamsLoaderTest {

  protected String getFilepath(String filename) {
    return getClass().getResource("/com/prondzyn/fifadrawer/loaders/teams/" + filename).getPath();
  }
}
