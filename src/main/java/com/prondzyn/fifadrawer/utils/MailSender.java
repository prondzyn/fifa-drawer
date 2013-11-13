package com.prondzyn.fifadrawer.utils;

import com.prondzyn.fifadrawer.Constants;
import com.prondzyn.fifadrawer.entities.Properties;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Set;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

  private final Properties appProperties;

  public MailSender(Properties applicationProperties) {
    this.appProperties = applicationProperties;
  }

  public void send(String message, Set<String> recipientsEmails) throws MessagingException, UnsupportedEncodingException {

    java.util.Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", appProperties.getMailHost());
    properties.setProperty("charset", Constants.DEFAULT_CHARSET);
    Session session = Session.getDefaultInstance(properties);

    InternetAddress[] to = prepareRecipients(recipientsEmails);

    MimeMessage mimeMsg = new MimeMessage(session);
    mimeMsg.setFrom(appProperties.getSender());
    mimeMsg.addRecipients(Message.RecipientType.TO, to);
    mimeMsg.setReplyTo(to);
    mimeMsg.setSubject(appProperties.getEmailSubject(), Constants.DEFAULT_CHARSET);
    mimeMsg.setText(message, Constants.DEFAULT_CHARSET);
    mimeMsg.setSentDate(new Date());

    String adminEmail = appProperties.getAdminEmailAddress();
    if (!recipientsEmails.contains(adminEmail)) {
      mimeMsg.setRecipient(Message.RecipientType.CC, new InternetAddress(adminEmail));
    }

    Transport.send(mimeMsg);
  }

  private InternetAddress[] prepareRecipients(Set<String> recipientsEmails) throws AddressException {
    InternetAddress[] addresses = new InternetAddress[recipientsEmails.size()];
    int i = 0;
    for (String email : recipientsEmails) {
      addresses[i] = new InternetAddress(email);
      i += 1;
    }
    return addresses;
  }
}
