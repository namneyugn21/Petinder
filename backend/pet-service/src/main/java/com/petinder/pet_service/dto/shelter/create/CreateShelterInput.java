package com.petinder.pet_service.dto.shelter.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

import java.util.UUID;

@Value
public class CreateShelterInput {
    @NotEmpty(message = "shelter's name cannot be empty")
    String name;

    @NotEmpty(message = "shelter's address cannot be empty")
    String address;

    @JsonIgnore
    UUID ownerId;
}
