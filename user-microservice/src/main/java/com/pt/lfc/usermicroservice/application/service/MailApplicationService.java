package com.pt.lfc.usermicroservice.application.service;

import com.pt.lfc.usermicroservice.application.port.in.MailApplicationServicePort;
import com.pt.lfc.usermicroservice.domain.port.in.MailServicePort;
import org.springframework.stereotype.Service;

@Service
public class MailApplicationService implements MailApplicationServicePort {

    private final MailServicePort mailServicePort;

    public MailApplicationService(MailServicePort mailServicePort) {
        this.mailServicePort = mailServicePort;
    }

    public void sendMail(String to, String subject, String body) {
        mailServicePort.sendMail(to, subject, body);
    }
}
