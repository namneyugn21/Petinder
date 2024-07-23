package com.petinder.auth_service.service;

import com.petinder.auth_service.repository.RedirectRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

/**
 * A custom {@link OAuth2AuthorizationRequestResolver} that saves the redirect_uri
 * parameter to the {@link RedirectRepository} for later use.
 * <p>
 * The saved redirect_uri will be used in the
 * {@link AuthServiceImpl}, which uses it to response
 * to the calling client with the JWT.
 */
@Component
public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    private final RedirectRepository redirectRepository;
    private final OAuth2AuthorizationRequestResolver defaultResolver;

    @Autowired
    public CustomAuthorizationRequestResolver(
            RedirectRepository redirectRepository,
            ClientRegistrationRepository clientRegistrationRepository
    ) {
        this.redirectRepository = redirectRepository;
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository,
                OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
        );
    }

    /**
     * Returns the {@link OAuth2AuthorizationRequest} resolved from the provided
     * {@code HttpServletRequest} or {@code null} if not available.
     *
     * @param request the {@code HttpServletRequest}
     * @return the resolved {@link OAuth2AuthorizationRequest} or {@code null} if not
     * available
     */
    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest req = defaultResolver.resolve(request);
        if (req != null) {
            String redirectUri = request.getParameter("redirect_uri");
            if (redirectUri != null) {
                redirectRepository.saveRedirect(req.getState(), redirectUri);
            }
        }
        return req;
    }

    /**
     * Returns the {@link OAuth2AuthorizationRequest} resolved from the provided
     * {@code HttpServletRequest} or {@code null} if not available.
     *
     * @param request              the {@code HttpServletRequest}
     * @param clientRegistrationId the clientRegistrationId to use
     * @return the resolved {@link OAuth2AuthorizationRequest} or {@code null} if not
     * available
     */
    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest req = defaultResolver.resolve(request, clientRegistrationId);
        if (req != null) {
            String redirectUri = request.getParameter("redirect_uri");
            if (redirectUri != null) {
                redirectRepository.saveRedirect(req.getState(), redirectUri);
            }
        }
        return req;
    }
}
