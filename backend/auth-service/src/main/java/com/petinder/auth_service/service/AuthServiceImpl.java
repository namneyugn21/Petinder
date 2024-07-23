package com.petinder.auth_service.service;

import com.petinder.auth_service.dto.UserInput;
import com.petinder.auth_service.model.*;
import com.petinder.auth_service.repository.AccountProviderRepository;
import com.petinder.auth_service.repository.AccountRepository;
import com.petinder.auth_service.repository.RedirectRepository;
import com.petinder.auth_service.repository.RoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RestClient restClient;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final RedirectRepository redirectRepository;
    private final AccountProviderRepository accountProviderRepository;

    /**
     * Get the OidcUser from the authentication object and register the user if it does not exist in the database.
     * Send user info to the User service.
     * Redirect to the home page after successful authentication.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     *                       the authentication process.
     * @throws IOException if fail to redirect to the home page
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        if (authentication instanceof OAuth2AuthenticationToken token) {
            if (authentication.getPrincipal() instanceof OAuth2User user) {
                // Save the user into the database if the user is new
                try {
                    registerIfNeed(user, token.getAuthorizedClientRegistrationId());
                } catch (Exception e) {
                    throw new RuntimeException("Fail to register/lookup an OAuth2 User", e);    // TODO: custom exception
                }

                // Obtain the redirect_uri from the initial FE request (when FE call /oauth2/authorization/{provider})
                String state = request.getParameter("state");
                String redirectUri = redirectRepository.findByState(state);

                // If the redirect_uri is found, send the JWT to the FE
                if (redirectUri != null) {
                    URI uri = UriComponentsBuilder
                            .fromUriString(redirectUri)
                            .queryParam("token", ((OidcUser) user).getIdToken().getTokenValue())    // TODO: generate JWT
                            .build()
                            .toUri();
                    redirectRepository.removeByState(state);
                    response.sendRedirect(uri.toString()); // optional; FE will probably close the window
                }
            }
        }
    }

    /**
     * Register the user if it does not exist in the database.
     * Link the user with the provider if needed.
     *
     * @param user     the OAuth2 returned by the {@link OAuth2User}
     * @param provider the provider name
     */
    private void registerIfNeed(OAuth2User user, String provider) throws Exception {
        String email = user.getAttribute("email");
        Assert.notNull(email, "Cannot obtain email from OAuth2");

        Optional<Account> account = accountRepository.findById(email);

        // New user -> Register a new account
        if (account.isEmpty()) {
            // Get first, middle, and last name
            String firstName = user.getAttribute("givenName");
            String lastName = user.getAttribute("familyName");
            String middleName = user.getAttribute("middleName");
            if (firstName == null && lastName == null) {
                String fullName = user.getAttribute("name");
                Assert.notNull(fullName, "Cannot obtain first or last name from OAuth2");

                // Obtain first, middle, and last through full name
                String[] names = fullName.split(" ");
                firstName = names[0];
                lastName = names[names.length - 1];
                middleName = names.length == 3 ? names[1] : null;
            }

            // Get user's picture
            String picture = user.getAttribute("picture");

            // Register a new account
            registerNewAccount(
                    email,
                    firstName,
                    middleName,
                    lastName,
                    picture
            );
        }

        AccountProviderKey key = new AccountProviderKey(
                email,
                Provider.valueOf(provider.toUpperCase())
        );
        Optional<AccountProvider> accountProvider = accountProviderRepository.findById(key);

        // First time login with this provider -> Link the user with the provider
        if (accountProvider.isEmpty()) {
            AccountProvider accountProviderEntity = new AccountProvider();
            accountProviderEntity.setId(key);
            accountProviderRepository.save(accountProviderEntity);
        }
    }

    private void registerNewAccount(
            String email,
            String firstName,
            String middleName,
            String lastName,
            String picture
    ) {
        // Save account
        Set<Role> roles = Set.of(roleRepository.findById("USER").orElseThrow());
        Account account = Account.builder()
                .email(email)
                .roles(roles)
                .status(Status.ACTIVE)
                .build();
        account = accountRepository.save(account);

        // Send user info to the User service
        UserInput userInput = UserInput.builder()
                .email(account.getEmail())
                .lastName(lastName)
                .middleName(middleName)
                .firstName(firstName)
                .picture(picture)
                .build();
        sendUserInfo(userInput);
    }

    private void sendUserInfo(UserInput userInput) {
        // TODO: Send user info to User service
        System.out.println("Sending " + userInput);
    }
}