/*
 * @author Daniel Mijens Tutor
 */
package mail;


import java.io.IOException;

import java.util.Properties;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class Sender.
 */
public class Sender {

	/**
	 * Gets the mail prop.
	 *
	 * @return the mail prop
	 */
	@Getter
	Properties mailProp = new Properties();

	/**
	 * Sets the credencial prop.
	 *
	 * @param credencialProp the new credencial prop
	 */
	@Setter
	
	/**
	 * Gets the credencial prop.
	 *
	 * @return the credencial prop
	 */
	@Getter
	Properties credencialProp = new Properties();

	/**
	 * Instantiates a new sender.
	 */
	public Sender() {
		try {
			// Loads all the properties of file "mail.properties".
			mailProp.load(getClass().getClassLoader().getResourceAsStream("mail.properties"));
			credencialProp.load(getClass().getClassLoader().getResourceAsStream("credentials.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send the email.
	 *
	 * @param from the from
	 * @param to the to
	 * @param subject the subject
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean send(String from, String to, String subject, String content) {
		// Get the Session object.// and pass username and password
		Session session = createSession();

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setContent(content,"text/html" );

			// Send message
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Creates the session.
	 *
	 * @return the session
	 */
	private Session createSession() {
		Session session = Session.getInstance(mailProp, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(credencialProp.getProperty(CredentialsConstants.USER),
						credencialProp.getProperty(CredentialsConstants.PASSWD));
			}

		});

		// Used to debug SMTP issues
		session.setDebug(true);
		return session;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */

}
