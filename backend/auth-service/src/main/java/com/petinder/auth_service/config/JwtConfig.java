package com.petinder.auth_service.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.petinder.auth_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.public.key}")
    private RSAPublicKey key;

    @Value("${jwt.private.key}")
    private RSAPrivateKey priv;

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtDecoder());
    }

    public JwtDecoder jwtDecoder() {
        final NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(this.key).build();
        decoder.setJwtValidator(blackListValidator());
        return decoder;
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        final JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        final JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    private OAuth2TokenValidator<Jwt> blackListValidator() {
        return new BlackListValidator(redisTemplate);
    }

    @RequiredArgsConstructor
    public static class BlackListValidator implements OAuth2TokenValidator<Jwt> {
        private final RedisTemplate<String, String> redisTemplate;

        @Override
        public OAuth2TokenValidatorResult validate(final Jwt jwt) {
            final String blackListTime = redisTemplate.opsForValue().get(JwtService.createBlackListKey(jwt));
            if (blackListTime != null) {
                final OAuth2Error error = new OAuth2Error(
                        "WH_BL",
                        "Token is invalidated at " + blackListTime,
                        null
                );
                return OAuth2TokenValidatorResult.failure(error);
            }

            return OAuth2TokenValidatorResult.success();
        }
    }
}
