package com.petinder.auth_service.config;

import com.petinder.auth_service.model.Account;
import com.petinder.auth_service.model.Role;
import com.petinder.auth_service.model.Status;
import com.petinder.auth_service.repository.AccountRepository;
import com.petinder.auth_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DefaultAccountConfig implements ApplicationRunner {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;

    public void run(ApplicationArguments args) {
        // Default user role
        if (!roleRepository.existsById(Role.USER_VALUE)) {
            roleRepository.save(Role.USER);
        }

        // Default shelter role
        if (!roleRepository.existsById(Role.SHELTER_VALUE)) {
            roleRepository.save(Role.SHELTER);
        }

        // Default shelter account
        if (!accountRepository.existsByEmail("shelter0@mail")) {
            Account account = Account
                    .builder()
                    .email("shelter0@mail")
                    .password(passwordEncoder.encode("password"))
                    .roles(Set.of(Role.SHELTER))
                    .status(Status.ACTIVE)
                    .build();
            accountRepository.save(account);
        }
    }
}
