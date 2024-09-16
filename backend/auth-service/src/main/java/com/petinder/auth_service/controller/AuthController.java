package com.petinder.auth_service.controller;

import com.petinder.auth_service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Logout. Invalidate JWT token")
    @PostMapping("/logout")
    @SecurityRequirement(name = "bearerJwt")
    public void logout(
            @Autowired final JwtAuthenticationToken token
    ) {
        authService.logout(token);
    }
}
