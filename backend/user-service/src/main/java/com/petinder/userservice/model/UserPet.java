package com.petinder.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
public class UserPet implements Serializable {
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
    @Column(name = "create_at")
    private Instant createAt;

    @JsonIgnore // for internal transmission btw backend services
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
