package com.petinder.auth_service.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@Entity(name = "account_provider")
public class AccountProvider {
    @EmbeddedId
    private AccountProviderKey id;

    @CreationTimestamp
    private Instant createdAt;
}
