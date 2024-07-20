package com.petinder.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@IdClass(UserPet.class)
public class UserPet implements Serializable {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "pet_id")
    private UUID petId;

    @CreationTimestamp
    private Instant creatAt;
}
