package com.petinder.pet_service.dto.shelter.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.UUID;

@Value
public class CreateShelterInput {
    @NotEmpty(message = "shelter's name cannot be empty")
    String name;

    @NotEmpty(message = "shelter's address cannot be empty")
    String address;

    @Setter
    @NonFinal
    @JsonIgnore
    UUID ownerId;
}
