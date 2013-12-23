package com.prondzyn.fifadrawer.mail;

import com.prondzyn.fifadrawer.Properties;
import static com.prondzyn.fifadrawer.Properties.DEFAULT_CHARSET;
import com.prondzyn.fifadrawer.lang.MailException;
import com.prondzyn.fifadrawer.utils.CopyUtils;
import java.util.Date;
import java.util.Set;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

  private static final String SMTP_PROTOCOL = "smtp";

  private final Properties appProperties;

  public MailSender(Properties properties) {
    this.appProperties = properties;
  }

  public void send(String message, Set<String> recipientsEmails) {

    try {

      java.util.Properties properties = System.getProperties();

      properties.setProperty("charset", DEFAULT_CHARSET);

      Properties.MailSMTP smtpProps = appProperties.new MailSMTP();
      smtpProps.copyTo(properties);

      Session session = Session.getDefaultInstance(properties);

      InternetAddress[] to = prepareRecipients(recipientsEmails, appProperties.getAdminEmailAddress());

      MimeMessage mimeMsg = new MimeMessage(session);
      mimeMsg.setFrom(appProperties.getSender());
      mimeMsg.addRecipients(Message.RecipientType.TO, to);
      mimeMsg.setReplyTo(to);
      mimeMsg.setSubject(appProperties.getEmailSubject(), DEFAULT_CHARSET);
      mimeMsg.setText(message, DEFAULT_CHARSET);
      mimeMsg.setSentDate(new Date());

      Transport transport = session.getTransport(SMTP_PROTOCOL);
      transport.connect(smtpProps.getHost(), smtpProps.getPort(), smtpProps.getUsername(), smtpProps.getPassword());
      transport.sendMessage(mimeMsg, to);
      transport.close();

    } catch (AuthenticationFailedException ex) {
      throw new MailException(invalidMailCredentialsMessage(), ex);
    } catch (SendFailedException ex) {
      throw new MailException("Invalid email address found. Please check all entered emails in the application config file and in the participants file.", ex);
    } catch (MessagingException ex) {
      String msg = (ex.getMessage() == null) ? couldNotConnectToMailServer() : ex.getMessage();
      throw new MailException(msg, ex);
    }
  }

  private InternetAddress[] prepareRecipients(Set<String> recipientsEmails, String adminEmailAddress) throws AddressException {
    Set<String> recipients = CopyUtils.copy(recipientsEmails);
    recipients.add(adminEmailAddress);
    InternetAddress[] addresses = new InternetAddress[recipients.size()];
    int i = 0;
    for (String email : recipients) {
      addresses[i] = new InternetAddress(email);
      i += 1;
    }
    return addresses;
  }

  private static String couldNotConnectToMailServer() {
    return pleaseCheckTheProperties("Could not connect to the mail server.");
  }

  private static String invalidMailCredentialsMessage() {
    return pleaseCheckTheProperties("Mail server username or password is invalid.");
  }

  private static String pleaseCheckTheProperties(String prefix) {
    return String.format("%s Please check the properties in the application config file.", prefix);
  }
}
