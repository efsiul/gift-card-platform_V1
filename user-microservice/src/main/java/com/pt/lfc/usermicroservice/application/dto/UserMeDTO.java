package com.pt.lfc.usermicroservice.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserMeDTO {
    private Long id;
    private String username;
    private String email;
    private Boolean enabled;
    private List<RoleDTO> roles;
}