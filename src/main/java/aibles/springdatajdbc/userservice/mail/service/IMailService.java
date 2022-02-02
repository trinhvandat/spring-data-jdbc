package aibles.springdatajdbc.userservice.mail.service;

import aibles.springdatajdbc.userservice.mail.dto.req.MailRequestDTO;

public interface IMailService {
    void sendMail(MailRequestDTO mailRequest);
}
