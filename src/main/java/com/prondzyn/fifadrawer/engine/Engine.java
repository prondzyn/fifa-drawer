package com.prondzyn.fifadrawer.engine;

import com.prondzyn.fifadrawer.entities.Participants;
import com.prondzyn.fifadrawer.entities.Properties;
import com.prondzyn.fifadrawer.entities.Teams;
import com.prondzyn.fifadrawer.lang.LoadingException;
import com.prondzyn.fifadrawer.lang.ParticipantsFileException;
import com.prondzyn.fifadrawer.lang.TeamsFileException;
import com.prondzyn.fifadrawer.utils.FIFADrawer;
import com.prondzyn.fifadrawer.utils.MailSender;
import com.prondzyn.fifadrawer.utils.ParticipantsLoader;
import com.prondzyn.fifadrawer.utils.PropertiesLoader;
import com.prondzyn.fifadrawer.utils.TeamsLoader;
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
      Properties properties = PropertiesLoader.load(configFilePath);
      
      String participantsFilePath = properties.getParticipantsFilePath();
      Participants participants = ParticipantsLoader.load(participantsFilePath);
      if (participants.isEmpty()) {
        return;
      }
      
      Teams teams = new TeamsLoader(properties).load();

      FIFADrawer fifaDrawer = new FIFADrawer(participants.getActiveParticipantsUsernames(), teams.get());
      String message = fifaDrawer.draw();
      
      new MailSender(properties).send(message, participants.getActiveParticipantsEmails());
      
    } catch (IOException | ParticipantsFileException | TeamsFileException ex) {
      throw new LoadingException(ex);
    }
  }

}
