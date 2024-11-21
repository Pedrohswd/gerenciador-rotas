package com.malmitas.backend.controller;

import com.malmitas.backend.model.dtos.response.AuthResponse;
import com.malmitas.backend.model.dtos.request.LoginRequest;
import com.malmitas.backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequest authentication) {
        String token = authenticationService.authenticate(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

