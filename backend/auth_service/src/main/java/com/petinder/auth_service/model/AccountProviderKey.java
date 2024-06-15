package com.petinder.auth_service.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AccountProviderKey {
    private String accountEmail;

    @Enumerated(EnumType.STRING)
    private Provider provider;
}
