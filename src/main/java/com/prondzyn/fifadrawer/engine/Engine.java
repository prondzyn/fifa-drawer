package com.prondzyn.fifadrawer.engine;

import com.prondzyn.fifadrawer.entities.holders.ParticipantsHolder;
import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.engine.drawer.Drawer;
import com.prondzyn.fifadrawer.entities.holders.TeamsHolder;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.mail.MailSender;
import com.prondzyn.fifadrawer.loaders.ParticipantsLoader;
import com.prondzyn.fifadrawer.loaders.PropertiesLoader;
import com.prondzyn.fifadrawer.loaders.TeamsLoader;
import com.prondzyn.fifadrawer.utils.StringUtils;
import java.util.Set;

public class Engine {

  public static void main(String[] args) throws Exception {

    if (args.length < 1) {
      System.out.println("\nError: Missing parameter. You have to specify path to the application config file.");
      return;
    }

    String configFilePath = args[0];

    try {
      Properties properties = PropertiesLoader.loadFrom(configFilePath);

      ParticipantsHolder participants = new ParticipantsLoader(properties).load();

      TeamsHolder teams = new TeamsLoader(properties).load();

      String drawn = draw(properties, participants, teams);

      printIfAllowed(properties, drawn);

      sendIfAllowed(properties, drawn, participants.getEmails());

    } catch (ApplicationException ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  private static String draw(Properties properties, ParticipantsHolder participants, TeamsHolder teams) {
    Drawer drawer = new Drawer(properties);
    StringBuilder result = new StringBuilder();
    boolean participantsDrawn = false;
    if (properties.shouldDrawParticipants()) {
      String drawMatchesResult = drawer.drawMatches(participants.getNames());
      if (StringUtils.isNotBlank(drawMatchesResult)) {
        result.append(drawMatchesResult);
        participantsDrawn = true;
      }
    }
    if (properties.shouldDrawTeams()) {
      String drawTeamsResult = drawer.drawTeams(teams.get());
      if (StringUtils.isNotBlank(drawTeamsResult)) {
        if (participantsDrawn) {
          result.append("\n\n");
        }
        result.append(drawTeamsResult);
      }
    }
    return result.toString();
  }

  private static void printIfAllowed(Properties properties, String drawResult) {
    if (properties.printDrawResultToConsole() && StringUtils.isNotBlank(drawResult)) {
      System.out.println(drawResult);
    }
  }

  private static void sendIfAllowed(Properties properties, String drawResult, Set<String> emails) {
    if (properties.sendDrawResultByEmail() && StringUtils.isNotBlank(drawResult)) {
      new MailSender(properties).send(drawResult, emails);
    }
  }

}
