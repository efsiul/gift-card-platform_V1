package com.pt.lfc.usermicroservice.application.mapper;

import com.pt.lfc.usermicroservice.domain.model.Mail;
import com.pt.lfc.usermicroservice.application.dto.MailDTO;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MailMapper {
    MailDTO toDTO(Mail mail);

    Mail toDomain(MailDTO dto);

    List<MailDTO> toDTOs(List<Mail> mails);

    List<Mail> toDomainList(List<MailDTO> dtos);
}