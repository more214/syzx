package com.cn.configlog;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import com.cn.basepage.BrowserEngine;

/**
 * Send email using java
 * 
 * @param from
 * @param pass
 * @param to
 * @param subject
 * @param body
 */
public class SendEmailByPDF extends BrowserEngine {

	public static void sendPDFReportByGMail(String from, String pass, String to, String subject, String body) {
		Properties props = System.getProperties();
		String host = "smtp.qq.com";

		// 设置邮件服务器的信息，这里设置smtp主机名称
		props.put("mail.smtp.host", host);

		// 设置socket factory 的端口
		props.put("mail.smtp.socketFactory.port", "465");

		// 设置socket factory
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		// 设置需要身份验证
		props.put("mail.smtp.auth", "true");

		// 设置SMTP的端口，QQ的smtp端口是25
		props.put("mail.smtp.port", "25");

		// 身份验证实现
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				// 第二个参数，就是我QQ开启smtp的授权码
				return new PasswordAuthentication("23044772@qq.com", "gxxrcnmfugiicaic");
			}
		});
		MimeMessage message = new MimeMessage(session);

		try {
			// Set from address
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set subject
			message.setSubject(subject);
			message.setText(body);

			BodyPart objMessageBodyPart = new MimeBodyPart();

			objMessageBodyPart.setText("Please Find The Attached Report File!");

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(objMessageBodyPart);

			objMessageBodyPart = new MimeBodyPart();

			// Set path to the pdf report file
			String filename = "./result/pdf/Test.pdf";
			// Create data source to attach the file in mail
			DataSource source = new FileDataSource(filename);

			objMessageBodyPart.setDataHandler(new DataHandler(source));

			objMessageBodyPart.setFileName(filename);

			multipart.addBodyPart(objMessageBodyPart);

			message.setContent(multipart);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
}
