package aibles.springdatajdbc.userservice.mail.service.iml;

import aibles.springdatajdbc.userservice.mail.dto.req.MailRequestDTO;
import aibles.springdatajdbc.userservice.mail.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class IMailServiceIml implements IMailService {

    private final JavaMailSender mailSender;

    @Value("${mail.user.username}")
    private String mailFrom;

    @Autowired
    public IMailServiceIml(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(MailRequestDTO mailRequest) {
        mailSender.send(createMailMessage(mailRequest));
    }

    private SimpleMailMessage createMailMessage(MailRequestDTO mailRequest){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailFrom);
        mailMessage.setTo(mailRequest.getReceiver());
        mailMessage.setSubject(mailRequest.getSubject());
        mailMessage.setText(mailRequest.getMessage());
        return mailMessage;
    }
}
