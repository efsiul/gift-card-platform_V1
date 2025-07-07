package com.pt.lfc.usermicroservice.adapter.in.controller;

import com.pt.lfc.usermicroservice.application.port.in.MailApplicationServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mail")
public class MailController {

    private final MailApplicationServicePort mailService;

    public MailController(MailApplicationServicePort mailService) {
        this.mailService = mailService;
    }

    // Endpoint de prueba/envío manual de correo
    @PostMapping("/send")
    public ResponseEntity<String> sendMail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body
    ) {
        mailService.sendMail(to, subject, body);
        return ResponseEntity.ok("Correo enviado a " + to);
    }
}