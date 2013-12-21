package com.prondzyn.fifadrawer.validators;

import com.prondzyn.fifadrawer.lang.ParticipantsFileException;
import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ParticipantsFileLineValidator extends AbstractFileLineValidator {

  @Override
  public void validate(List<String> line, int lineNumber) {

    if (line.size() != 3) {
      throw new ParticipantsFileException(errorMessage(lineNumber, "Incorrect columns number."));
    }

    String name = line.get(0);
    required(lineNumber, "Participant username", name);

    String rawIsActive = line.get(1);
    required(lineNumber, "Is active indicator", rawIsActive);
    tryToParseBoolean(lineNumber, rawIsActive);

    String email = line.get(2);
    required(lineNumber, "Participant email address", email);
    validateEmail(lineNumber, email);
  }

  private void validateEmail(int lineNumber, String email) {
    try {
      new InternetAddress(email).validate();
    } catch (AddressException ex) {
      throw new ParticipantsFileException(errorMessage(lineNumber, "Invalid email address."));
    }
  }

  @Override
  protected String errorMessage(int lineNumber, String prefix) {
    return String.format("%s Line #%s. Please check the participants file.", prefix, lineNumber);
  }

  @Override
  protected void requiredInternal(String message) {
    throw new ParticipantsFileException(message);
  }

  @Override
  protected void tryToParseBooleanInternal(String message) {
    throw new ParticipantsFileException(message);
  }
}
