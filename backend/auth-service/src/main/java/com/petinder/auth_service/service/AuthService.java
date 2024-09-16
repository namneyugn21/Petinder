package com.petinder.auth_service.service;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public interface AuthService extends AuthenticationSuccessHandler {
    void logout(JwtAuthenticationToken token);
}
