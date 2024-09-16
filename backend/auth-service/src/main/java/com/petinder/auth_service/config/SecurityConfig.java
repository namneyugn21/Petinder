package com.petinder.auth_service.config;

import com.petinder.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtDecoder jwtDecoder;
    private final AuthService authenticationService;
    private final OAuth2AuthorizationRequestResolver authorizationRequestResolver;

    //    @Value("${spring.application.api-prefix}")
    private final String API_PREFIX = "";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .anyRequest()
                                .permitAll()
                )
                .oauth2Login(oauth2 ->
                        oauth2
                                .successHandler(authenticationService)
                                .authorizationEndpoint(authEndpoint ->
                                        authEndpoint
                                                .authorizationRequestResolver(authorizationRequestResolver)
                                )
                                .loginProcessingUrl(
                                        API_PREFIX + OAuth2LoginAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI
                                )
                )
                .oauth2ResourceServer(oauth ->
                        oauth
                                .jwt(jwtConfigurer ->
                                        jwtConfigurer
                                                .decoder(jwtDecoder)
                                )

                )
                .sessionManagement(session ->
                        session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptions ->
                        exceptions
                                .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
