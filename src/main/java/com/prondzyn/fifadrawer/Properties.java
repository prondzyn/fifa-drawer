package com.prondzyn.fifadrawer;

import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.TeamType;
import com.prondzyn.fifadrawer.entities.ComparisonType;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.lang.InvalidPropertyException;
import com.prondzyn.fifadrawer.lang.MissingPropertyException;
import com.prondzyn.fifadrawer.lang.ParseException;
import com.prondzyn.fifadrawer.utils.BooleanUtils;
import com.prondzyn.fifadrawer.utils.RandomUtils;
import com.prondzyn.fifadrawer.utils.StringUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Properties extends java.util.Properties {

  public static final String DEFAULT_CHARSET = "utf-8";
  public static final String DEFAULT_TIME_FORMAT = "HH:mm";

  private static final int PARTICIPANTS_PER_MATCH_MIN_COUNT = 2;
  private static final int PARTICIPANTS_PER_MATCH_MAX_COUNT = 20;

  private static final String PRINT_CONSOLE = "print.console";
  private static final String PRINT_EMAIL = "print.email";

  private static final String MAIL_SUBJECT = "mail.subject";
  private static final String MAIL_SENDER_EMAIL = "mail.sender.email";
  private static final String MAIL_SENDER_NAMES = "mail.sender.names";

  private static final String ADMIN_EMAIL = "admin.email";

  private static final String DRAW_PARTICIPANTS = "draw.participants";
  private static final String PARTICIPANTS_FILE_PATH = "file.path.participants";

  private static final String PARTICIPANTS_PER_MATCH_COUNT = "participants.per.match.count";

  private static final String DISPLAY_TIME = "time.display";
  private static final String MATCHES_START_TIME = "matches.start.time";
  private static final String SINGLE_MATCH_DURATION = "single.match.duration";

  private static final String DRAW_TEAMS = "draw.teams";
  private static final String TEAMS_FILE_PATH = "file.path.teams";

  private static final String TEAMS_RANK_THRESHOLD = "teams.rank.threshold";
  private static final String TEAMS_RANK_COMPARISON = "teams.rank.comparison";

  private static final String TEAMS_TYPES_TO_SKIP = "teams.skipped.types";
  private static final String TEAMS_COUNTRIES_TO_SKIP = "teams.skipped.countries";
  private static final String TEAMS_LEAGUES_TO_SKIP = "teams.skipped.leagues";
  private static final String TEAMS_NAMES_TO_SKIP = "teams.skipped.names";

  private static final String ALLOW_MIXED_MATCHES = "teams.allow.mixed.matches";

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
    requiredForEmail.add(MailSMTP.HOST);
    requiredForEmail.add(MailSMTP.PORT);
    requiredForEmail.add(MailSMTP.USERNAME);
    requiredForEmail.add(MailSMTP.PASSWORD);
    requiredForEmail.add(MAIL_SUBJECT);
    requiredForEmail.add(MAIL_SENDER_EMAIL);
    requiredForEmail.add(MAIL_SENDER_NAMES);
    requiredForEmail.add(ADMIN_EMAIL);
  }

  private final File directory;

  public Properties(String filepath) {
    this.directory = new File(filepath).getParentFile();
  }

  @Override
  public synchronized void load(InputStream inStream) throws IOException {
    super.load(inStream);
    validate();
  }

  @Override
  public synchronized void load(Reader reader) throws IOException {
    super.load(reader);
    validate();
  }

  @Override
  public synchronized void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
    super.loadFromXML(in);
    validate();
  }

  public boolean printDrawResultToConsole() {
    return BooleanUtils.parse(getProperty(PRINT_CONSOLE));
  }

  public boolean sendDrawResultByEmail() {
    return BooleanUtils.parse(getProperty(PRINT_EMAIL));
  }

  public boolean shouldDrawParticipants() {
    return BooleanUtils.parse(getProperty(DRAW_PARTICIPANTS));
  }

  public File getParticipantsFile() {
    return new File(directory, getProperty(PARTICIPANTS_FILE_PATH));
  }

  public int getParticipantsPerMatchCount() {
    return Integer.parseInt(getProperty(PARTICIPANTS_PER_MATCH_COUNT));
  }

  public boolean shouldDisplayTime() {
    return BooleanUtils.parse(getProperty(DISPLAY_TIME));
  }

  public DateTime getMatchesStartTime() {
    return DateTime.parse(getProperty(MATCHES_START_TIME), DateTimeFormat.forPattern(DEFAULT_TIME_FORMAT));
  }

  public int getSingleMatchDuration() {
    return Integer.parseInt(getProperty(SINGLE_MATCH_DURATION));
  }

  public boolean shouldDrawTeams() {
    return BooleanUtils.parse(getProperty(DRAW_TEAMS));
  }

  public File getTeamsFile() {
    return new File(directory, getProperty(TEAMS_FILE_PATH));
  }

  public InternetAddress getSender() {
    try {
      String email = getProperty(MAIL_SENDER_EMAIL);
      String name = RandomUtils.getRandomItem(getArrayProperty(MAIL_SENDER_NAMES));
      if (StringUtils.isBlank(email) || StringUtils.isBlank(name)) {
        return null;
      }
      return new InternetAddress(email, name, DEFAULT_CHARSET);
    } catch (UnsupportedEncodingException ex) {
      throw new ApplicationException(ex);
    }
  }

  private List<String> getArrayProperty(String key) {
    String value = getProperty(key);
    return StringUtils.isBlank(value) ? new ArrayList<String>() : StringUtils.split(value);
  }

  public String getAdminEmailAddress() {
    return getProperty(ADMIN_EMAIL);
  }

  public String getEmailSubject() {
    return getProperty(MAIL_SUBJECT);
  }

  public Rank getTeamsRankThreshold() {
    return Rank.parse(getProperty(TEAMS_RANK_THRESHOLD));
  }

  public ComparisonType getTeamsRankComparison() {
    String value = getProperty(TEAMS_RANK_COMPARISON);
    return ComparisonType.parse(value);
  }

  public Set<TeamType> getTeamTypesToSkip() {
    Set<TeamType> types = new LinkedHashSet<>();
    Set<String> set = getSetProperty(TEAMS_TYPES_TO_SKIP);
    for (String string : set) {
      types.add(TeamType.parse(string));
    }
    return types;
  }

  public Set<String> getCountriesToSkip() {
    return getSetProperty(TEAMS_COUNTRIES_TO_SKIP);
  }

  public Set<String> getLeaguesToSkip() {
    return getSetProperty(TEAMS_LEAGUES_TO_SKIP);
  }

  public Set<String> getNamesToSkip() {
    return getSetProperty(TEAMS_NAMES_TO_SKIP);
  }

  private Set<String> getSetProperty(String key) {
    return new LinkedHashSet<>(getArrayProperty(key));
  }

  public boolean shouldAllowMixedMatches() {
    String allow = getProperty(ALLOW_MIXED_MATCHES);
    return StringUtils.isNotBlank(allow) ? BooleanUtils.parse(allow) : false;
  }

  private void validate() {

    validateRequired(required);

    validatePrintDrawResultToConsole();
    validateSendDrawResultByEmail();
    validatePrinting();

    validateShouldDrawParticipants();
    validateShouldDrawTeams();
    validateDrawIndicators();

    if (shouldDrawParticipants()) {

      validateRequired(requiredForParticipantsDraw);

      validateParticipantsFile();

      validateParticipantsPerMatchCount();

      validateShouldDisplayTime();
      if (shouldDisplayTime()) {

        validateRequired(requiredForDisplayTime);

        validateMatchesStartTime();
        validateSingleMatchDuration();
      }
    }

    if (shouldDrawTeams()) {

      validateRequired(requiredForTeamsDraw);

      validateTeamsFile();

      validateTeamsRankComparison();
      validateTeamsRankThreshold();

      validateTeamTypesToSkip();

      validateShouldAllowMixedMatches();
    }

    if (sendDrawResultByEmail()) {
      validateRequired(requiredForEmail);

      validateMailSMTP();

      validateEmailSubject();

      validateSender();

      validateAdminEmail();
    }
  }

  private void validateRequired(Set<String> requiredProperties) {
    for (String req : requiredProperties) {
      String value = getProperty(req);
      if (value == null) {
        throw new MissingPropertyException("Property '" + req + "' not found. Please check the application config file.");
      }
    }
  }

  private void validatePrintDrawResultToConsole() {
    try {
      printDrawResultToConsole();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(PRINT_CONSOLE, ex.getMessage()));
    }
  }

  private void validateSendDrawResultByEmail() {
    try {
      sendDrawResultByEmail();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(PRINT_EMAIL, ex.getMessage()));
    }
  }

  private void validatePrinting() {
    if (!printDrawResultToConsole() && !sendDrawResultByEmail()) {
      throw new ApplicationException("Both printing methods are disabled. Please enable at least one printing method in the application config file.");
    }
  }

  private void validateShouldDrawParticipants() {
    try {
      shouldDrawParticipants();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(DRAW_PARTICIPANTS, ex.getMessage()));
    }
  }

  private void validateShouldDrawTeams() {
    try {
      shouldDrawTeams();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(DRAW_TEAMS, ex.getMessage()));
    }
  }

  private void validateDrawIndicators() {
    if (!shouldDrawParticipants() && !shouldDrawTeams()) {
      throw new ApplicationException("Both draw types are turned off. Please turn on at least one draw type in the application config file.");
    }
  }

  private void validateParticipantsFile() {
    validateFile(getParticipantsFile(), PARTICIPANTS_FILE_PATH);
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
      int count = getParticipantsPerMatchCount();
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
      shouldDisplayTime();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(DISPLAY_TIME, ex.getMessage()));
    }
  }

  private void validateMatchesStartTime() {
    try {
      getMatchesStartTime();
    } catch (IllegalArgumentException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(MATCHES_START_TIME, "Invalid time format. HH:MM is a valid format."));
    }
  }

  private void validateSingleMatchDuration() {
    try {
      int value = getSingleMatchDuration();
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

  private void validateTeamsFile() {
    validateFile(getTeamsFile(), TEAMS_FILE_PATH);
  }

  private void validateTeamsRankComparison() {
    try {
      getTeamsRankComparison();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(TEAMS_RANK_COMPARISON, ex.getMessage()));
    }
  }

  private void validateTeamsRankThreshold() {
    try {
      getTeamsRankThreshold();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(TEAMS_RANK_THRESHOLD, ex.getMessage()));
    }
  }

  private void validateTeamTypesToSkip() {
    try {
      getTeamTypesToSkip();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(TEAMS_TYPES_TO_SKIP, ex.getMessage()));
    }
  }

  private void validateShouldAllowMixedMatches() {
    try {
      shouldAllowMixedMatches();
    } catch (ParseException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(ALLOW_MIXED_MATCHES, ex.getMessage()));
    }
  }

  private void validateMailSMTP() {
    MailSMTP mailSMTP = new MailSMTP();
  }

  private void validateEmailSubject() {
    String subject = getEmailSubject();
    if (StringUtils.isBlank(subject)) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(MAIL_SUBJECT, "An email subject cannot be empty."));
    }
  }

  private void validateSender() {
    try {
      InternetAddress address = getSender();
      if (address != null) {
        address.validate();
      }
    } catch (AddressException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(MAIL_SENDER_EMAIL, "Invalid email address."));
    }
  }

  private void validateAdminEmail() {
    try {
      String email = getAdminEmailAddress();
      new InternetAddress(email).validate();
    } catch (AddressException ex) {
      throw new InvalidPropertyException(pleaseCheckTheProperty(ADMIN_EMAIL, "Invalid email address."));
    }
  }

  public static String couldNotConnectToMailServer() {
    return pleaseCheckTheProperties("Could not connect to the mail server.");
  }

  public static String invalidMailCredentialsMessage() {
    return pleaseCheckTheProperties("Mail server username or password is invalid.");
  }

  private static String pleaseCheckTheProperty(String propertyName, String prefix) {
    return String.format("%s Please check the '%s' property in the application config file.", prefix, propertyName);
  }

  private static String pleaseCheckTheProperties(String prefix) {
    return String.format("%s Please check the properties in the application config file.", prefix);
  }

  public class MailSMTP {

    private static final String PREFIX = "mail.smtp.";

    private static final String HOST = PREFIX + "host";
    private static final String PORT = PREFIX + "port";
    private static final String USERNAME = PREFIX + "user";
    private static final String PASSWORD = PREFIX + "password";

    public MailSMTP() {
      validate();
    }

    public String getHost() {
      return getProperty(HOST);
    }

    public int getPort() {
      return Integer.valueOf(getProperty(PORT));
    }

    public String getUsername() {
      return getProperty(USERNAME);
    }

    public String getPassword() {
      return getProperty(PASSWORD);
    }

    public void copyTo(java.util.Properties properties) {
      for (Map.Entry<String, String> entry : getAll().entrySet()) {
        properties.setProperty(entry.getKey(), entry.getValue());
      }
    }

    private Map<String, String> getAll() {
      Map<String, String> properties = new LinkedHashMap<>();
      for (Object key : keySet()) {
        String name = key.toString();
        if (name.startsWith(PREFIX)) {
          properties.put(name, getProperty(name));
        }
      }
      return properties;
    }

    private void validate() {
      validatePort();
    }

    private void validatePort() {
      try {
        int port = getPort();
        mustBeNotNegative(port);
      } catch (NumberFormatException ex) {
        throw new InvalidPropertyException("Mail server port is invalid. Please check the '" + PORT + "' property in the application config file.");
      }
    }

  }
}
