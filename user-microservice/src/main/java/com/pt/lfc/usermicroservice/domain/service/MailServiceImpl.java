package com.pt.lfc.usermicroservice.domain.service;

import com.pt.lfc.usermicroservice.domain.port.in.MailServicePort;
import com.pt.lfc.usermicroservice.domain.port.out.MailRepositoryPort;
import com.pt.lfc.usermicroservice.domain.port.out.MailSenderPort;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailServicePort {
    private final MailSenderPort mailSenderPort;
    private final MailRepositoryPort mailRepositoryPort;

    public MailServiceImpl(MailSenderPort mailSenderPort, MailRepositoryPort mailRepositoryPort) {
        this.mailSenderPort = mailSenderPort;
        this.mailRepositoryPort = mailRepositoryPort;
    }


    @Override
    public void sendMail(String to, String subject, String body) {
        mailSenderPort.sendMail(to, subject, body);
    }
}
