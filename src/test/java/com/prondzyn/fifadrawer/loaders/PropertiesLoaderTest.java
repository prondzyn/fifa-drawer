package com.prondzyn.fifadrawer.loaders;

import static org.junit.Assert.*;
import com.prondzyn.fifadrawer.Properties;
import org.junit.Test;

public class PropertiesLoaderTest extends AbstractLoaderTest {

  @Test
  public void testMinimalAllowedConfigFileForParticipantsDrawOnly() {
    String filepath = getFilepath("minimal-allowed-config-file-for-participants-draw-only.cfg");
    Properties properties = PropertiesLoader.loadFrom(filepath);
    assertNotNull(properties);
  }

  @Test
  public void testMinimalAllowedConfigFileForTeamsDrawOnly() {
    String filepath = getFilepath("minimal-allowed-config-file-for-teams-draw-only.cfg");
    Properties properties = PropertiesLoader.loadFrom(filepath);
    assertNotNull(properties);
  }
}
