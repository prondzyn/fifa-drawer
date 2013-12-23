package com.prondzyn.fifadrawer.validators;

import static com.prondzyn.fifadrawer.Properties.Key.*;
import static com.prondzyn.fifadrawer.Properties.MailSMTP.*;
import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.lang.InvalidPropertyException;
import com.prondzyn.fifadrawer.lang.MissingPropertyException;
import com.prondzyn.fifadrawer.lang.ParseException;
import com.prondzyn.fifadrawer.utils.StringUtils;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class PropertiesValidator {

  private static final int PARTICIPANTS_PER_MATCH_MIN_COUNT = 2;
  private static final int PARTICIPANTS_PER_MATCH_MAX_COUNT = 20;

  private static final Set<String> required;
  private static final Set<String> requiredForParticipantsDraw;
  private static final Set<String> requiredForDisplayTime;
  private static final Set<String> requiredForTeamsDraw;
  private static final Set<String> requiredForEmail;

  static {

    required = new LinkedHashSet<>();
    required.add(PRINT_CONSOLE);
    required.add(PRINT_EMAIL);
    required.add(DRAW_PARTICIPANTS);
    required.add(DRAW_TEAMS);

    requiredForParticipantsDraw = new LinkedHashSet<>();
    requiredForParticipantsDraw.add(PARTICIPANTS_FILE_PATH);
    requiredForParticipantsDraw.add(PARTICIPANTS_PER_MATCH_COUNT);
    requiredForParticipantsDraw.add(DISPLAY_TIME);

    requiredForDisplayTime = new LinkedHashSet<>();
    requiredForDisplayTime.add(MATCHES_START_TIME);
    requiredForDisplayTime.add(SINGLE_MATCH_DURATION);

    requiredForTeamsDraw = new LinkedHashSet<>();
    requiredForTeamsDraw.add(TEAMS_FILE_PATH);
    requiredForTeamsDraw.add(TEAMS_RANK_THRESHOLD);
    requiredForTeamsDraw.add(TEAMS_RANK_COMPARISON);

    requiredForEmail = new LinkedHashSet<>();
    requiredForEmail.add(HOST);
    requiredForEmail.add(PORT);
    requiredForEmail.add(USERNAME);
    requiredForEmail.add(PASSWORD);
    requiredForEmail.add(MAIL_SUBJECT);
    requiredForEmail.add(MAIL_SENDER_EMAIL);
    requiredForEmail.add(MAIL_SENDER_NAMES);
    requiredForEmail.add(ADMIN_EMAIL);
  }

  private final Properties properties;

  public PropertiesValidator(Properties properties) {
    this.properties = properties;
  }

  public void validate() {

    validateRequired(required);

    validatePrinting();

    validateDrawIndicators();

    if (properties.shouldDrawParticipants()) {
      validatePropertiesRequiredForParticipantsDraw();
    }

    if (properties.shouldDrawTeams()) {
      validatePropertiesRequiredForTeamsDraw();
    }

    if (properties.sendDrawResultByEmail()) {
      validatePropertiesRequiredForEmail();
    }
  }

  private void validateRequired(Set<String> requiredProperties) {
    for (String req : requiredProperties) {
      String value = properties.getProperty(req);
      if (value == null) {
        throw new MissingPropertyException("Property '" + req + "' not found. Please check the application config file.");
      }
    }
  }

  private void validatePrinting() {

    validatePrintDrawResultToConsole();
    validateSendDrawResultByEmail();

    if (!properties.printDrawResultToConsole() && !properties.sendDrawResultByEmail()) {
      throw new ApplicationException("Both printing methods are disabled. Please enable at least one printing method in the application config file.");
    }
  }

  private void validatePrintDrawResultToConsole() {
    try {
      properties.printDrawResultToConsole();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(PRINT_CONSOLE, ex.getMessage()));
    }
  }

  private void validateSendDrawResultByEmail() {
    try {
      properties.sendDrawResultByEmail();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(PRINT_EMAIL, ex.getMessage()));
    }
  }

  private void validateDrawIndicators() {

    validateShouldDrawParticipants();
    validateShouldDrawTeams();

    if (!properties.shouldDrawParticipants() && !properties.shouldDrawTeams()) {
      throw new ApplicationException("Both draw types are turned off. Please turn on at least one draw type in the application config file.");
    }
  }

  private void validateShouldDrawParticipants() {
    try {
      properties.shouldDrawParticipants();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(DRAW_PARTICIPANTS, ex.getMessage()));
    }
  }

  private void validateShouldDrawTeams() {
    try {
      properties.shouldDrawTeams();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(DRAW_TEAMS, ex.getMessage()));
    }
  }

  private void validatePropertiesRequiredForParticipantsDraw() {
    validateRequired(requiredForParticipantsDraw);
    validateParticipantsFile();
    validateParticipantsPerMatchCount();
    validateShouldDisplayTime();
    if (properties.shouldDisplayTime()) {
      validatePropertiesRequiredForDisplayTime();
    }
  }

  private void validateParticipantsFile() {
    validateFile(properties.getParticipantsFile(), PARTICIPANTS_FILE_PATH);
  }

  private void validateFile(File file, String key) {
    if (!file.exists()) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(key, "File '" + file + "' not found."));
    }
    if (!file.isFile()) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(key, "Given file is not a regular file."));
    }
  }

  private void validateParticipantsPerMatchCount() {
    try {
      int count = properties.getParticipantsPerMatchCount();
      validateParticipantsPerMatchCountRange(count);
    } catch (NumberFormatException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(PARTICIPANTS_PER_MATCH_COUNT, "Invalid value. It must be an integer number."));
    }
  }

  private void validateParticipantsPerMatchCountRange(int count) {
    if (PARTICIPANTS_PER_MATCH_MIN_COUNT > count || count > PARTICIPANTS_PER_MATCH_MAX_COUNT) {
      throw new InvalidPropertyException(String.format("The '%s' property is out of range. It must be between %d and %d. Please check the application config file.", PARTICIPANTS_PER_MATCH_COUNT, PARTICIPANTS_PER_MATCH_MIN_COUNT, PARTICIPANTS_PER_MATCH_MAX_COUNT));
    }
  }

  private void validateShouldDisplayTime() {
    try {
      properties.shouldDisplayTime();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(DISPLAY_TIME, ex.getMessage()));
    }
  }

  private void validatePropertiesRequiredForDisplayTime() {
    validateRequired(requiredForDisplayTime);
    validateMatchesStartTime();
    validateSingleMatchDuration();
  }

  private void validateMatchesStartTime() {
    try {
      properties.getMatchesStartTime();
    } catch (IllegalArgumentException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(MATCHES_START_TIME, "Invalid time format. HH:MM is a valid format."));
    }
  }

  private void validateSingleMatchDuration() {
    try {
      int value = properties.getSingleMatchDuration();
      mustBeNotNegative(value);
    } catch (NumberFormatException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(SINGLE_MATCH_DURATION, "Invalid value. It must be an integer number."));
    }
  }

  private void mustBeNotNegative(int value) {
    if (value < 0) {
      throw new InvalidPropertyException(String.format("The '%s' property must not be negative. Please check the application config file.", SINGLE_MATCH_DURATION));
    }
  }

  private void validatePropertiesRequiredForTeamsDraw() {
    validateRequired(requiredForTeamsDraw);
    validateTeamsFile();
    validateTeamsRankComparison();
    validateTeamsRankThreshold();
    validateTeamTypesToSkip();
    validateShouldAllowMixedMatches();
  }

  private void validateTeamsFile() {
    validateFile(properties.getTeamsFile(), TEAMS_FILE_PATH);
  }

  private void validateTeamsRankComparison() {
    try {
      properties.getTeamsRankComparison();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(TEAMS_RANK_COMPARISON, ex.getMessage()));
    }
  }

  private void validateTeamsRankThreshold() {
    try {
      properties.getTeamsRankThreshold();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(TEAMS_RANK_THRESHOLD, ex.getMessage()));
    }
  }

  private void validateTeamTypesToSkip() {
    try {
      properties.getTeamTypesToSkip();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(TEAMS_TYPES_TO_SKIP, ex.getMessage()));
    }
  }

  private void validateShouldAllowMixedMatches() {
    try {
      properties.shouldAllowMixedMatches();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(ALLOW_MIXED_MATCHES, ex.getMessage()));
    }
  }

  private void validatePropertiesRequiredForEmail() {
    validateRequired(requiredForEmail);
    validateMailSMTP();
    validateEmailSubject();
    validateSender();
    validateAdminEmail();
    validatePort();
  }

  private void validateMailSMTP() {
    Properties.MailSMTP mailSMTP = properties.new MailSMTP();
  }

  private void validateEmailSubject() {
    String subject = properties.getEmailSubject();
    if (StringUtils.isBlank(subject)) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(MAIL_SUBJECT, "An email subject cannot be empty."));
    }
  }

  private void validateSender() {
    try {
      InternetAddress address = properties.getSender();
      if (address != null) {
        address.validate();
      }
    } catch (AddressException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(MAIL_SENDER_EMAIL, "Invalid email address."));
    }
  }

  private void validateAdminEmail() {
    try {
      String email = properties.getAdminEmailAddress();
      new InternetAddress(email).validate();
    } catch (AddressException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(ADMIN_EMAIL, "Invalid email address."));
    }
  }

  private void validatePort() {
    try {
      int port = properties.new MailSMTP().getPort();
      mustBeNotNegative(port);
    } catch (NumberFormatException ex) {
      throw new InvalidPropertyException("Mail server port is invalid. Please check the '" + PORT + "' property in the application config file.");
    }
  }

  private static String pleaseCheckTheProperty(String propertyName, String prefix) {
    return String.format("%s Please check the '%s' property in the application config file.", prefix, propertyName);
  }
}
