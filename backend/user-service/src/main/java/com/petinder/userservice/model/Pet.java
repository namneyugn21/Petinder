package com.petinder.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class Pet implements Serializable {
    @Column(name = "pet_id", nullable = false)
    private UUID petId;
}
