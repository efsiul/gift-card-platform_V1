package com.pt.lfc.usermicroservice.adapter.in.controller;

import com.pt.lfc.usermicroservice.application.dto.AuthenticationRequest;
import com.pt.lfc.usermicroservice.application.dto.AuthenticationResponse;
import com.pt.lfc.usermicroservice.application.port.in.AuthenticationApplicationServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
public class AuthenticationController {

    private final AuthenticationApplicationServicePort authenticationService;

    public AuthenticationController(AuthenticationApplicationServicePort authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/internalLogin")
    public ResponseEntity<AuthenticationResponse> internalLogin(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.internalLogin(request));
    }
}
