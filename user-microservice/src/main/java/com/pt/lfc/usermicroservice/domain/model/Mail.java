package com.pt.lfc.usermicroservice.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    private Long idMail;
    private String body;
    private String description;
}