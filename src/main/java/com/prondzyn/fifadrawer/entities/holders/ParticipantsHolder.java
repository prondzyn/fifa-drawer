package com.prondzyn.fifadrawer.entities.holders;

import com.prondzyn.fifadrawer.entities.domain.Participant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ParticipantsHolder implements Serializable {

  private final List<Participant> participants = new ArrayList<>();

  public void add(Participant participant) {
    participants.add(participant);
  }

  public List<String> getNames() {
    List<String> usernames = new LinkedList<>();
    for (Participant p : participants) {
      usernames.add(p.getUsername());
    }
    return usernames;
  }

  public Set<String> getEmails() {
    Set<String> emails = new LinkedHashSet<>();
    for (Participant p : participants) {
      emails.add(p.getEmail());
    }
    return emails;
  }

  public boolean isEmpty() {
    return participants.isEmpty();
  }

  public int size() {
    return getNames().size();
  }
}
