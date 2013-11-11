package com.prondzyn.fifadrawer.entities;

import com.prondzyn.fifadrawer.Constants;
import com.prondzyn.fifadrawer.lang.MissingPropertyException;
import com.prondzyn.fifadrawer.utils.RandomUtils;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
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

  public void validate() {
    for (String req : required) {
      String value = getProperty(req);
      if (value == null) {
        throw new MissingPropertyException("'" + req + "' property not found!");
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
    if (value == null) {
      return null;
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

  public BigDecimal getTeamsRankThreshold() {
    BigDecimal threshold = getBigDecimalProperty(TEAMS_RANK_THRESHOLD);
    threshold.setScale(1);
    return threshold;
  }

  private BigDecimal getBigDecimalProperty(String key) {
    String value = getProperty(key);
    return new BigDecimal(value);
  }
  
  public String getTeamsRankComparision() {
    return getProperty(TEAMS_RANK_COMPARISION);
  }
}
