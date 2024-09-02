package com.petinder.pet_service.dto.comm;

import lombok.Value;

import java.util.UUID;

@Value
public class UserPetDto {
    UUID userId;
    UUID petId;
    Boolean liked;
}
