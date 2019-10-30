package seedu.address.model;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.SendMailCommand;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Logger;

public class Mailer {
    public static void sendEmail(String from, String password, String to, String sub, String msg) throws Exception {

        final Logger logger = LogsCenter.getLogger(SendMailCommand.class);

        //Get properties object
        Properties props = new Properties();

        //for gmail server need to set this to true (need to provide authentication)
        props.put("mail.smtp.auth", "true");

        //in gmail have to provide true value for this key
        props.put("mail.smtp.starttls.enable", "true");

        //the email host which in this case is gmail
        props.put("mail.smtp.host", "smtp.gmail.com");

        //the port is 587 for gmail
        props.put("mail.smtp.port", "587");

        //get Session => to log in using the email address
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        //compose message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(sub);
        message.setText(msg);
        //send message
        Transport.send(message); //throws an exception
    }
}
