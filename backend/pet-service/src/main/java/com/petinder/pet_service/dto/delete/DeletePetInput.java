package com.petinder.pet_service.dto.delete;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeletePetInput {
    private String petId;
}
