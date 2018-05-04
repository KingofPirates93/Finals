package LogMeOn;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Crunchify.com
 *
 */

public class JavaMail {

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;
  private static String SEND;

 public static String getSEND() {
  return SEND;
 }


   /** public static void main(String args[]) throws AddressException, MessagingException {
        generateAndSendEmail();
        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
    }
    */

    public static void generateAndSendEmail() throws AddressException, MessagingException {

       SEND = String.valueOf( KeyGen.OTP( 6 ) );

        // Step1
        System.out.println("\nSetting Up Mail Server..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");

        // Step2
        System.out.println("\n\nGenerating Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("swapnilp618@gmail.com"));
        generateMailMessage.setSubject("Greetings from Swapnil..");
        String emailBody = "Secure Email by Swapnil. " + "<br><br> Your one time password is: " + SEND + "<br><br> Regards, <br>Swapnil";
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

        // Step3
        System.out.println("\n\nGetting Session and Sending email");
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "javatestmailer55@gmail.com", "Swapnil6189@");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();


    }


}