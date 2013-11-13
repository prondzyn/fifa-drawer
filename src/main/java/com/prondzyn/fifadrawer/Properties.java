package com.prondzyn.fifadrawer;

import com.prondzyn.fifadrawer.entities.Rank;
import com.prondzyn.fifadrawer.entities.TeamType;
import com.prondzyn.fifadrawer.entities.ComparisionType;
import com.prondzyn.fifadrawer.Constants;
import com.prondzyn.fifadrawer.lang.MissingPropertyException;
import com.prondzyn.fifadrawer.utils.RandomUtils;
import com.prondzyn.fifadrawer.utils.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Set;
import javax.mail.internet.InternetAddress;

public class Properties extends java.util.Properties {

  private static final String MAIL_HOST = "mail.host";
  private static final String MAIL_SUBJECT = "mail.subject";
  private static final String MAIL_SENDER_EMAIL = "mail.sender.email";
  private static final String MAIL_SENDER_NAMES = "mail.sender.names";

  private static final String ADMIN_EMAIL = "admin.email";

  private static final String PARTICIPANTS_FILE_PATH = "file.path.participants";
  private static final String TEAMS_FILE_PATH = "file.path.teams";

  private static final String TEAMS_RANK_THRESHOLD = "teams.rank.threshold";
  private static final String TEAMS_RANK_COMPARISION = "teams.rank.comparison";
  
  private static final String TEAMS_TYPES_TO_SKIP = "teams.skipped.types";
  private static final String TEAMS_COUNTRIES_TO_SKIP = "teams.skipped.countries";
  private static final String TEAMS_LEAGUES_TO_SKIP = "teams.skipped.leagues";

  private static final Set<String> required;

  static {
    required = new HashSet<>();
    required.add(MAIL_HOST);
    required.add(MAIL_SUBJECT);
    required.add(MAIL_SENDER_EMAIL);
    required.add(MAIL_SENDER_NAMES);
    required.add(ADMIN_EMAIL);
    required.add(PARTICIPANTS_FILE_PATH);
    required.add(TEAMS_FILE_PATH);
    required.add(TEAMS_RANK_THRESHOLD);
    required.add(TEAMS_RANK_COMPARISION);
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

  private void validate() {
    for (String req : required) {
      String value = getProperty(req);
      if (value == null) {
        throw new MissingPropertyException("Property '" + req + "' not found.");
      }
    }
  }

  public String getParticipantsFilePath() {
    return getProperty(PARTICIPANTS_FILE_PATH);
  }

  public String getTeamsFilePath() {
    return getProperty(TEAMS_FILE_PATH);
  }

  public InternetAddress getSender() throws UnsupportedEncodingException {
    List<String> senders = getArrayProperty(MAIL_SENDER_NAMES);
    return new InternetAddress(getProperty(MAIL_SENDER_EMAIL), RandomUtils.getRandomItem(senders), Constants.DEFAULT_CHARSET);
  }

  private List<String> getArrayProperty(String key) {
    String value = getProperty(key);
    if (StringUtils.isBlank(value)) {
      return new ArrayList<>();
    }
    String[] result = value.split(",");
    for (int i = 0; i < result.length; i++) {
      result[i] = result[i].trim();
    }
    return Arrays.asList(result);
  }

  public String getMailHost() {
    return getProperty(MAIL_HOST);
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

  public ComparisionType getTeamsRankComparision() {
    String value = getProperty(TEAMS_RANK_COMPARISION);
    return ComparisionType.parse(value);
  }

  public Set<TeamType> getTeamTypesToSkip() {
    Set<TeamType> types = new HashSet<>();
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

  private Set<String> getSetProperty(String key) {
    return new HashSet<>(getArrayProperty(key));
  }
}
