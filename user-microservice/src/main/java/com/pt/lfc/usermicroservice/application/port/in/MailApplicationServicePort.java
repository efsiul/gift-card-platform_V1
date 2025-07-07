package com.pt.lfc.usermicroservice.application.port.in;

public interface MailApplicationServicePort {
    void sendMail(String to, String subject, String body);
}