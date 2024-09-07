package com.petinder.pet_service.dto.shelter.update;

import lombok.Value;

import java.util.UUID;

@Value
public class UpdateShelterOutput {
    UUID id;

    String name;

    String address;
}
