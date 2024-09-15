package com.petinder.auth_service.service;

import com.petinder.auth_service.model.Account;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private static final String ISSUER = "Petinder";
    private static final String BLACK_LIST_KEY_PREFIX = "BL_";

    private final JwtEncoder encoder;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.duration}")
    private long JWT_DURATION;  // in minutes

    @Nonnull
    public static String createBlackListKey(@Nonnull final Jwt jwt) {
        return BLACK_LIST_KEY_PREFIX + jwt.getId();
    }

    @Nonnull
    public String generateJwt(@Nonnull final Account account, @Nonnull final List<String> roles) {
        final Instant issueTime = Instant.now();
        final String scope = String.join(" ", roles);
        final JwtClaimsSet claims = JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ISSUER)
                .issuedAt(issueTime)
                .expiresAt(issueTime.plus(JWT_DURATION, ChronoUnit.MINUTES))
                .subject(account.getId().toString())
                .claim("scope", scope)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public void invalidateJwt(@Nonnull final JwtAuthenticationToken token) {
        final Instant now = Instant.now();
        final Instant expireTime = token.getToken().getExpiresAt();
        final String blackListKey = createBlackListKey(token.getToken());
        redisTemplate.opsForValue().set(blackListKey, Instant.now().toString(), Duration.between(now, expireTime));

        log.debug("Blacked list token id {}", token.getToken().getId());
    }
}
