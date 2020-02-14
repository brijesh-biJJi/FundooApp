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
		sendEmail sendEmail = new sendEmail();
		String myEmail = sendEmail.getEmail();
		String password = sendEmail.getPass();
		
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		
		Authenticator auth = new Authenticator() 
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myEmail, password);
			}

		};
		
		Session ses = Session.getInstance(prop, auth);
		send(ses, myEmail, toEmail, subject, msg);
	}
	
	private static void send(Session session, String fromEmail, String toEmail, String subject, String msg) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail, "Brijesh Kanchan"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			message.setText(msg);
			System.out.println("Mime Message"+message);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception..!");
		}
	}
}
