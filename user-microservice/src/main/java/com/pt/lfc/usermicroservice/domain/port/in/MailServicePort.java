package com.pt.lfc.usermicroservice.domain.port.in;

public interface MailServicePort {
    void sendMail(String to, String subject, String body);
}
