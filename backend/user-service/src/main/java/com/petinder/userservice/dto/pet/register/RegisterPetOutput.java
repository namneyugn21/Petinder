package com.petinder.userservice.dto.pet.register;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class RegisterPetOutput {
    private UUID userId;
    private UUID petId;
    private Instant creatAt;
}
