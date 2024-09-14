package com.petinder.pet_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "shelter")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Shelter implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Instant createAt;

    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private Instant updateAt;
}
