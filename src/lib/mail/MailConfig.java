package lib.mail;

import java.util.Properties;

public class MailConfig {

    public static Properties getMailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "cloud.goplicity.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "cloud.goplicity.com");
        return props;
    }

    public static final String USERNAME = "envios@cloud.goplicity.com";
    public static final String PASSWORD = "^BxUl91Cv-)5^SpA0E";
}
