package com.petinder.pet_service.dto.shelter.read;

import lombok.Value;

import java.util.UUID;

@Value
public class ReadShelterOutput {
    UUID id;

    String name;

    String address;
}
