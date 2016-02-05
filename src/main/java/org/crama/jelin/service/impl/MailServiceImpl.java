package org.crama.jelin.service.impl;

import java.util.Date;
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
import org.crama.jelin.model.UserModel;
import org.crama.jelin.service.MailService;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailServiceImpl implements MailService {
	
	
	
	private void sendEmail(String from, String to, String subject, String body) {
		// sets SMTP server properties
		//local system TODO change it
		final String userName = "vitalii.oleksiv@gmail.com";
		final String password = "liverpool0_1892";
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", 587);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);
		
		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(properties, auth);
		
		//create and send message
		MimeMessage msg = new MimeMessage(session);
	    try {
			msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		    msg.setFrom(new InternetAddress(from));
		    msg.setSubject(subject);
		    msg.setText(body);
		    
		    Transport.send(msg);
	    } catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remindPassword(UserModel userModel) {
		
		String from = Constants.EMAIL;
		String to = userModel.getEmail();
		String subject = Constants.SHORT_GAME_NAME + " Password Reset";
		StringBuilder body = new StringBuilder();
		body.append("Hello " + userModel.getUsername() + "! \n");
		body.append("\n");
		body.append("Your password: " + userModel.getPassword() + "\n");
		body.append("\n");
		body.append("If you need any help or would like to know more about " + Constants.SHORT_GAME_NAME + 
				", please visit " + Constants.SITE_URL + "\n");
		body.append("\n");
		body.append("Thanks! \n");
		body.append(Constants.SHORT_GAME_NAME);
		
		System.out.println(from);
		System.out.println(to);
		System.out.println(subject);
		System.out.println(body.toString());
		
		this.sendEmail(from, to, subject, body.toString());
		
	}
}
