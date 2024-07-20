package com.petinder.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_pet")
@IdClass(PetKey.class)
public class Pet implements Serializable, Persistable<PetKey> {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "pet_id")
    private UUID petId;

    @CreationTimestamp
    private Instant creatAt;

    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public PetKey getId() {
        return new PetKey(this.userId, this.petId);
    }

    /**
     * This is to prevent JPA update.
     * Always return true make JPA always uses insert instead of update
     *
     * @return always return true
     */
    @Override
    public boolean isNew() {
        return true;
    }
}
