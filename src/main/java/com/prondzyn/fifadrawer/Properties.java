package com.prondzyn.fifadrawer;

import static com.prondzyn.fifadrawer.Properties.Key.*;
import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.TeamType;
import com.prondzyn.fifadrawer.entities.ComparisonType;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.utils.BooleanUtils;
import com.prondzyn.fifadrawer.utils.RandomUtils;
import com.prondzyn.fifadrawer.utils.StringUtils;
import com.prondzyn.fifadrawer.validators.PropertiesValidator;
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
import javax.mail.internet.InternetAddress;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Properties extends java.util.Properties {

  public static final String DEFAULT_CHARSET = "utf-8";
  public static final String DEFAULT_TIME_FORMAT = "HH:mm";

  public static class Key {

    public static final String PRINT_CONSOLE = "print.console";
    public static final String PRINT_EMAIL = "print.email";

    public static final String MAIL_SUBJECT = "mail.subject";
    public static final String MAIL_SENDER_EMAIL = "mail.sender.email";
    public static final String MAIL_SENDER_NAMES = "mail.sender.names";

    public static final String ADMIN_EMAIL = "admin.email";

    public static final String DRAW_PARTICIPANTS = "draw.participants";
    public static final String PARTICIPANTS_FILE_PATH = "file.path.participants";

    public static final String PARTICIPANTS_PER_MATCH_COUNT = "participants.per.match.count";

    public static final String DISPLAY_TIME = "time.display";
    public static final String MATCHES_START_TIME = "matches.start.time";
    public static final String SINGLE_MATCH_DURATION = "single.match.duration";

    public static final String DRAW_TEAMS = "draw.teams";
    public static final String TEAMS_FILE_PATH = "file.path.teams";

    public static final String TEAMS_RANK_THRESHOLD = "teams.rank.threshold";
    public static final String TEAMS_RANK_COMPARISON = "teams.rank.comparison";

    public static final String TEAMS_TYPES_TO_SKIP = "teams.skipped.types";
    public static final String TEAMS_COUNTRIES_TO_SKIP = "teams.skipped.countries";
    public static final String TEAMS_LEAGUES_TO_SKIP = "teams.skipped.leagues";
    public static final String TEAMS_NAMES_TO_SKIP = "teams.skipped.names";

    public static final String ALLOW_MIXED_MATCHES = "teams.allow.mixed.matches";
  }

  private final File directory;
  private final PropertiesValidator validator;

  public Properties(String filepath) {
    this.directory = new File(filepath).getParentFile();
    this.validator = new PropertiesValidator(this);
  }

  @Override
  public synchronized void load(InputStream inStream) throws IOException {
    super.load(inStream);
    validator.validate();
  }

  @Override
  public synchronized void load(Reader reader) throws IOException {
    super.load(reader);
    validator.validate();
  }

  @Override
  public synchronized void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
    super.loadFromXML(in);
    validator.validate();
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

  public class MailSMTP {

    private static final String PREFIX = "mail.smtp.";

    public static final String HOST = PREFIX + "host";
    public static final String PORT = PREFIX + "port";
    public static final String USERNAME = PREFIX + "user";
    public static final String PASSWORD = PREFIX + "password";

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
  }
}
