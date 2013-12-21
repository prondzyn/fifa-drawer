package com.prondzyn.fifadrawer.loaders.filters;

import com.prondzyn.fifadrawer.entities.domain.Participant;

public class ParticipantsFilter {

  public boolean isAcceptable(Participant participant) {
    if (participant == null) {
      throw new IllegalArgumentException("Participant cannot be null.");
    }
    return participant.isActive();
  }
}
