package com.prondzyn.fifadrawer.loaders.properties;

import static com.prondzyn.fifadrawer.utils.StringUtils.msg;

public abstract class AbstractPropertiesLoaderTest {

  protected String getFilepath(String filename) {
    return getClass().getResource(msg("/com/prondzyn/fifadrawer/loaders/properties/%s", filename)).getPath();
  }
}
