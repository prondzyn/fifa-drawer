package com.prondzyn.fifadrawer.engine;

import com.prondzyn.fifadrawer.engine.drawer.FIFADrawer;
import com.prondzyn.fifadrawer.entities.holders.ParticipantsHolder;
import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.entities.holders.TeamsHolder;
import com.prondzyn.fifadrawer.lang.InvalidComparisionTypeException;
import com.prondzyn.fifadrawer.lang.InvalidPropertyException;
import com.prondzyn.fifadrawer.lang.InvalidRankException;
import com.prondzyn.fifadrawer.lang.LoadingException;
import com.prondzyn.fifadrawer.lang.MissingPropertyException;
import com.prondzyn.fifadrawer.lang.ParticipantsFileException;
import com.prondzyn.fifadrawer.lang.TeamsFileException;
import com.prondzyn.fifadrawer.mail.MailSender;
import com.prondzyn.fifadrawer.loaders.ParticipantsLoader;
import com.prondzyn.fifadrawer.loaders.PropertiesLoader;
import com.prondzyn.fifadrawer.loaders.TeamsLoader;

public class Engine {

  public static void main(String[] args) throws Exception {

    if (args.length < 1) {
      System.out.println("ERROR: Missing parameter. You have to specify path to the config file.");
      return;
    }

    String configFilePath = args[0];

    try {
      Properties properties = PropertiesLoader.loadFrom(configFilePath);

      ParticipantsHolder participants = new ParticipantsLoader(properties).load();

      TeamsHolder teams = new TeamsLoader(properties).load();

      String drawResult = new FIFADrawer(participants.getNames(), teams.get()).draw();

      new MailSender(properties).send(drawResult, participants.getEmails());

    } catch (LoadingException | MissingPropertyException | InvalidPropertyException | ParticipantsFileException | TeamsFileException | InvalidComparisionTypeException | InvalidRankException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }
  }

}
