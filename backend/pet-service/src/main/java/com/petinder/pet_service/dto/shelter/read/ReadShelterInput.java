package com.petinder.pet_service.dto.shelter.read;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ReadShelterInput {
    UUID id;
}
