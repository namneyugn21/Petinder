package com.petinder.pet_service.dto.delete;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DeletePetInput {
    private UUID petId;
}
