package com.petinder.pet_service.dto.read;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReadPetInput {
    private UUID petId;
}
