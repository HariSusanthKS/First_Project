import java.util.*;
import java.io.*; 
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.*;  
import javax.servlet.http.*;  

public class Mail extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException  
	{
]
		final String user="ravivijay0512222@gmail.com"; 
		final String password="poojakannan0512";
		String to=req.getRemoteUser();
	   	Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {protected PasswordAuthentication getPasswordAuthentication() {return new PasswordAuthentication(user,password);}});
		try 
		{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			message.setSubject("From First Project");
			message.setText("Thanks for login to My Project");
			Transport.send(message);	 
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}
}