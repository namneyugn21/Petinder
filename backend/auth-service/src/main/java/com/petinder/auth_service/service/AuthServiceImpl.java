package com.petinder.auth_service.service;

import com.petinder.auth_service.config.RabbitMqConfig;
import com.petinder.auth_service.dto.UserInfo;
import com.petinder.auth_service.model.*;
import com.petinder.auth_service.repository.AccountProviderRepository;
import com.petinder.auth_service.repository.AccountRepository;
import com.petinder.auth_service.repository.redirect.RedirectRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final TopicExchange topicExchange;
    private final RabbitTemplate rabbitTemplate;
    private final AccountRepository accountRepository;
    private final RedirectRepository redirectRepository;
    private final AccountProviderRepository accountProviderRepository;

    /**
     * Register the user if there is no account associated with {@code authentication}'s email in the DB.
     * Additionally, register the provider to the user's account
     * if this is the first time the user log in via that provider.
     * Redirect to the home page after successful authentication.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     *                       the authentication process.
     * @throws IOException if fail to redirect to the home page
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        if (!(authentication instanceof OAuth2AuthenticationToken token) ||
                !(authentication.getPrincipal() instanceof OAuth2User user)
        ) {
            return;
        }

        // Obtain the redirect_uri from the initial FE request (when FE call /oauth2/authorization/{provider})
        final String state = request.getParameter("state");
        String redirectUri = redirectRepository.findAndDeleteByState(state);

        // Save the user into the database if the user is new
        final Account account = registerIfNeed(user, token.getAuthorizedClientRegistrationId());
        final List<String> roles = account.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        // Redirect to the set redirect link
        redirectUri = UriComponentsBuilder
                .fromUriString(redirectUri)
                .queryParam("userId", account.getId())
                .queryParam("role", roles)
                .queryParam("token", jwtService.generateJwt(account, roles))
                .build()
                .toUriString();
        response.sendRedirect(redirectUri);
    }

    /**
     * Extract user info from {@code OAuth2user}.
     * This function is typically used to construct a DTO that will be sent to User Service to create a new profile.
     *
     * @param email user's email extracted from OAuth2User
     * @param user  user info obtains from OAuth provider
     * @return {@code UserInfo} DTO
     */
    private UserInfo constructUserInfo(
            final String email, final OAuth2User user
    ) {
        // Get user's info
        String firstName = user.getAttribute("given_name");
        String lastName = user.getAttribute("family_name");
        String middleName = user.getAttribute("middle_name");
        if (firstName == null && lastName == null) {
            final String fullName = user.getAttribute("name");
            Assert.notNull(fullName, "Cannot obtain first or last name from OAuth2");

            // Obtain first, middle, and last through full name
            final String[] names = fullName.split(" ");
            firstName = names[0];
            lastName = names[names.length - 1];
            if (names.length == 3) {
                middleName = names[1];
            }
        }
        final String picture = user.getAttribute("picture");

        return UserInfo.builder()
                .email(email)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .picture(picture)
                .build();
    }

    /**
     * Register the user if s/he doesn't exist in the database.
     * Link the user with the provider if this is the first time the user log in via that provider.
     *
     * @param user     the OAuth2 returned by the {@link OAuth2User}
     * @param provider the provider name
     */
    @Transactional(Transactional.TxType.MANDATORY)
    public Account registerIfNeed(
            final OAuth2User user,
            final String provider
    ) {
        final String email = user.getAttribute("email");
        Assert.notNull(email, "Cannot obtain email from OAuth2");

        // New user -> Register a new account
        final Account account = accountRepository.findByEmail(email)
                .orElseGet(() -> {
                    final UserInfo userInfo = constructUserInfo(email, user);
                    return registerNewAccount(userInfo);
                });

        final AccountProviderKey key = new AccountProviderKey(
                account,
                Provider.valueOf(provider.toUpperCase())
        );

        // First time login with this provider -> Link the user with the provider
        if (!accountProviderRepository.existsById(key)) {
            AccountProvider accountProviderEntity = new AccountProvider();
            accountProviderEntity.setAccount(account);
            accountProviderEntity.setProvider(key.getProvider());
            accountProviderRepository.save(accountProviderEntity);
        }

        return account;
    }

    /**
     * Create a new account and a new user profile in User Service
     *
     * @param userInfo user detail got from OAuth provider
     * @return a new account
     */
    @Transactional(Transactional.TxType.MANDATORY)
    public Account registerNewAccount(
            final UserInfo userInfo
    ) {
        // Save account
        final Set<Role> roles = Set.of(Role.USER);
        Account account = Account.builder()
                .email(userInfo.getEmail())
                .roles(roles)
                .status(Status.ACTIVE)
                .build();
        account = accountRepository.save(account);

        // Send user info to the User service
        userInfo.setAccountId(account.getId());
        rabbitTemplate.convertAndSend(topicExchange.getName(), RabbitMqConfig.CREATE_USER, userInfo);

        return account;
    }

    public void logout(JwtAuthenticationToken token) {
        jwtService.invalidateJwt(token);
    }
}
