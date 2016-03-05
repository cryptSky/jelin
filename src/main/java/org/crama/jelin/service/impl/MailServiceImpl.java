package org.crama.jelin.service.impl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.crama.jelin.model.Constants;
import org.crama.jelin.model.Settings;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;
import org.crama.jelin.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailServiceImpl implements MailService {
	
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	private void sendEmail(Settings settings, String from, String to, String subject, String body) {
		
		
		//Jelin server smtp configuration
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.yandex.ru");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl", "true");
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.socketFactory.port", "465");
		properties.put("mail.user", settings.getEmail());
		properties.put("mail.password", settings.getEmailPassword());
		
		logger.debug("email: " + settings.getEmail());
		logger.debug("password: " + settings.getEmailPassword());
		
		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(settings.getEmail(), settings.getEmailPassword());
			}
		};
		
		Session session = Session.getInstance(properties, auth);
		
		logger.debug("session created");
		
		//create and send message
		MimeMessage msg = new MimeMessage(session);
	    try {
			msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		    msg.setFrom(new InternetAddress(from));
		    msg.setSubject(subject);
		    msg.setText(body);
		    logger.debug("sending message");
		    Transport.send(msg);
		    logger.debug("message have been sent");
	    } catch (MessagingException e) {
	    	
	    	logger.info("Mail Exception");
	    	logger.info(e.toString());
	    	logger.error(e.toString());
	    	e.printStackTrace();
	    	
			
		}
	}

	@Override
	public void remindPassword(UserModel userModel, Settings settings) {
		
		String from = settings.getEmail();
		String to = userModel.getEmail();
		String subject = settings.getShortName() + " Password Reset";
		StringBuilder body = new StringBuilder();
		body.append("Hello " + userModel.getUsername() + "! \n");
		body.append("\n");
		body.append("Your password: " + userModel.getPassword() + "\n");
		body.append("\n");
		body.append("If you need any help or would like to know more about " + settings.getShortName() + 
				", please visit " + settings.getUrl() + "\n");
		body.append("\n");
		body.append("Thanks! \n");
		body.append(settings.getShortName());
		
		logger.info("Remind password for user: " + userModel.getUsername());
		logger.debug(from);
		logger.debug(to);
		logger.debug(subject);
		logger.debug(body.toString());
		
		this.sendEmail(settings, from, to, subject, body.toString());
		
	}

	@Override
	public void sendRegistrationEmail(User user, Settings settings) {
		
		String from = settings.getEmail();
		String to = user.getEmail();
		String subject = settings.getShortName() + " Username Details";
		
		StringBuilder body = new StringBuilder();
		body.append("Hello " + user.getUsername() + "! \n");
		body.append("\n");
		body.append("Your email address has the following associated username:\n");
		body.append("\n");
		body.append(user.getUsername() + "\n");
		body.append("\n");
		body.append("If you need any help or would like to know more about " + settings.getShortName() + 
				", please visit " + settings.getUrl() + "\n");
		body.append("\n");
		body.append("Thanks! \n");
		body.append(settings.getShortName());
		
		logger.info("Registration email: " + user.getUsername());
		logger.debug(from);
		logger.debug(to);
		logger.debug(subject);
		logger.debug(body.toString());
		
		this.sendEmail(settings, from, to, subject, body.toString());
		
	}
}
