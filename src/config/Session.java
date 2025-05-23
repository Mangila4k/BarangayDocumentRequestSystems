package config;

import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Session {

    private static Session instance;

    private int id;
    private String first_name;
    private String last_name;
    private String gender;
    private String contact;
    private String username;
    private String user_type;
    private String status;
    private String email;

    private Session() {
        // Private constructor for singleton
    }

    public static synchronized Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public static boolean isInstanceEmpty() {
        return instance == null;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // --- Added method below ---

    /**
     * Sends a verification code to the email stored in this Session.
     * @param code the verification code to send
     * @return true if email sent successfully, false otherwise
     */
    public boolean sendVerificationCode(String code) {
        String toEmail = this.email;
        if (toEmail == null || toEmail.isEmpty()) {
            System.err.println("Email address is not set.");
            return false;
        }

        // TODO: Replace with your actual email and app password
        final String fromEmail = "your_email@gmail.com";
        final String password = "your_email_password";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        try {
    jakarta.mail.Session mailSession = jakarta.mail.Session.getInstance(props, new jakarta.mail.Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(fromEmail, password);
        }
    });

    Message message = new MimeMessage(mailSession);
    message.setFrom(new InternetAddress(fromEmail, "NoReply-PasswordReset"));
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
    message.setSubject("Password Reset Verification Code");
    message.setContent("Your verification code is: <b>" + code + "</b>", "text/html");
    message.setSentDate(new java.util.Date());

    Transport.send(message);

    System.out.println("Verification code sent to " + toEmail);
    return true;
} catch (Exception e) {
    e.printStackTrace();
    return false;
}
    }

}
