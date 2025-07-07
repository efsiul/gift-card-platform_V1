package com.pt.lfc.usermicroservice.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private String email;
    private List<Role> roles;
}