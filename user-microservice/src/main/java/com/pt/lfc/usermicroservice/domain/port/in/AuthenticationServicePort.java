package com.pt.lfc.usermicroservice.domain.port.in;

import com.pt.lfc.usermicroservice.domain.model.User;


public interface AuthenticationServicePort {
    String authenticate(String username, String password);
    User validateToken(String token);
}
