package com.petinder.pet_service.dto.shelter.update;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.UUID;

@Value
public class UpdateShelterInput {
    @NotNull(message = "shelter's id cannot be empty")
    UUID id;

    String name;

    String address;
}
