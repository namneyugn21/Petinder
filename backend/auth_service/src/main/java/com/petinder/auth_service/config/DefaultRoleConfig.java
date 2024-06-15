package com.petinder.auth_service.config;

import com.petinder.auth_service.model.Account;
import com.petinder.auth_service.model.Permission;
import com.petinder.auth_service.model.Role;
import com.petinder.auth_service.model.Status;
import com.petinder.auth_service.repository.AccountRepository;
import com.petinder.auth_service.repository.PermissionRepository;
import com.petinder.auth_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DefaultRoleConfig implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final PermissionRepository permissionRepository;

    public void run(ApplicationArguments args) {
        if (accountRepository.findById("shelter_owner").isEmpty()) {
            // Default permissions
            Permission addPetPerm = permissionRepository.save(
                    Permission.builder()
                            .name("ADD_PET")
                            .description("Add new pet to the shelter")
                            .build()
            );
            Permission removePetPerm = permissionRepository.save(
                    Permission.builder()
                            .name("REMOVE_PET")
                            .description("Adopt/remove a pet from the shelter")
                            .build()
            );
            Permission updatePetPerm = permissionRepository.save(
                    Permission.builder()
                            .name("UPDATE_PET")
                            .description("Update pet information")
                            .build()
            );
            Permission viewPetPerm = permissionRepository.save(
                    Permission.builder()
                            .name("VIEW_PET")
                            .description("View pet information")
                            .build()
            );
            Permission viewShelterPerm = permissionRepository.save(
                    Permission.builder()
                            .name("VIEW_SHELTER")
                            .description("View shelter information")
                            .build()
            );
            Permission reservePetPerm = permissionRepository.save(
                    Permission.builder()
                            .name("RESERVE_PET")
                            .description("Reserve a pet for adoption")
                            .build()
            );

            // Default roles
            Set<Role> roles = Set.of(
                    roleRepository.save(
                            Role.builder()
                                    .name("SHELTER_OWNER")
                                    .permissions(
                                            Set.of(
                                                    addPetPerm,
                                                    removePetPerm,
                                                    updatePetPerm,
                                                    viewPetPerm,
                                                    viewShelterPerm,
                                                    reservePetPerm  // employee can be user
                                            )
                                    )
                                    .build()
                    ),
                    roleRepository.save(
                            Role.builder()
                                    .name("SHELTER_EMPLOYEE")
                                    .permissions(
                                            Set.of(
                                                    addPetPerm,
                                                    removePetPerm,
                                                    updatePetPerm,
                                                    viewPetPerm,
                                                    viewShelterPerm,
                                                    reservePetPerm  // employee can be user
                                            )
                                    )
                                    .build()
                    ),
                    roleRepository.save(
                            Role.builder()
                                    .name("USER")
                                    .permissions(
                                            Set.of(
                                                    viewPetPerm,
                                                    viewShelterPerm,
                                                    reservePetPerm
                                            )
                                    )
                                    .build()
                    ),
                    roleRepository.save(
                            Role.builder()
                                    .name("ANONYMOUS")
                                    .permissions(
                                            Set.of(
                                                    viewPetPerm
                                            )
                                    )
                                    .build()
                    )
            );


            // Default user account
            Account account = Account
                    .builder()
                    .email("shelter_owner_0")
                    .password(passwordEncoder.encode("password"))
                    .roles(roles)
                    .status(Status.ACTIVE)
                    .build();
            accountRepository.save(account);
        }
    }
}
