package com.prondzyn.fifadrawer.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

  private static final String ADMIN_EMAIL = "TODO";

  private static List<String> SENDERS;
  static {
    SENDERS = new ArrayList<String>();
    SENDERS.add("Lionel Messi");
    SENDERS.add("Cristiano Ronaldo");
    SENDERS.add("Kaka");
    SENDERS.add("Fabio Cannavaro");
    SENDERS.add("Ronaldinho");
    SENDERS.add("Zinedine Zidane");
    SENDERS.add("Ronaldo");
    SENDERS.add("Luis Figo");
    SENDERS.add("Andrij Szewczenko");
    SENDERS.add("Pavel Nedved");
    SENDERS.add("Michael Owen");
  }

  public static void send(String message, Set<String> recipientsEmails) {

    String host = "TODO";

    Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", host);
    Session session = Session.getDefaultInstance(properties);

    try {

      InternetAddress from = new InternetAddress("TODO", RandomUtils.getRandomItem(SENDERS));

      InternetAddress[] to = prepareRecipients(recipientsEmails);

      MimeMessage mimeMsg = new MimeMessage(session);
      mimeMsg.setFrom(from);
      mimeMsg.addRecipients(Message.RecipientType.TO, to);
      if (!recipientsEmails.contains(ADMIN_EMAIL)) {
        mimeMsg.setRecipient(Message.RecipientType.CC, new InternetAddress(ADMIN_EMAIL));
      }
      mimeMsg.setReplyTo(to);
      mimeMsg.setSubject("TODO");
      mimeMsg.setText(message);

      Transport.send(mimeMsg);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private static InternetAddress[] prepareRecipients(Set<String> recipientsEmails) throws AddressException {
    InternetAddress[] addresses = new InternetAddress[recipientsEmails.size()];
    int i = 0;
    for (String email : recipientsEmails) {
      addresses[i] = new InternetAddress(email);
      i += 1;
    }
    return addresses;
  }
}
