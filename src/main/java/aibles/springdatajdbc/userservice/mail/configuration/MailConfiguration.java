package aibles.springdatajdbc.userservice.mail.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import static aibles.springdatajdbc.userservice.mail.constant.MailConfigProperties.*;

@Configuration
public class MailConfiguration {

    @Value("${mail.user.username}")
    private String mailUsername;

    @Value("${mail.user.password}")
    private String mailPassword;

    @Bean
    public JavaMailSender createJavaMailSendBean() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(MAIL_HOST);
        mailSender.setPort(MAIL_PORT);

        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", MAIL_TRANSPORT_PROTOCOL);
        props.put("mail.smtp.auth", MAIL_STMP_AUTH);
        props.put("mail.smtp.starttls.enable", MAIL_STMP_START_TLS);
        props.put("mail.debug", MAIL_DEBUG);

        return mailSender;
    }
}
