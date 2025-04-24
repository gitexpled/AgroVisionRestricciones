package lib.db;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class utilMail {

	
	
	public void send(String to, String from, String password, String host, String port, String ssl, String Subject,
			String body, String[] files) throws Exception {

		try {
			Properties props = System.getProperties();
			if (ssl.equals("ssl"))
			{
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.ssl.protocols", "TLSv1.2");
			}
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");

			String[] tos = to.split(",|;");

			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			InternetAddress[] toAddress = new InternetAddress[tos.length];

			// To get the array of addresses
			for (int i = 0; i < tos.length; i++) { // changed from a while loop
				toAddress[i] = new InternetAddress(tos[i]);
			}
			System.out.println(Message.RecipientType.TO);

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			// messageBodyPart.setText(body,"text/html; charset=utf-8");
			messageBodyPart.setContent(body, "text/html; charset=utf-8");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			
			if (files != null) {
				

				for (int i = 0; i < files.length; i++) {
					// Part two is attachment
					messageBodyPart = new MimeBodyPart();
					String filename = files[i];

					DataSource source = new FileDataSource(filename);
					messageBodyPart.setDataHandler(new DataHandler(source));

					filename = filename.replace("/eDte/cl/Procesado/", "");
					filename = filename.replace("/eDte/cl/mailGet/", "");
					filename = filename.replace("/", "_");
					LocalDate fechaHoy = LocalDate.now();
				    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				    String fechaFormateada = fechaHoy.format(formatter);
				        
					messageBodyPart.setFileName("InformeRestriciones"+fechaFormateada+" .xlsx");
					multipart.addBodyPart(messageBodyPart);
				}

				
			}
			// Send the complete message parts
			message.setContent(multipart);
			message.setSubject(Subject);

			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("mail: AddressException" + e.getMessage());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("mail: MessagingException" + e.getMessage());
		}
	}
}
