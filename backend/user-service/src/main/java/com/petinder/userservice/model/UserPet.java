package com.petinder.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_pet")
@IdClass(UserPetKey.class)
public class UserPet implements Serializable, Persistable<UserPetKey> {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "pet_id")
    private UUID petId;

    @Builder.Default
    @Column(name = "liked")
    private Boolean liked = Boolean.TRUE;

    @CreationTimestamp
    @Column(name = "createAt")
    private Instant createAt;

    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public UserPetKey getId() {
        return new UserPetKey(this.userId, this.petId);
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
