package com.petinder.auth_service.service;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public interface AuthService extends AuthenticationSuccessHandler, LogoutSuccessHandler {
}
