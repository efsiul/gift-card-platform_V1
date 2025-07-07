package com.pt.lfc.usermicroservice.application.service;

import com.pt.lfc.usermicroservice.application.dto.AuthenticationRequest;
import com.pt.lfc.usermicroservice.application.dto.AuthenticationResponse;
import com.pt.lfc.usermicroservice.application.dto.ResultDTO;
import com.pt.lfc.usermicroservice.application.port.in.AuthenticationApplicationServicePort;
import com.pt.lfc.usermicroservice.domain.model.User;
import com.pt.lfc.usermicroservice.domain.port.in.AuthenticationServicePort;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationApplicationService implements AuthenticationApplicationServicePort {

    private final AuthenticationServicePort authenticationServicePort;

    public AuthenticationApplicationService(AuthenticationServicePort authenticationServicePort) {
        this.authenticationServicePort = authenticationServicePort;
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        String token = authenticationServicePort.authenticate(request.username(), request.password());
        return new AuthenticationResponse(token, request.username());
    }

    @Override
    public AuthenticationResponse internalLogin(AuthenticationRequest request) {
        String token = authenticationServicePort.authenticate(request.username(), request.password());
        return new AuthenticationResponse(token, request.username());
    }
}