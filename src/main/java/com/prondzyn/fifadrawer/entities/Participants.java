package com.prondzyn.fifadrawer.entities;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Participants {

  private List<Participant> participants = new ArrayList<Participant>();

  public void add(Participant participant) {
    participants.add(participant);
  }

  public Set<String> getActiveParticipantsEmails() {
    Set<String> emails = new LinkedHashSet<String>();
    for (Participant p : participants) {
      if (p.isActive()) {
        emails.add(p.getEmail());
      }
    }
    return emails;
  }

  public List<String> getActiveParticipantsUsernames() {
    List<String> usernames = new LinkedList<String>();
    for (Participant p : participants) {
      if (p.isActive()) {
        usernames.add(p.getUsername());
      }
    }
    return usernames;
  }

  public boolean isEmpty() {
    return participants.isEmpty();
  }
}
