package com.prondzyn.fifadrawer.engine;

import com.prondzyn.fifadrawer.entities.Participants;
import com.prondzyn.fifadrawer.lang.LoadingParticipantsException;
import com.prondzyn.fifadrawer.utils.FIFADrawer;
import com.prondzyn.fifadrawer.utils.MailSender;
import com.prondzyn.fifadrawer.utils.ParticipantsLoader;

public class Engine {

  private static final int DRAW_COUNT = 1;

  public static void main(String[] args) {

    if (args.length != 1) {
      System.out.println("You have to specify path to file with participants.");
      System.out.println("One participant info per line: USERNAME,IS_ACTIVE[Y|N],EMAIL");
      return;
    }

    String filepath = args[0];

    Participants participants = new Participants();

    try {
      participants = ParticipantsLoader.load(filepath);
    } catch (Exception ex) {
      throw new LoadingParticipantsException(ex);
    }

    if (participants.isEmpty()) {
      return;
    }

    FIFADrawer fifaDrawer = new FIFADrawer();
    for (int i = 0; i < DRAW_COUNT; i++) {
      String message = fifaDrawer.draw(participants.getActiveParticipantsUsernames());
      MailSender.send(message, participants.getActiveParticipantsEmails());
    }
  }

}
