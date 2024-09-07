package com.petinder.pet_service.dto.shelter.create;

import lombok.Value;

import java.util.UUID;

@Value
public class CreateShelterOutput {
    UUID id;

    String name;

    String address;
}
