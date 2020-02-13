package com.bridgelabz.fundoonotes.utility;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailServiceProvider {

	@Autowired
	private static JavaMailSender javaMailSender;
	
	public static void sendEmail(String toEmail, String subject, String msg) {
		Gmail gmail = new Gmail();
		String myEmail = gmail.getEmail();
		String password = gmail.getPass();
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		Authenticator auth = new Authenticator() 
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myEmail, password);
			}

		};
		
		Session ses = Session.getInstance(properties, auth);
		send(ses, myEmail, toEmail, subject, msg);
	}
	
	private static void send(Session session, String fromEmail, String toEmail, String subject, String msg) {
		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(fromEmail, "BrijeshKanchan"));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(msg);
			Transport.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception..!");
		}
	}
}
