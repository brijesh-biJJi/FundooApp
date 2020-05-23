package com.bridgelabz.fundoonotes.utility;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Slf4j
@Component
public class MailServiceProvider {

	
	private static final Logger log = LoggerFactory.getLogger(MailServiceProvider.class);

	/**
	 * This method used to create a Session by passing Properties and Authenticator as an argument 
	 * @param toEmail
	 * @param subject
	 * @param msg
	 */
	public static void sendEmail(String toEmail, String subject, String msg) {
		String fromEmail = System.getenv("email");
		String password = System.getenv("password");
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		
		Authenticator auth = new Authenticator() 
		{
			/**
			 * Authenticating the password
			 */
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session ses = Session.getInstance(prop, auth);
		send(ses, fromEmail, toEmail, subject, msg);
	}
	
	/**
	 * This method is used to Send the Email to the specified User
	 * @param session
	 * @param fromEmail
	 * @param toEmail
	 * @param subject
	 * @param msg
	 */
	private static void send(Session session, String fromEmail, String toEmail, String subject, String msg) 
	{
		try 
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail, "Brijesh Kanchan"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			message.setText(msg);
			log.info("Mime Message: "+message);
			Transport.send(message);
		}
		catch (Exception e) 
		{
			log.info(e.getMessage());
		}
	}
}
