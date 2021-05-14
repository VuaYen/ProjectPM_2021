package miu.edu.product.sender;

import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService{
    @Override
    public Object sendMailA() {
        String from= new String() ;
        String host =new String()  ;
        String port =new String();
        String user = new String();
        String password = new String();
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.user", user);
        properties.setProperty("mail.smtp.password", password);
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set from email address
            message.setFrom(new InternetAddress(from, "Group3CS490PM_2021"));
            // Set email subject
            message.setSubject("Mail Subject");
            // Initiate body of email address
            BodyPart messageBodyPart = new MimeBodyPart();
            // Set email body
            messageBodyPart.setText("This is message body");
            // Initiate class to send media
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            // Set path for attachment
            String filename = "ubuntu.jpg";
            // Bind the attachment
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            // Set file name into email
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            // Set configs for sending email
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            // Send email
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("done");
            return "Email Sent! Check Inbox!";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object sendMailMulti() {
        // Set required configs
        String from= new String() ;
        String host =new String()  ;
        String port =new String();
        String user = new String();
        String password = new String();
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.user", user);
        properties.setProperty("mail.smtp.password", password);
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set from email address
            message.setFrom(new InternetAddress(from, "Group3CS490PM_2021"));
            // Set the recipient email address
            ArrayList<String> recipients = new ArrayList<>();
            recipients.add("poolsaliya@gmail.com");
            recipients.add("salithachathuranga94@gmail.com");
            InternetAddress[] addresses = new InternetAddress[recipients.size()];
            for (int i = 0; i < recipients.size(); i++) {
                addresses[i] = new InternetAddress(recipients.get(i));
            }
            message.addRecipients(MimeMessage.RecipientType.TO, addresses);
            // Set email subject
            message.setSubject("Mail Subject");
            // Initiate body of email address
            BodyPart messageBodyPart = new MimeBodyPart();
            // Set email body
            messageBodyPart.setText("This is message body");
            // Initiate class to send media
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            // Set path for attachment
            String filename = "ubuntu.jpg";
            // Bind the attachment
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            // Set file name into email
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            // Set configs for sending email
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            // Send email
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("done");
            return "Email Sent! Check Inbox!";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object sendMailHTML() {
        String from= new String() ;
        String host =new String()  ;
        String port =new String();
        String user = new String();
        String password = new String();
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.user", user);
        properties.setProperty("mail.smtp.password", password);
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set from email address
            message.setFrom(new InternetAddress(from, "Group3CS490PM_2021"));
            message.setSubject("Mail Subject");
            // Set email body
            message.setContent("<h1>hello</h1>","text/html");
            // Set configs for sending email
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            // Send email
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("done");
            return "Email Sent! Check Inbox!";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object sendMailCC() {
        String cc = "cc_mail@gmail.com";
//        message.addRecipient(MimeMessage.RecipientType.CC, new InternetAddress(cc))
        return null;
    }

    @Override
    public Object sendMail(String to) {
        // Set the recipient email address
        String from= new String() ;
        String host =new String()  ;
        String port =new String();
        String user = new String();
        String password = new String();
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.user", user);
        properties.setProperty("mail.smtp.password", password);
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set from email address
            message.setFrom(new InternetAddress(from, "Group3CS490PM_2021"));
            // Set the recipient email address
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            // Set email subject
            message.setSubject("Test mail");
            // Set email body
//            message.setText("This is message body test");
            message.setContent("<h1>hello</h1>","text/html");
            // Set configs for sending email
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            // Send email
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("done");
            return "Email Sent! Check Inbox!";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object sendMail(String to, String content) {
        String from = "thephamec@gmail.com";
        String host = "smtp.gmail.com";
        String port = "587";
        String user = "thephamec@gmail.com";
        String password = "Hello@123456789";

        // Set system properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.user", user);
        properties.setProperty("mail.smtp.password", password);
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set from email address
            message.setFrom(new InternetAddress(from, "Group3CS490PM_2021"));
            // Set the recipient email address
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            // Set email subject
            message.setSubject("Order Information ");
            // Set email body
//            message.setText("This is message body test");
            message.setContent("<h1>This is your Order</h1>" +
                    content,"text/html");
            // Set configs for sending email
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            // Send email
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("done");
            return "Email Sent! Check Inbox!";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
