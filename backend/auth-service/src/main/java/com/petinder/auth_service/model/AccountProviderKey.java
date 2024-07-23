package com.petinder.auth_service.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountProviderKey {
    private Account account;

    @Enumerated(EnumType.STRING)
    private Provider provider;
}
