package com.pt.lfc.usermicroservice.application.port.in;

import com.pt.lfc.usermicroservice.application.dto.*;

public interface AuthenticationApplicationServicePort {
    AuthenticationResponse login(AuthenticationRequest request);
    AuthenticationResponse internalLogin(AuthenticationRequest request);
}