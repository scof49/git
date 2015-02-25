package TimeNMoney;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMailTLS {
	final String username;
	final String password;
	Properties props;
	Session session;
	
	public SendMailTLS(){
		this.username = "info.odkas.tnm@gmail.com";
		this.password = "odkas.tnm";
		this.props = new Properties();
		this.props.put("mail.smtp.auth", "true");
		this.props.put("mail.smtp.starttls.enable", "true");
		this.props.put("mail.smtp.host", "smtp.gmail.com");
		this.props.put("mail.smtp.port", "587");
		
		this.session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
	}
	
	public void send_mail_relatorio_erro(String mail_to, String msg_txt) {
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("info.odkas.tnm@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mail_to));
			message.setSubject("Relatório de Erros Time & Money");
				message.setText("Relatório de Erros Time & Money"+
								"\n---------------------------------------"+
								"\n\n"+
								msg_txt);
			
			Transport.send(message);
 
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	public void send_mail_notificacao(String mail_to) {
		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("info.odkas.tnm@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mail_to));
			message.setSubject("Notificação Time & Money");
			message.setText("Notificação Time & Money"+
					"\n---------------------------------"+
					"\n\n"+
					"\nForam aprovadas/rejeitadas tarefas e/ou despesas."+
					"\nPor favor, garanta que tem os dados atualizados para um correto funcionamento da aplicação!"+
					"\n\nObrigado,"+
					"\nA Equipa de Desenvolvimento Odkas - Time & Money");
			
			Transport.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}