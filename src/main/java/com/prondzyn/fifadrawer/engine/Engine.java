package com.prondzyn.fifadrawer.engine;

import com.prondzyn.fifadrawer.engine.drawer.FIFADrawer;
import com.prondzyn.fifadrawer.entities.holders.ParticipantsHolder;
import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.holders.TeamsHolder;
import com.prondzyn.fifadrawer.lang.LoadingException;
import com.prondzyn.fifadrawer.lang.ParticipantsFileException;
import com.prondzyn.fifadrawer.lang.TeamsFileException;
import com.prondzyn.fifadrawer.mail.MailSender;
import com.prondzyn.fifadrawer.loaders.ParticipantsLoader;
import com.prondzyn.fifadrawer.loaders.PropertiesLoader;
import com.prondzyn.fifadrawer.loaders.TeamsLoader;
import java.io.IOException;

public class Engine {

  public static void main(String[] args) throws Exception {

    if (args.length != 1) {
      System.out.println("You have to specify path to file with participants.");
      System.out.println("One participant info per line: USERNAME,IS_ACTIVE[Y|N],EMAIL");
      return;
    }

    String configFilePath = args[0];

    try {
      Properties properties = PropertiesLoader.loadFrom(configFilePath);
      
      ParticipantsHolder participants = new ParticipantsLoader(properties).load();
      
      TeamsHolder teams = new TeamsLoader(properties).load();

      String drawResult = new FIFADrawer(participants.getNames(), teams.get()).draw();
      
      new MailSender(properties).send(drawResult, participants.getEmails());
      
    } catch (IOException | ParticipantsFileException | TeamsFileException ex) {
      throw new LoadingException(ex);
    }
  }

}
