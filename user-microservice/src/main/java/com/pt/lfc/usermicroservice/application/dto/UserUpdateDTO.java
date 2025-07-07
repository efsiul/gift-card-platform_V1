package com.pt.lfc.usermicroservice.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserUpdateDTO {
    private String email;
    private Boolean enabled;
    private List<Long> roles;
}
