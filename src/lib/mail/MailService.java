package lib.mail;


import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import lib.struc.MailRequest;

public class MailService {

    public void sendEmail(MailRequest request) throws MessagingException {
        Properties props = MailConfig.getMailProperties();

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailConfig.USERNAME, MailConfig.PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(MailConfig.USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(request.getTo()));
        message.setSubject(request.getSubject());

        if (request.isHtml()) {
            message.setContent(request.getBody(), "text/html; charset=utf-8");
        } else {
            message.setText(request.getBody());
        }

        Transport.send(message);
    }
}
