package com.petinder.auth_service.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@Table(name = "account_provider")
@Entity
@IdClass(AccountProviderKey.class)
public class AccountProvider {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Id
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @CreationTimestamp
    private Instant createdAt;
}
