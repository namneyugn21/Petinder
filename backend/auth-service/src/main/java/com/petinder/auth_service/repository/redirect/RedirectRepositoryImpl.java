package com.petinder.auth_service.repository.redirect;


import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedirectRepositoryImpl implements RedirectRepository {

    private static final Duration OAUTH_MAX_TIME = Duration.ofMinutes(15);

    private final RedisTemplate<String, String> redirectByState;

    @Value("${spring.application.fe-default-url}")
    private String DEFAULT_REDIRECT;

    @Nonnull
    @Override
    public String findAndDeleteByState(String state) {
        String redirectUrl = redirectByState.opsForValue().getAndDelete(state);
        if (redirectUrl == null) {
            redirectUrl = DEFAULT_REDIRECT;
        }

        return redirectUrl;
    }

    @Override
    public void saveRedirect(String state, String redirect) {
        redirectByState.opsForValue().set(state, redirect, OAUTH_MAX_TIME);
    }
}
