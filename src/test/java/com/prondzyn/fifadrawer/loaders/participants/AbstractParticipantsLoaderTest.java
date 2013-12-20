package com.prondzyn.fifadrawer.loaders.participants;

public abstract class AbstractParticipantsLoaderTest {

  protected String getFilepath(String filename) {
    return getClass().getResource("/com/prondzyn/fifadrawer/loaders/participants/" + filename).getPath();
  }
}
