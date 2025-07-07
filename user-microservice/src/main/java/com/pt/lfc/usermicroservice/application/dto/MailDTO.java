package com.pt.lfc.usermicroservice.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDTO {
    private Long idMail;
    private String body;
    private String description;
}