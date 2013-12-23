package com.prondzyn.fifadrawer.loaders.teams;

import static com.prondzyn.fifadrawer.utils.StringUtils.msg;

public abstract class AbstractTeamsLoaderTest {

  protected String getFilepath(String filename) {
    return getClass().getResource(msg("/com/prondzyn/fifadrawer/loaders/teams/%s", filename)).getPath();
  }
}
