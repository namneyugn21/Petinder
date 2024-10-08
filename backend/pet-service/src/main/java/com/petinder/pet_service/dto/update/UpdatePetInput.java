package com.petinder.pet_service.dto.update;

import com.petinder.pet_service.model.Pet;
import com.petinder.pet_service.model.PetProperty;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UpdatePetInput {
    @Min(0)
    private Double weight;
    private UUID id;
    private UUID shelterId;
    private String description;
    private Pet.Status status;
    private String name;
    private String picture;
    private PetProperty.Age age;
    private String breed;
    private String furColor;
    private String eyeColor;
}
