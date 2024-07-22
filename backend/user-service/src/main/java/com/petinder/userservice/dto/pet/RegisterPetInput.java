package com.petinder.userservice.dto.pet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.UUID;

@Data
public class RegisterPetInput {
    private UUID petId;

    @JsonIgnore
    private UUID userId;
}
