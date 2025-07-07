package com.pt.lfc.usermicroservice.domain.service;

import com.pt.lfc.usermicroservice.domain.model.Mail;
import com.pt.lfc.usermicroservice.domain.port.out.DataSeederPort;
import com.pt.lfc.usermicroservice.domain.port.out.MailRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadDataServiceImpl {
    private final DataSeederPort dataSeederPort;
    private final MailRepositoryPort mailRepositoryPort;

    public LoadDataServiceImpl(DataSeederPort dataSeederPort, MailRepositoryPort mailRepositoryPort) {
        this.dataSeederPort = dataSeederPort;
        this.mailRepositoryPort = mailRepositoryPort;
    }

    public void seedMailData(String path) {
        List<Mail> mails = dataSeederPort.loadMailDataFromFile(path);
        mails.forEach(mailRepositoryPort::save);
    }
}